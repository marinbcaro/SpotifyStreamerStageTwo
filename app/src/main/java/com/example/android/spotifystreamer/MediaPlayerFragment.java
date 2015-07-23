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
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.android.spotifystreamer.data.TrackSpotify;
import com.example.android.spotifystreamer.service.PlayerService;
import com.example.android.spotifystreamer.data.Utility;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MediaPlayerFragment extends DialogFragment {

    private static MediaPlayer mediaPlayer;
    PlayerService mService;
    boolean mBound = false;
    boolean played = false;
    boolean mIsLargeLayout;
    @Bind(R.id.artistName)
    TextView artistName;
    @Bind(R.id.albumName)
    TextView album;
    @Bind(R.id.trackName)
    TextView trackName;
    @Bind(R.id.progressUpdate)
    TextView trackProgress;
    @Bind(R.id.totalDuration)
    TextView trackDuration;
    @Bind(R.id.trackBar)
    SeekBar trackbar;
    @Bind(R.id.trackImage)
    ImageView trackImage;
    @Bind(R.id.edit_name)
    LinearLayout edit;
    @Bind(R.id.play)
    ImageButton btPlay;
    @Bind(R.id.next)
    ImageButton btNext;
    @Bind(R.id.previous)
    ImageButton btBack;
    @Bind(R.id.pause)
    ImageButton btPause;
    private ArrayList<TrackSpotify> mTracks = new ArrayList<>();
    private int position = -1;
    private int positionArg;
    private Handler mHandler = new Handler();
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            mService = binder.getService();
            mService.setList(mTracks);
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }
    };

    public static MediaPlayerFragment newInstance(ArrayList<TrackSpotify> tracks, int pos, String artistName) {
        MediaPlayerFragment frag = new MediaPlayerFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("tracksplayer", tracks);
        args.putInt("position", pos);
        args.putString("artistName", artistName);
        frag.setArguments(args);
        return frag;
    }

    /**
     * The system calls this to get the DialogFragment's layout, regardless
     * of whether it's being displayed as a dialog or an embedded fragment.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_media_player, container, false);
        ButterKnife.bind(this, view);
        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);

        if (getDialog() != null) {
            getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        }

        mTracks = getArguments().getParcelableArrayList("tracksplayer");
        positionArg = getArguments().getInt("position");
        if (position == -1) {
            position = positionArg;
        }

        playSong(position, mTracks);
        updateTrack(position);


        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound) {
                    playPause();
                    btPlay.setVisibility(View.GONE);
                    btPause.setVisibility(View.VISIBLE);
                }
            }
        });

        btNext.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mediaPlayer != null) {
                    played = false;
                    mediaPlayer.stop();
                }
                if (position < (mTracks.size() - 1)) {
                    position = position + 1;
                    updateTrack(position);
                    playSong(position, mTracks);
                    btPlay.setVisibility(View.GONE);
                    btPause.setVisibility(View.VISIBLE);
                } else {
                    position = 0;
                    updateTrack(position);
                    playSong(position, mTracks);
                    btPlay.setVisibility(View.GONE);
                    btPause.setVisibility(View.VISIBLE);
                }
            }
        });

        btBack.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                if (mediaPlayer != null) {
                    played = false;
                    mediaPlayer.stop();
                }
                if (position > 0) {
                    position = position - 1;
                    updateTrack(position);
                    playSong(position, mTracks);
                    btPlay.setVisibility(View.GONE);
                    btPause.setVisibility(View.VISIBLE);
                } else {
                    position = mTracks.size() - 1;
                    updateTrack(position);
                    playSong(position, mTracks);
                    btPlay.setVisibility(View.GONE);
                    btPause.setVisibility(View.VISIBLE);
                }
            }

        });


        btPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mBound) {
                    playPause();
                    btPlay.setVisibility(View.VISIBLE);
                    btPause.setVisibility(View.GONE);
                }

            }
        });
        return view;
    }

    private void updateTrack(int position) {
        String name = getArguments().getString("artistName");
        mTracks = getArguments().getParcelableArrayList("tracksplayer");
        TrackSpotify track = mTracks.get(position);
        String url = track.getUrl();
        String mAlbumName = track.getAlbumName();
        String mtrackName = track.getName();
        Picasso.with(getActivity().getApplicationContext()).load(url).into(trackImage);
        album.setText(mAlbumName);
        trackName.setText(mtrackName);
        artistName.setText(name);
        if (mediaPlayer != null && mediaPlayer.isPlaying()) {
            btPlay.setVisibility(View.GONE);
            btPause.setVisibility(View.VISIBLE);
        } else {
            btPlay.setVisibility(View.GONE);
            btPause.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Intent intent = new Intent(getActivity(), PlayerService.class);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        mBound = true;
    }

    public void playSong(int pos, ArrayList<TrackSpotify> songs) {
        TrackSpotify track = songs.get(pos);
        String url = track.getPreviewUrl();
        if (played == false) {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            try {
                mediaPlayer.setDataSource(url);
                mediaPlayer.prepare();
                played = true;
            } catch (Exception e) {
                Log.d("error", e.getMessage());
            }

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer player) {
                    player.start();

                    updateSeekbar();

                }
            });


            mediaPlayer.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                @Override
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    return false;
                }
            });
        }

    }

    private void playPause() {
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();

        } else {
            mediaPlayer.start();
        }
    }

    private void updateSeekbar() {


        getActivity().runOnUiThread(new Runnable() {

            @Override
            public void run() {
                Utility timeMethods = new Utility();

                if (mediaPlayer != null) {
                    long totalDuration = mediaPlayer.getDuration();
                    long currentDuration = mediaPlayer.getCurrentPosition();

                    trackDuration.setText("" + timeMethods.milliSecondsToTimer(totalDuration));
                    trackProgress.setText("" + timeMethods.milliSecondsToTimer(currentDuration));

                    int progress = (int) (timeMethods.getProgressPercentage(currentDuration, totalDuration));
                    trackbar.setProgress(progress);
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

    @Override
    public void onDestroy() {
        if (mBound) {
            getActivity().unbindService(mConnection);
            mediaPlayer.release();
            mBound = false;
        }
        super.onDestroy();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mBound) {
            getActivity().unbindService(mConnection);
            mBound = false;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mediaPlayer != null) {
            played = false;
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


}