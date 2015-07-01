package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.android.spotifystreamer.data.ArtistSpotify;
import com.example.android.spotifystreamer.data.SpotifyObject;
import com.example.android.spotifystreamer.data.TrackSpotify;

import java.util.ArrayList;

/**
 * Created by carolinamarin on 6/20/15.
 */
public class TracksFragment extends Fragment {


    private ResultsAdapter adapter;
    private ListView listView;
    private ArrayList<TrackSpotify> tracks;

    public TracksFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();

        View rootView = inflater.inflate(R.layout.fragment_tracks, container, false);

        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {

            ArtistSpotify myParcelableObject = intent.getParcelableExtra(Intent.EXTRA_TEXT);
            String artistName = myParcelableObject.getName();

            ((TracksActivity) getActivity()).setActionBarTitle(artistName);
            tracks = myParcelableObject.getTopTracks();

            ArrayList<SpotifyObject> childArray = (ArrayList<SpotifyObject>) ((ArrayList<?>) tracks);
            adapter = new ResultsAdapter(getActivity(), R.layout.list_item_main, childArray);

            listView = (ListView) rootView.findViewById(R.id.listview_tracks);
            listView.setAdapter(adapter);
        }

        return rootView;
    }

    public void onSaveInstanceState(Bundle outState) {
        if (tracks != null) {
            outState.putParcelableArrayList("tracks", tracks);
        }
        super.onSaveInstanceState(outState);
    }

    public void onViewStateRestored(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            tracks = savedInstanceState.getParcelableArrayList("tracks");
        }
        super.onViewStateRestored(savedInstanceState);
    }
}



