package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.spotifystreamer.data.ArtistSpotify;
import com.example.android.spotifystreamer.data.SpotifyObject;
import com.example.android.spotifystreamer.data.TrackSpotify;

import java.util.ArrayList;

/**
 * Created by carolinamarin on 6/20/15.
 */
public class TracksFragment extends Fragment {


    static final String DETAIL_URI = "URI";
    boolean mIsLargeLayout;
    private ResultsAdapter adapter;
    private ListView listView;
    private ArrayList<TrackSpotify> tracks;
    private ArtistSpotify obj;


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


        Intent intent = getActivity().getIntent();
        View rootView = inflater.inflate(R.layout.fragment_tracks, container, false);

        if (obj != null) {
            final String artistName = obj.getName();
            ((TracksActivity) getActivity()).setActionBarTitle(artistName);
            tracks = obj.getTopTracks();
            ArrayList<SpotifyObject> childArray = (ArrayList<SpotifyObject>) ((ArrayList<?>) tracks);
            adapter = new ResultsAdapter(getActivity(), R.layout.list_item_main, childArray);

            listView = (ListView) rootView.findViewById(R.id.listview_tracks);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    MediaPlayerFragment newFragment = new MediaPlayerFragment().newInstance(tracks, position, artistName);
                    newFragment.show(fragmentManager, "dialog");


                }
            });

        } else {
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                ArtistSpotify myParcelableObject = intent.getParcelableExtra(Intent.EXTRA_TEXT);
                final String artistName = myParcelableObject.getName();
                ((TracksActivity) getActivity()).setActionBarTitle(artistName);
                tracks = myParcelableObject.getTopTracks();

                ArrayList<SpotifyObject> childArray = (ArrayList<SpotifyObject>) ((ArrayList<?>) tracks);
                adapter = new ResultsAdapter(getActivity(), R.layout.list_item_main, childArray);

                listView = (ListView) rootView.findViewById(R.id.listview_tracks);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                        MediaPlayerFragment newFragment = new MediaPlayerFragment().newInstance(tracks, position, artistName);

                        if (mIsLargeLayout) {
                            newFragment.show(fragmentManager, "dialog");
                        } else {
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
                            transaction.add(android.R.id.content, newFragment)
                                    .addToBackStack(null).commit();
                        }
                    }
                });
            }
        }
        return rootView;
    }
}



