package com.example.android.spotifystreamer.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.android.spotifystreamer.data.TrackSpotify;

import java.util.ArrayList;

public class PlayerService extends Service {

    private final IBinder mBinder = new LocalBinder();
    private ArrayList<TrackSpotify> songs;

    public PlayerService() {
    }

    public void setList(ArrayList<TrackSpotify> theSongs) {
        songs = theSongs;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    public class LocalBinder extends Binder {
        public PlayerService getService() {
            // Return this instance of LocalService so clients can call public methods
            return PlayerService.this;
        }
    }

}
