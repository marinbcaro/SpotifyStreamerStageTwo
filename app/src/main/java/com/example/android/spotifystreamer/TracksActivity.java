package com.example.android.spotifystreamer;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;


public class TracksActivity extends ActionBarActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.

            Bundle arguments = new Bundle();
            arguments.putParcelable(TracksFragment.DETAIL_URI, getIntent().getData());

            TracksFragment fragment = new TracksFragment();
            fragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.containerTracks, fragment)
                    .commit();
        }
    }


    public void setActionBarTitle(String artistName) {
        ActionBar ab = getSupportActionBar();
        ab.setSubtitle(artistName);
    }

}
