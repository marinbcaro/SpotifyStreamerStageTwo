package com.example.android.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MediaPlayerActivity extends AppCompatActivity {

    boolean mIsLargeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_media_player);
        mIsLargeLayout = getResources().getBoolean(R.bool.large_layout);
        showEditDialog();
    }


    private void showEditDialog() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyDialogFragment newFragment = new MyDialogFragment();

        if (mIsLargeLayout) {
            // The device is using a large layout, so show the fragment as a dialog
            newFragment.show(fragmentManager, "dialog");
        } else {
            // The device is smaller, so show the fragment fullscreen
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            // For a little polish, specify a transition animation
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            // To make it fullscreen, use the 'content' root view as the container
            // for the fragment, which is always the root view for the activity
            transaction.add(android.R.id.content, newFragment)
                    .addToBackStack(null).commit();
        }


//        FragmentManager fragmentManager = getSupportFragmentManager();
//        MediaPlayerFragment newFragment = new MediaPlayerFragment();
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_media_player, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
