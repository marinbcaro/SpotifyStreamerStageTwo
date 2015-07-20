package com.example.android.spotifystreamer;

import android.app.Dialog;
import android.app.Fragment;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;

import com.example.android.spotifystreamer.data.TrackSpotify;
import com.example.android.spotifystreamer.service.MediaPlayerService;
import com.example.android.spotifystreamer.service.MediaPlayerService.MusicBinder;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link MediaPlayerFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link MediaPlayerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MediaPlayerFragment extends DialogFragment {


    private EditText mEditText;
    private MediaPlayerService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;
    private ArrayList<TrackSpotify> mTracks=new ArrayList<>();
    private int position;
    boolean mIsLargeLayout;

    @Bind(R.id.artistName) TextView artistName;
    @Bind(R.id.albumName) TextView album;
    @Bind(R.id.trackName) TextView trackName;
    @Bind(R.id.trackImage) ImageView trackImage;
    @Bind(R.id.edit_name) LinearLayout edit;


    @Override
    public void onStart() {
        super.onStart();
        if(playIntent==null){
            playIntent = new Intent(getActivity(), MediaPlayerService.class);
            getActivity().startService(playIntent);
            getActivity().bindService(playIntent, musicConnection, Context.BIND_AUTO_CREATE);

        }
    }

//    @Override
//    public void onStop() {
//        super.onStop();
//        getActivity().unbindService(musicConnection);
//
//    }


    public void stopSong(){
       // if(playIntent!=null) {

        //    getActivity().stopService(playIntent);
      //  getActivity().unbindService(musicConnection);
          //  getActivity().stopService(playIntent);
       //     musicSrv = null;
           // System.exit(0);
        //}
    }

    public void songPicked(View view){
        musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
       // musicSrv.playSong();
    }
public void startPlayer(View view){
//    Toast.makeText(getActivity().getApplicationContext(), "No tracks available for this artist",
//            Toast.LENGTH_SHORT).show();
}

    public MediaPlayerFragment() {
        // Empty constructor required for DialogFragment
    }

    public static MediaPlayerFragment newInstance(ArrayList<TrackSpotify> tracks,int pos,String artistName) {
        MediaPlayerFragment frag = new MediaPlayerFragment();
        Bundle args = new Bundle();

        //String name=artist.getPreviewUrl();

        args.putParcelableArrayList("tracksplayer", tracks);
        args.putInt("position", pos);
        args.putString("artistName", artistName);
        frag.setArguments(args);

        return frag;
    }


//    public void showEditDialog2() {
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        MyDialogFragment newFragment = new MyDialogFragment();
//
//        if (mIsLargeLayout) {
//            // The device is using a large layout, so show the fragment as a dialog
//            newFragment.show(fragmentManager, "dialog");
//        } else {
//            // The device is smaller, so show the fragment fullscreen
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            // For a little polish, specify a transition animation
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            // To make it fullscreen, use the 'content' root view as the container
//            // for the fragment, which is always the root view for the activity
//            transaction.add(android.R.id.content, newFragment)
//                    .addToBackStack(null).commit();
//        }
//    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_media_player, container, false);

        ButterKnife.bind(this, view);

        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);

        if(mIsLargeLayout) {
            edit.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        }


        Log.v("logTag", "getDialog(): " + getDialog());

       // if(mIsLargeLayout) {
            //mEditText = (EditText) view.findViewById(R.id.txt_your_name);
            String name = getArguments().getString("artistName");
//        getDialog().setTitle(title);
            mTracks = getArguments().getParcelableArrayList("tracksplayer");
            position = getArguments().getInt("position");
            TrackSpotify track=mTracks.get(position);
            String url= track.getUrl();
            ImageView img=(ImageView) view.findViewById(R.id.trackImage);
            Picasso.with(getActivity().getApplicationContext()).load(url).into(img);
            album.setText(track.getAlbumName());
            trackName.setText(track.getName());
            artistName.setText(name);

            // Show soft keyboard automatically
          //  mEditText.requestFocus();
       // }

        ImageButton btPlay=(ImageButton)view.findViewById(R.id.play);
        btPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
                musicSrv.playSong(position);
String test="";
            }
        });
        ImageButton btStop=(ImageButton)view.findViewById(R.id.pause);
        btStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //   musicSrv.setSong(Integer.parseInt(view.getTag().toString()));
                musicSrv.playSong(position);

            }
        });


      //  showEditDialog2();

//        getDialog().getWindow().setSoftInputMode(
//                WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        return view;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //menu item selected
//        switch (item.getItemId()) {
//            case R.id.action_shuffle:
//                //shuffle
//                break;
//            case R.id.action_end:
//                getActivity().stopService(playIntent);
//                musicSrv=null;
//                System.exit(0);
//                break;
//        }
//        if(item.getItemId()){
//            getActivity().stopService(playIntent);
//            musicSrv=null;
//            System.exit(0);
//        }
        return super.onOptionsItemSelected(item);
    }
//    @Override
//    public void onDestroy() {
////        getActivity().stopService(playIntent);
////        getActivity().unbindService(musicConnection);
////        musicSrv=null;
////        super.onDestroy();
//    }
    private ServiceConnection musicConnection = new ServiceConnection(){

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicBinder binder = (MusicBinder)service;
            //get service
            musicSrv = binder.getService();
            //pass list
            musicSrv.setList(mTracks);
            musicBound = true;
        }

        @Override

        public void onServiceDisconnected(ComponentName name) {
            musicSrv=null;
            musicBound = false;
        }
    };




//
//    private EditText mEditText;
//    boolean mIsLargeLayout;
//    Context mContext;
//
//
//    public MediaPlayerFragment() {
//      //  mContext=getActivity();
//        // Required empty public constructor
//    }
////    public static MediaPlayerFragment newInstance() {
////        MediaPlayerFragment f = new MediaPlayerFragment();
////        return f;
//    }
    public void showEditDialog() {


//
//        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
//        CustomDialogFragment newFragment = new CustomDialogFragment();
//
//        if (mIsLargeLayout) {
//            // The device is using a large layout, so show the fragment as a dialog
//            newFragment.show(fragmentManager, "dialog");
//        } else {
//            // The device is smaller, so show the fragment fullscreen
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            // For a little polish, specify a transition animation
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            // To make it fullscreen, use the 'content' root view as the container
//            // for the fragment, which is always the root view for the activity
//            transaction.add(android.R.id.content, newFragment)
//                    .addToBackStack(null).commit();
//        }

//        MediaPlayerFragment newFragment = new MediaPlayerFragment();
//
//        if (mIsLargeLayout) {
//            // The device is using a large layout, so show the fragment as a dialog
//           // newFragment.show(fragmentManager, "dialog");
//        } else {
//            // The device is smaller, so show the fragment fullscreen
//            FragmentTransaction transaction = fragmentManager.beginTransaction();
//            // For a little polish, specify a transition animation
//            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
//            // To make it fullscreen, use the 'content' root view as the container
//            // for the fragment, which is always the root view for the activity
//            transaction.add(android.R.id.content, newFragment)
//                    .addToBackStack(null).commit();
//        }
    }
//
//    /** The system calls this to get the DialogFragment's layout, regardless
//     of whether it's being displayed as a dialog or an embedded fragment. */
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);
//        showEditDialog();
//        // Inflate the layout to use as dialog or embedded fragment
//        return inflater.inflate(R.layout.fragment_media_player, container, false);
//    }
//
//    /** The system calls this only when creating the layout in a dialog. */
//    @Override
//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        // The only reason you might override this method when using onCreateView() is
//        // to modify any dialog characteristics. For example, the dialog includes a
//        // title by default, but your custom layout might not need it. So here you can
//        // remove the dialog title, but you must call the superclass to get the Dialog.
//        Dialog dialog = super.onCreateDialog(savedInstanceState);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        return dialog;
//    }
@Override
public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
    inflater.inflate(R.menu.menu_media_player, menu);
    super.onCreateOptionsMenu(menu, inflater);

}
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // The only reason you might override this method when using onCreateView() is
        // to modify any dialog characteristics. For example, the dialog includes a
        // title by default, but your custom layout might not need it. So here you can
        // remove the dialog title, but you must call the superclass to get the Dialog.
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }


}
