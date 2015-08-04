package com.example.android.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.android.spotifystreamer.data.ArtistSpotify;

public class MainActivity extends ActionBarActivity implements MainFragment.Callback {

    private static final String DETAILFRAGMENT_TAG = "DFTAG";
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (findViewById(R.id.containerTracks) != null) {
            mTwoPane = true;
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.containerTracks, new TracksFragment(), DETAILFRAGMENT_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }

    }


    @Override
    public void onItemSelected(ArtistSpotify obj) {
        if (mTwoPane) {
            Bundle args = new Bundle();
            args.putParcelable(TracksFragment.DETAIL_URI, obj);

            TracksFragment fragment = new TracksFragment();
            fragment.setArguments(args);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.containerTracks, fragment, DETAILFRAGMENT_TAG)
                    .commit();
        } else {
            Intent intent = new Intent(this, TracksActivity.class);
            intent.putExtra(Intent.EXTRA_TEXT, obj);
            startActivity(intent);
        }
    }

}
