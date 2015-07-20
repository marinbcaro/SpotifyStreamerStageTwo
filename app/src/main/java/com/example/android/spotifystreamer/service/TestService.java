package com.example.android.spotifystreamer.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.example.android.spotifystreamer.data.TrackSpotify;

import java.util.ArrayList;
import java.util.Random;

public class TestService extends Service {

    // Binder given to clients
    private final IBinder mBinder = new LocalBinder();
    // Random number generator
    private final Random mGenerator = new Random();
    private ArrayList<TrackSpotify> songs;
    private static MediaPlayer mediaPlayer;
    private boolean played;

    public TestService() {
    }

    public class LocalBinder extends Binder {
        public TestService getService() {
            // Return this instance of LocalService so clients can call public methods
            return TestService.this;
        }
    }


    public void setList(ArrayList<TrackSpotify> theSongs){
        songs=theSongs;
    }

    @Override
    public IBinder onBind(Intent intent) {
      return mBinder;
    }
    /** method for clients */
    public int getRandomNumber() {
        return mGenerator.nextInt(100);
    }
}
