package com.example.android.spotifystreamer.data;

import android.media.MediaPlayer;

/**
 * Created by carolinamarin on 7/18/15.
 */
public class MediaPlayerObject {


    private static MediaPlayerObject instance;

    public String customVar;

    public static void initInstance() {
        if (instance == null) {
            // Create the instance
            instance = new MediaPlayerObject();
        }
    }

    public static MediaPlayerObject getInstance() {
        // Return the instance
        return instance;
    }

    private MediaPlayerObject() {
        // Constructor hidden because this is a singleton
    }

    public MediaPlayer customSingletonMethod() {
       MediaPlayer player=new MediaPlayer();
        return player;
    }
}