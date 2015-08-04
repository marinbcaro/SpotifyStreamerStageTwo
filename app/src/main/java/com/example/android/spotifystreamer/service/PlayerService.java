package com.example.android.spotifystreamer.service;

import android.app.NotificationManager;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

import com.example.android.spotifystreamer.data.TrackSpotify;

import java.util.ArrayList;

public class PlayerService extends Service {

    private static ArrayList<TrackSpotify> songs;
    private static MediaPlayer mediaPlayer;
    private static boolean played = false;
    private static int position;
    private static String artist;
    private final IBinder mBinder = new LocalBinder();
    private int songPosn;
    private NotificationManager mNotificationManager;

    public PlayerService() {
    }

    public void setList(ArrayList<TrackSpotify> theSongs) {
        songs = theSongs;
    }

    public void setPlayer(MediaPlayer m) {
        mediaPlayer = m;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public void playPause(MediaPlayer m) {
        mediaPlayer = m;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();

        } else {
            mediaPlayer.start();
        }
    }

    public void stop(MediaPlayer m) {
        mediaPlayer = m;
        mediaPlayer.stop();
    }

    public class LocalBinder extends Binder {
        public PlayerService getService() {
            // Return this instance of LocalService so clients can call public methods
            return PlayerService.this;
        }
    }

}
