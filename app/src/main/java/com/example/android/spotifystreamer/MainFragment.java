package com.example.android.spotifystreamer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.spotifystreamer.data.ArtistSpotify;
import com.example.android.spotifystreamer.data.SpotifyObject;
import com.example.android.spotifystreamer.data.TrackSpotify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;
import retrofit.RetrofitError;

/**
 * Created by carolinamarin on 6/16/15.
 */
public class MainFragment extends Fragment {


    private static final String SELECTED_KEY = "selected_position";
    private String artistId;
    private String artistName;
    private ResultsAdapter adapter;
    private ListView listView;
    private int mPosition = ListView.INVALID_POSITION;
    private ArrayList<SpotifyObject> mArtists;

    @Override
    public void onCreate(Bundle instanceState) {
        super.onCreate(instanceState);
        // Log.d(LOG_TAG, "In onCreate method.");
        this.setRetainInstance(true);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        adapter = new ResultsAdapter(getActivity(), R.layout.list_item_main, new ArrayList<SpotifyObject>());

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (ListView) rootView.findViewById(R.id.listview_main);
        listView.setAdapter(adapter);

        ListView listViewTracks = (ListView) rootView.findViewById(R.id.listview_tracks);
        if (listViewTracks != null) {
            ResultsAdapter adap = (ResultsAdapter) listViewTracks.getAdapter();
            adap.clear();
            adap.notifyDataSetChanged();
        }


        EditText queryTerm = (EditText) rootView.findViewById(R.id.search);
        queryTerm.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                String term = v.getText().toString();
                searchArtist(term);
                return false;
            }
        });
        queryTerm.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    ((EditText) v).setText("");
                }
                return false;
            }
        });

        queryTerm.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    ((EditText) v).setText("");
                }
            }
        });


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                SpotifyObject artist = adapter.getItem(position);
                artistId = artist.getId();
                artistName = artist.getName();
                String[] paramsArray = new String[2];
                paramsArray[0] = artistId;
                paramsArray[1] = artistName;
                searchTracks(paramsArray);
                mPosition = position;
            }
        });


        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {

            mPosition = savedInstanceState.getInt(SELECTED_KEY);

        }
        return rootView;
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mArtists = savedInstanceState.getParcelableArrayList("artists");
            adapter.clear();
            if (mArtists != null) {
                adapter.addAll(mArtists);
                adapter.notifyDataSetChanged();
            }

        }
        super.onViewStateRestored(savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        // When tablets rotate, the currently selected list item needs to be saved.
        // When no item is selected, mPosition will be set to Listview.INVALID_POSITION,
        // so check for that before storing.
        if (mArtists != null) {
            outState.putParcelableArrayList("artists", mArtists);
        }
        if (mPosition != ListView.INVALID_POSITION) {
            outState.putInt(SELECTED_KEY, mPosition);
        }
        super.onSaveInstanceState(outState);
    }

    //Execute Async Task
    private void searchArtist(String term) {
        SpotifyTask artistTask = new SpotifyTask();
        artistTask.execute(term);
    }

    //Execute Sync Task
    private void searchTracks(String[] params) {
        TracksTask topTracksTacks = new TracksTask();
        topTracksTacks.execute(params);
    }

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(ArtistSpotify obj);
    }

    //Async Task for Search an artist
    public class SpotifyTask extends AsyncTask<String, Void, ArrayList<SpotifyObject>> {

        @Override
        protected ArrayList<SpotifyObject> doInBackground(String... params) {

            String artistName = params[0];

            if (artistName != null && artistName.length() > 0) {
                try {
                    SpotifyApi api = new SpotifyApi();
                    SpotifyService spotify = api.getService();
                    ArtistsPager results = spotify.searchArtists(artistName);
                    List<Artist> listArtist = results.artists.items;
                    ArrayList<SpotifyObject> listSpotifyObjects = new ArrayList<>();

                    for (Artist element : listArtist) {

                        String name = element.name;
                        String id = element.id;
                        String url = "";
                        String urlThumb = "";
                        List<Image> images = element.images;
                        SpotifyObject obj = new ArtistSpotify(id, name, url, "", urlThumb, new ArrayList<TrackSpotify>());
                        url = obj.getImage(images, "original");
                        urlThumb = obj.getImage(images, "thumbnail");
                        obj.setUrl(url);
                        obj.setUrlThumb(urlThumb);
                        listSpotifyObjects.add(obj);
                        mArtists = listSpotifyObjects;
                    }
                    return listSpotifyObjects;
                } catch (RetrofitError error) {
                    Log.e("Error with Search API", error.getMessage());
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<SpotifyObject> result) {
            if (result != null && result.size() > 0) {

                adapter.clear();
                adapter.addAll(result);
                adapter.notifyDataSetChanged();


            } else {
                adapter.clear();
                Toast.makeText(getActivity().getApplicationContext(), "No artist found, please refine your search",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    /*Async Task Search top tracks*/
    public class TracksTask extends AsyncTask<String, Void, ArtistSpotify> {

        @Override
        protected ArtistSpotify doInBackground(String... params) {

            SpotifyApi api = new SpotifyApi();
            SpotifyService spotify = api.getService();
            HashMap<String, Object> queryString = new HashMap<>();
            queryString.put(SpotifyService.COUNTRY, Locale.getDefault().getCountry());

            try {
                Tracks tracksResults = spotify.getArtistTopTrack(artistId, queryString);
                List<Track> listTracks = tracksResults.tracks;
                ArrayList<TrackSpotify> listSpotifyObjects = new ArrayList<>();

                String nameArtist = params[1];
                String idArtist = params[0];

                for (Track element : listTracks) {
                    String name = element.name;
                    String id = element.id;
                    String albumName = element.album.name;
                    String url = "";
                    String urlThumb = "";
                    List<Image> images = element.album.images;
                    String preview = element.preview_url;
                    TrackSpotify obj = new TrackSpotify(id, name, url, albumName, urlThumb, preview);
                    url = obj.getImage(images, "original");
                    urlThumb = obj.getImage(images, "thumbnail");
                    obj.setUrl(url);
                    obj.setUrlThumb(urlThumb);
                    listSpotifyObjects.add(obj);
                }
                ArtistSpotify artistObj = new ArtistSpotify(idArtist, nameArtist, "", "", "", listSpotifyObjects);

                return artistObj;
            } catch (RetrofitError error) {
                Log.e("Retrofit Tracks API", error.getMessage());
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArtistSpotify result) {
            if (result != null && result.getTopTracks().size() > 0) {
                ((Callback) getActivity()).onItemSelected(result);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "No tracks available for this artist",
                        Toast.LENGTH_SHORT).show();
            }
        }

    }


}



