package com.example.android.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.spotifystreamer.data.SpotifyObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by carolinamarin on 6/17/15.
 */
public class ResultsAdapter extends ArrayAdapter<SpotifyObject> {

    private int mResource;
    private Context mContext;


    public ResultsAdapter(Context context, int resource, ArrayList<SpotifyObject> objects) {
        super(context, resource, objects);
        mResource = resource;
        mContext = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LinearLayout layout;


        SpotifyObject result = getItem(position);
        String url = result.getUrl();
        String name = result.getName();
        String album = result.getAlbumName();


        if (convertView == null) {
            layout = new LinearLayout(getContext());
            ((LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE))
                    .inflate(mResource, layout, true);
        } else {
            layout = (LinearLayout) convertView;
        }
        if (album != "") {
            ((TextView) layout.findViewById(R.id.text_view_album)).setText(album);
        }

        ((TextView) layout.findViewById(R.id.text_view_name)).setText(name);
        ImageView imageView = (ImageView) layout.findViewById(R.id.imageView);
        imageView.setImageBitmap(null);


        if (url !="") {

            Picasso.with(mContext).load(url).into(((ImageView) layout.findViewById(R.id.imageView)));
        } else {
            imageView.setImageResource(R.drawable.placeholder);
        }

        return layout;
    }

}
