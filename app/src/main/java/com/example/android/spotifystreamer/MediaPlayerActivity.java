package com.example.android.spotifystreamer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MediaPlayerActivity extends AppCompatActivity {

    boolean mIsLargeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);
    }

}
