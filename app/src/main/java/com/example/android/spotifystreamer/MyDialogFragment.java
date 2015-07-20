package com.example.android.spotifystreamer;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.spotifystreamer.data.TrackSpotify;
import com.example.android.spotifystreamer.service.TestService;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MyDialogFragment extends DialogFragment {

    TestService mService;
    boolean mBound=false;
    private static  MediaPlayer   mediaPlayer;
    boolean  played=false;

    private ArrayList<TrackSpotify> mTracks=new ArrayList<>();
    private int position;
    boolean mIsLargeLayout;

    @Bind(R.id.artistName) TextView artistName;
    @Bind(R.id.albumName) TextView album;
    @Bind(R.id.trackName) TextView trackName;
    @Bind(R.id.progressUpdate) TextView trackProgress;
    @Bind(R.id.totalDuration) TextView trackDuration;
    @Bind(R.id.trackBar) SeekBar trackbar;
    @Bind(R.id.trackImage) ImageView trackImage;
    @Bind(R.id.edit_name) LinearLayout edit;
    @Bind(R.id.play) ImageButton btPlay;
    @Bind(R.id.next) ImageButton btNext;
    @Bind(R.id.previous) ImageButton btBack;
    @Bind(R.id.pause) ImageButton btPause;
    private Handler mHandler = new Handler();

    /** The system calls this to get the DialogFragment's layout, regardless
     of whether it's being displayed as a dialog or an embedded fragment. */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {




        View view = inflater.inflate(R.layout.fragment_media_player, container, false);

        ButterKnife.bind(this, view);

        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);

        if(mIsLargeLayout) {
            edit.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        }


//        String name = getArguments().getString("artistName");
////        getDialog().setTitle(title);
        mTracks = getArguments().getParcelableArrayList("tracksplayer");
//        position = getArguments().getInt("position");
//        TrackSpotify track=mTracks.get(position);
//        String url= track.getUrl();
//        String mAlbumName=track.getAlbumName();
//        String mtrackName=track.getName();
        position = getArguments().getInt("position");

        updateTrack(position);
     //  playSong(position,mTracks);




        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mBound) {
                 //   updateTrack();
                   playSong(position, mTracks);
                    btPlay.setVisibility(View.GONE);
                    btPause.setVisibility(View.VISIBLE);
                }
            }
            });

        btNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(mediaPlayer!=null) {
                    played = false;
                    mediaPlayer.stop();

                }
                    // check if next song is there or not
                    if (position < (mTracks.size() - 1)) {
                        position = position + 1;
                        updateTrack(position);
                        playSong(position, mTracks);

                    } else {
                        // play first song
                        position = 0;
                        updateTrack(position);
                        playSong(position, mTracks);
                    }
                }
            
        });

        btBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {

                if(mediaPlayer!=null) {
                    played = false;
                    mediaPlayer.stop();
                }

                    if (position > 0) {

                        position = position - 1;
                        updateTrack(position);
                        playSong(position, mTracks);

                    } else {
                        // play last song

                        position = mTracks.size() - 1;
                        updateTrack(position);
                        playSong(position, mTracks);

                    }
                }

        });



        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound) {
                    int num = mService.getRandomNumber();
                    playSong(position, mTracks);
                    btPlay.setVisibility(View.VISIBLE);
                    btPause.setVisibility(View.GONE);

//                  if(played==false) {
//                      mediaPlayer=new MediaPlayer();
//                      String url = "https://p.scdn.co/mp3-preview/f6cd2dac5beb16dc277d92ba4656df19765f9ab6";
//
//                      mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                      try {
//                          mediaPlayer.setDataSource(url);
//                          mediaPlayer.prepareAsync(); // might take long! (for buffering, etc)
//                          played=true;
//                      } catch (Exception e) {
//                          Log.d("error", e.getMessage());
//                      }
//
//                      mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                          public void onPrepared(MediaPlayer player) {
//                              mediaPlayer.start();
//                          }
//                      });
//
//
//                      mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                          @Override
//                          public boolean onError(MediaPlayer mp, int what, int extra) {
//                              // ... react appropriately ...
//                              // The MediaPlayer has moved to the Error state, must be reset!
//                              return false;
//                          }
//                      });
//                  }else{
//
//                      if(mediaPlayer.isPlaying()){
//                          mediaPlayer.pause();
//
//                      } else {
//
//                          mediaPlayer.start();
//
//                      }
//
//                  } if(played==false) {
//                        mediaPlayer=new MediaPlayer();
//                        String url = "https://p.scdn.co/mp3-preview/f6cd2dac5beb16dc277d92ba4656df19765f9ab6";
//
//                        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                        try {
//                            mediaPlayer.setDataSource(url);
//                            mediaPlayer.prepareAsync(); // might take long! (for buffering, etc)
//                            played=true;
//                        } catch (Exception e) {
//                            Log.d("error", e.getMessage());
//                        }
//
//                        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                            public void onPrepared(MediaPlayer player) {
//                                mediaPlayer.start();
//                            }
//                        });
//
//
//                        mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
//                            @Override
//                            public boolean onError(MediaPlayer mp, int what, int extra) {
//                                // ... react appropriately ...
//                                // The MediaPlayer has moved to the Error state, must be reset!
//                                return false;
//                            }
//                        });
//                    }else{
//
//                        if(mediaPlayer.isPlaying()){
//                            mediaPlayer.pause();
//
//                        } else {
//
//                            mediaPlayer.start();
//
//                        }
//
//                    }

//                     String url = "https://p.scdn.co/mp3-preview/f6cd2dac5beb16dc277d92ba4656df19765f9ab6"; // your URL here
//
//                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    try {
//                    mediaPlayer.setDataSource(url);
//// or just mediaPlayer.setDataSource(mFileName);
//                    mediaPlayer.prepareAsync(); // must call prepare first
//                    mediaPlayer.start(); // then start
//
//                    } catch (Exception e) {
//                        Log.e("MUSIC SERVICE", "Error setting data source", e);
//                    }
//

//                    String url = "https://dl.dropboxusercontent.com/u/10281242/sample_audio.mp3";
//                    mediaPlayer.reset();
//// Set type to streaming
//                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

// Listen for if the audio file can't be prepared
//                    mediaPlayer.setOnErrorListener(new OnErrorListener() {
//                        @Override
//                        public boolean onError(MediaPlayer mp, int what, int extra) {
//                            // ... react appropriately ...
//                            // The MediaPlayer has moved to the Error state, must be reset!
//                            return false;
//                        }
//                    });
//// Attach to when audio file is prepared for playing
//                    mediaPlayer.setOnPreparedListener(new OnPreparedListener() {
//                        @Override
//                        public void onPrepared(MediaPlayer mp) {
//                            mediaPlayer.start();
//                        }
//                    });
//                    try {
//// Set the data source to the remote URL
//                        mediaPlayer.setDataSource(url);
//                    } catch (Exception e) {
//                       Log.e("MUSIC SERVICE", "Error setting data source", e);
//                    }
//// Trigger an async preparation which will file listener when completed
//                    mediaPlayer.prepareAsync();
//                    mediaPlayer.start();
//



                  //  Toast.makeText(getActivity(), "number: " + num, Toast.LENGTH_SHORT).show();

                }

            }
        });
        // Inflate the layout to use as dialog or embedded fragment
        return view;
    }
//    public static MyDialogFragment  newInstance(String title) {
//        MyDialogFragment frag = new MyDialogFragment();
//        Bundle args = new Bundle();
//        args.putString("title", title);
//        frag.setArguments(args);
//        return frag;
//    }


 private void updateTrack(int position){
     String name = getArguments().getString("artistName");
//        getDialog().setTitle(title);
     mTracks = getArguments().getParcelableArrayList("tracksplayer");

     TrackSpotify track=mTracks.get(position);
     String url= track.getUrl();
     String mAlbumName=track.getAlbumName();
     String mtrackName=track.getName();

     Picasso.with(getActivity().getApplicationContext()).load(url).into(trackImage);
     album.setText(mAlbumName);
     trackName.setText(mtrackName);
     artistName.setText(name);
 }



    public static MyDialogFragment newInstance(ArrayList<TrackSpotify> tracks,int pos,String artistName) {
        MyDialogFragment frag = new MyDialogFragment();
        Bundle args = new Bundle();

        //String name=artist.getPreviewUrl();

        args.putParcelableArrayList("tracksplayer", tracks);
        args.putInt("position", pos);
        args.putString("artistName", artistName);
        frag.setArguments(args);

        return frag;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Bind to LocalService
        Intent intent = new Intent(getActivity(), TestService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mBound=true;
    }

    public void playSong(int pos,ArrayList<TrackSpotify> songs){
        TrackSpotify track=songs.get(pos);
        String url=track.getPreviewUrl();


        if(played==false) {
            mediaPlayer=new MediaPlayer();
            // String url = "https://p.scdn.co/mp3-preview/f6cd2dac5beb16dc277d92ba4656df19765f9ab6";

            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare(); // might take long! (for buffering, etc)
                played=true;
            } catch (Exception e) {
                Log.d("error", e.getMessage());
            }

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer player) {
                   // mediaPlayer=player;
                    player.start();
                  //  mHandler.postDelayed(mUpdateTimeTask, 100);
                    updateSeekbar();

                }
            });


            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                 //   mediaPlayer.reset();
                  //  mediaPlayer=mp;

                   // playSong(position,mTracks);
                    // ... react appropriately ...
                    // The MediaPlayer has moved to the Error state, must be reset!
                    return false;
                }
            });
        }else{

            if(mediaPlayer.isPlaying()){
                mediaPlayer.pause();

            } else {

                mediaPlayer.start();

            }

        }


    }

    private void updateSeekbar(){

        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {

                if (mediaPlayer != null) {
                    long totalDuration = mediaPlayer.getDuration();
                    long currentDuration = mediaPlayer.getCurrentPosition();

                    // Displaying Total Duration time
                    trackDuration.setText(""+milliSecondsToTimer(totalDuration));
                    // Displaying time completed playing
                    trackProgress.setText(""+milliSecondsToTimer(currentDuration));

                    // Updating progress bar
                    int progress = (int)(getProgressPercentage(currentDuration, totalDuration));
                    //Log.d("Progress", ""+progress);
                    trackbar.setProgress(progress);

                    // Running this thread after 100 milliseconds
                    mHandler.postDelayed(this, 100);


                }

            }
        });


        trackbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null && fromUser) {
                    mediaPlayer.seekTo(progress * 300);
                }
            }
        });



    }


    private Runnable mUpdateTimeTask = new Runnable() {
        public void run() {
            long totalDuration = mediaPlayer.getDuration();
            long currentDuration = mediaPlayer.getCurrentPosition();

            // Displaying Total Duration time
            trackDuration.setText(""+milliSecondsToTimer(totalDuration));
            // Displaying time completed playing
            trackProgress.setText(""+milliSecondsToTimer(currentDuration));

            // Updating progress bar
            int progress = (int) (getProgressPercentage(currentDuration, totalDuration));
            //Log.d("Progress", ""+progress);
            trackbar.setProgress(progress);

            // Running this thread after 100 milliseconds
            mHandler.postDelayed(this, 100);
        }
    };

    /**
     * Function to convert milliseconds time to
     * Timer Format
     * Hours:Minutes:Seconds
     * */
    public String milliSecondsToTimer(long milliseconds){
        String finalTimerString = "";
        String secondsString = "";

        // Convert total duration into time
        int hours = (int)( milliseconds / (1000*60*60));
        int minutes = (int)(milliseconds % (1000*60*60)) / (1000*60);
        int seconds = (int) ((milliseconds % (1000*60*60)) % (1000*60) / 1000);
        // Add hours if there
        if(hours > 0){
            finalTimerString = hours + ":";
        }

        // Prepending 0 to seconds if it is one digit
        if(seconds < 10){
            secondsString = "0" + seconds;
        }else{
            secondsString = "" + seconds;}

        finalTimerString = finalTimerString + minutes + ":" + secondsString;

        // return timer string
        return finalTimerString;
    }

    /**
     * Function to get Progress percentage
     * @param currentDuration
     * @param totalDuration
     * */
    public int getProgressPercentage(long currentDuration, long totalDuration){
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration / 1000);
        long totalSeconds = (int) (totalDuration / 1000);

        // calculating percentage
        percentage =(((double)currentSeconds)/totalSeconds)*100;

        // return percentage
        return percentage.intValue();
    }

    /**
     * Function to change progress to timer
     * @param progress -
     * @param totalDuration
     * returns current duration in milliseconds
     * */
    public int progressToTimer(int progress, int totalDuration) {
        int currentDuration = 0;
        totalDuration = (int) (totalDuration / 1000);
        currentDuration = (int) ((((double)progress) / 100) * totalDuration);

        // return current duration in milliseconds
        return currentDuration * 1000;
    }




        @Override
    public void onDestroy() {
            if (mBound) {
                getActivity().unbindService(mConnection);
                mediaPlayer.release();
                mBound = false;
            }
        super.onDestroy();
    }

//    @Override
//    public void onPause() {
//        super.onPause(); // Don't forget this line
//        if (mBound) {
//            getActivity().unbindService(mConnection);
//            mediaPlayer.release();
//
//            mBound = false;
//        }
//    }

    @Override
    public void onStop() {
        super.onStop();
        // Unbind from the service
        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if( mediaPlayer!=null){
            played=false;
           mediaPlayer.stop();
        }
        setRetainInstance(true);
    }
    @Override
    public void onDestroyView() {
        if (getDialog() != null && getRetainInstance()) {
            getDialog().setDismissMessage(null);
        }
        super.onDestroyView();
    }
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            TestService.LocalBinder binder = (TestService.LocalBinder) service;
            mService = binder.getService();
            mService.setList(mTracks);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };



}