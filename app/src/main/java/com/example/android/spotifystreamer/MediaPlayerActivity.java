package com.example.android.spotifystreamer;


import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.android.spotifystreamer.data.TrackSpotify;

import java.util.ArrayList;


public class MediaPlayerActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getIntent().getExtras();

        ArrayList<TrackSpotify> arr = args.getParcelableArrayList("tracksplayer");
        Integer pos = (Integer) args.get("position");
        String artist = (String) args.get("artistName");
        FragmentManager fragmentManager = getSupportFragmentManager();


        String menuFragment = getIntent().getStringExtra("artistName");

        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
       boolean mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);
        if (menuFragment != null) {
            MediaPlayerFragment newFragment = new MediaPlayerFragment().newInstance(arr, pos, artist);
            if(!mIsLargeLayout) {
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(android.R.id.content, newFragment).commit();
            }else{
                newFragment.show(fragmentManager, "dialog");

            }
        }

    }

}
