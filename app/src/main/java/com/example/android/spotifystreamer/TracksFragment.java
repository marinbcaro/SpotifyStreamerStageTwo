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
    private ResultsAdapter adapter;
    private ListView listView;
    private ArrayList<TrackSpotify> tracks;
    private ArtistSpotify obj;
    boolean mIsLargeLayout;

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

        // if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
        if (obj != null) {

            final String artistName = obj.getName();
         //     ((TracksActivity) getActivity()).setActionBarTitle(artistName);
            tracks = obj.getTopTracks();
            ArrayList<SpotifyObject> childArray = (ArrayList<SpotifyObject>) ((ArrayList<?>) tracks);
            adapter = new ResultsAdapter(getActivity(), R.layout.list_item_main, childArray);

            listView = (ListView) rootView.findViewById(R.id.listview_tracks);
            listView.setAdapter(adapter);

//            ArtistSpotify myParcelableObject = intent.getParcelableExtra(Intent.EXTRA_TEXT);
//            String artistName = myParcelableObject.getName();
//
//            ((TracksActivity) getActivity()).setActionBarTitle(artistName);
//            tracks = myParcelableObject.getTopTracks();
//
//            ArrayList<SpotifyObject> childArray = (ArrayList<SpotifyObject>) ((ArrayList<?>) tracks);
//            adapter = new ResultsAdapter(getActivity(), R.layout.list_item_main, childArray);
//
//            listView = (ListView) rootView.findViewById(R.id.listview_tracks);
//            listView.setAdapter(adapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

//                    MediaPlayerFragment dialog = MediaPlayerFragment.newInstance();
           //        FragmentManager fm=getActivity().getFragmentManager();
//                    dialog.showEditDialog();
                    //FragmentManager fm = getActivity().getSupportFragmentManager();
                    //MediaPlayerFragment editNameDialog = MediaPlayerFragment.newInstance(tracks,position);
                  //  editNameDialog.showEditDialog(fm);
                    //editNameDialog.show(fm, "fragment_edit_name");

//                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                    MyDialogFragment newFragment = MyDialogFragment.newInstance("test");
//                    FragmentTransaction transaction = fragmentManager.beginTransaction();
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                    transaction.add(android.R.id.content, newFragment)
//                            .addToBackStack(null).commit();

                 //   ((TracksActivity)getActivity()).showTest();
//                    ((TracksActivity) getActivity()).setActionBarTitle("Spotify Streamer");
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                  //  MediaPlayerFragment newFragment = MediaPlayerFragment.newInstance(tracks, position,artistName);
//                    FragmentTransaction transaction = fragmentManager.beginTransaction();
//                    transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                    transaction.add(android.R.id.content, newFragment)
//                            .addToBackStack(null).commit();

                    MyDialogFragment newFragment=new MyDialogFragment().newInstance(tracks,position,artistName);
                    newFragment.show(fragmentManager, "dialog");

//                    if (mIsLargeLayout) {
//                        // The device is using a large layout, so show the fragment as a dialog
//                        newFragment.show(fragmentManager, "dialog");
//                    } else {
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                        transaction.add(android.R.id.content, newFragment)
//                        .addToBackStack(null).commit();
//
//                    }






                }
            });

        } else {
            if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
                ArtistSpotify myParcelableObject = intent.getParcelableExtra(Intent.EXTRA_TEXT);
                final String artistName = myParcelableObject.getName();

          //      ((TracksActivity) getActivity()).setActionBarTitle(artistName);
                tracks = myParcelableObject.getTopTracks();

                ArrayList<SpotifyObject> childArray = (ArrayList<SpotifyObject>) ((ArrayList<?>) tracks);
                adapter = new ResultsAdapter(getActivity(), R.layout.list_item_main, childArray);

                listView = (ListView) rootView.findViewById(R.id.listview_tracks);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                      //  MediaPlayerFragment newFragment = MediaPlayerFragment.newInstance(tracks, position, artistName);

                       MyDialogFragment newFragment=new MyDialogFragment().newInstance(tracks,position,artistName);
                     //   newFragment.show(fragmentManager, "dialog");


                        if (mIsLargeLayout) {
                            // The device is using a large layout, so show the fragment as a dialog
                            newFragment.show(fragmentManager, "dialog");
                        } else {
                            FragmentTransaction transaction = fragmentManager.beginTransaction();
                            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);

                            transaction.add(android.R.id.content, newFragment)
                                    .addToBackStack(null).commit();

                        }

//                        ((TracksActivity) getActivity()).setActionBarTitle("Spotify Streamer");
//                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                        MediaPlayerFragment newFragment = MediaPlayerFragment.newInstance(tracks, position);
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                        transaction.add(android.R.id.content, newFragment)
//                                .addToBackStack(null).commit();
            //   ((TracksActivity) getActivity()).showEditDialog();

//                        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//                        MyDialogFragment newFragment = MyDialogFragment.newInstance("test");
//                        FragmentTransaction transaction = fragmentManager.beginTransaction();
//                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                        transaction.add(android.R.id.content, newFragment)
//                                .addToBackStack(null).commit();

                   //     ((TracksActivity)getActivity()).showTest();

//                        FragmentManager fm = getActivity().getSupportFragmentManager();
//                        MediaPlayerFragment dialog = MediaPlayerFragment.newInstance(tracks,position);
//                        FragmentTransaction transaction = fm.beginTransaction();
//                        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//                        transaction.add(R.id.tracks_fragment, dialog)
//                                .addToBackStack(null).commit();


                    }
                });

            }
        }

        return rootView;
    }


//    public void onSaveInstanceState(Bundle outState) {
//        if (tracks != null) {
//            outState.putParcelableArrayList("tracks", tracks);
//        }
//        super.onSaveInstanceState(outState);
//    }
//
//    public void onViewStateRestored(Bundle savedInstanceState) {
//        if (savedInstanceState != null) {
//            tracks = savedInstanceState.getParcelableArrayList("tracks");
//        }
//        super.onViewStateRestored(savedInstanceState);
//    }
}



