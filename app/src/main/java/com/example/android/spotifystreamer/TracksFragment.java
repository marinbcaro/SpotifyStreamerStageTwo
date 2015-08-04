package com.example.android.spotifystreamer;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.android.spotifystreamer.data.ArtistSpotify;
import com.example.android.spotifystreamer.data.SpotifyObject;
import com.example.android.spotifystreamer.data.TrackSpotify;

import java.util.ArrayList;

/**
 * Created by carolinamarin on 6/20/15.
 */
public class TracksFragment extends Fragment {


    static final String DETAIL_URI = "URI";
    private static int mPosition;
    private static String artistName;
    boolean mIsLargeLayout;
    FragmentManager fragmentManager;
    private ResultsAdapter adapter;
    private ListView listView;
    private ArrayList<TrackSpotify> tracks;
    private ArtistSpotify obj;
    private Menu menu;

    public TracksFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle arguments = getArguments();
        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);

        if (arguments != null) {
            obj = arguments.getParcelable(TracksFragment.DETAIL_URI);
        }

        fragmentManager = getActivity().getSupportFragmentManager();
        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_tracks, container, false);

        if (obj != null) {
            final String artistName = obj.getName();
            ((ActionBarActivity) getActivity()).getSupportActionBar().setSubtitle(artistName);

            tracks = obj.getTopTracks();
            ArrayList<SpotifyObject> childArray = (ArrayList<SpotifyObject>) ((ArrayList<?>) tracks);
            adapter = new ResultsAdapter(getActivity(), R.layout.list_item_main, childArray);

            listView = (ListView) rootView.findViewById(R.id.listview_tracks);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    boolean netConnection = isNetworkAvailable();
                    if (netConnection) {
                        mPosition = position;
                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        MediaPlayerFragment newFragment = new MediaPlayerFragment().newInstance(tracks, mPosition, artistName);
                        newFragment.show(fragmentManager, "dialog");

                    } else {
                        Toast.makeText(getActivity().getApplicationContext(), "No Network Connection, Please try again later",
                                Toast.LENGTH_SHORT).show();
                    }


                }
            });

        } else {
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                ArtistSpotify myParcelableObject = intent.getParcelableExtra(Intent.EXTRA_TEXT);
                final String artistName = myParcelableObject.getName();
                ((ActionBarActivity) getActivity()).getSupportActionBar().setSubtitle(artistName);

                tracks = myParcelableObject.getTopTracks();

                ArrayList<SpotifyObject> childArray = (ArrayList<SpotifyObject>) ((ArrayList<?>) tracks);
                adapter = new ResultsAdapter(getActivity(), R.layout.list_item_main, childArray);

                listView = (ListView) rootView.findViewById(R.id.listview_tracks);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        boolean netConnection = isNetworkAvailable();
                        if (netConnection) {
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            MediaPlayerFragment newFragment = new MediaPlayerFragment().newInstance(tracks, position, artistName);
                            mPosition = position;
                            if (mIsLargeLayout) {
                                newFragment.show(fragmentManager, "dialog");
                            } else {

                                Bundle args = new Bundle();
                                args.putParcelableArrayList("tracksplayer", tracks);
                                args.putInt("position", position);
                                args.putString("artistName", artistName);

                                newFragment.setArguments(args);
                                FragmentTransaction transaction = fragmentManager.beginTransaction();
                                transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                                transaction.add(android.R.id.content, newFragment)
                                        .addToBackStack("addFrag").commit();
                            }
                        }
                    }
                });
            }
        }
        return rootView;
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}



