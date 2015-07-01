package com.example.android.spotifystreamer;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.spotifystreamer.data.SpotifyObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

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

    static class ViewHolder {

        @Bind(R.id.text_view_name) TextView name;
        @Bind(R.id.text_view_album) TextView album;
        @Bind(R.id.imageView) ImageView image;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        ViewHolder holder=null;

        SpotifyObject result = getItem(position);
        String url = result.getUrl();
        String name = result.getName();
        String album = result.getAlbumName();

        if(row==null){
            LayoutInflater inflater=((Activity)mContext).getLayoutInflater();
            row=inflater.inflate(mResource,parent,false);
            holder=new ViewHolder(row);
            row.setTag(holder);
        }else{
            holder=(ViewHolder)row.getTag();
        }

        if (album != "") {
            holder.album.setText(album);
        }
        holder.name.setText(name);
        holder.image.setImageBitmap(null);

        if (url !="") {
            Picasso.with(mContext).load(url).into(holder.image);
        } else {
            holder.image.setImageResource(R.drawable.placeholder);
        }

        return row;
    }

}
