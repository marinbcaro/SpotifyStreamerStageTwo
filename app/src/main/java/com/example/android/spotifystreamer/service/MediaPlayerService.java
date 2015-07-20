package com.example.android.spotifystreamer.service;

import android.app.Service;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import com.example.android.spotifystreamer.data.TrackSpotify;

import java.util.ArrayList;

/**
 * Created by carolinamarin on 7/5/15.
 */
public class MediaPlayerService extends Service implements
        MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
        MediaPlayer.OnCompletionListener {

    //media player
    private MediaPlayer player=new MediaPlayer();
    //song list
    private ArrayList<TrackSpotify> songs;
    private final IBinder musicBind = new MusicBinder();
    private boolean played=false;

    //current position
    private int songPosn;

    @Override
    public IBinder onBind(Intent arg0) {
        // TODO Auto-generated method stub
        return musicBind;
    }
    @Override
    public boolean onUnbind(Intent intent){
        player.stop();
        player.release();
        played=false;
        return false;
    }
    @Override
    public void onCompletion(MediaPlayer mp) {

    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
    mp.start();
    }
    public void setSong(int songIndex){
        songPosn=songIndex;
    }
    public void playSong(int position){
        TrackSpotify track=songs.get(position);
        String url=track.getPreviewUrl();


        if(played==false){

            //play a song
            // player.reset();

            // String url = "https://p.scdn.co/mp3-preview/f6cd2dac5beb16dc277d92ba4656df19765f9ab6"; // your URL here
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                player.setDataSource(url);
                player.prepareAsync(); // might take long! (for buffering, etc)
                played=true;
            } catch (Exception e) {
                Log.e("MUSIC SERVICE", "Error setting data source", e);
            }


        }else{
            if(player.isPlaying()){
                player.pause();

            } else {

                player.start();

            }

        }








        }



    public void onCreate(){
        //create the service
        //create the service
        super.onCreate();
//initialize position
        songPosn=0;
//create player
        player = new MediaPlayer();
        initMusicPlayer();
    }

    public void initMusicPlayer(){
        //set player properties
        player.setWakeMode(getApplicationContext(),
                PowerManager.PARTIAL_WAKE_LOCK);
        player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        player.setOnPreparedListener(this);
        player.setOnCompletionListener(this);
        player.setOnErrorListener(this);
    }
    public void setList(ArrayList<TrackSpotify> theSongs){
        songs=theSongs;
    }
    public class MusicBinder extends Binder {
       public  MediaPlayerService getService() {
            return MediaPlayerService.this;
        }
    }

}
