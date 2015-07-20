package com.example.android.spotifystreamer.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;


/**
 * Created by carolinamarin on 6/24/15.
 */
public class ArtistSpotify extends SpotifyObject {

    private ArrayList<TrackSpotify> topTracks = new ArrayList<TrackSpotify>();


    public ArtistSpotify(String id, String name, String url, String albumName,String urlThumb ,ArrayList<TrackSpotify> tracks) {
        super(id, name, url, albumName,urlThumb);
        this.topTracks = tracks;
    }

    public ArrayList<TrackSpotify> getTopTracks() {
        return topTracks;
    }

    public void setTopTracks(ArrayList<TrackSpotify> topTracks) {
        this.topTracks = topTracks;
    }


    //Parcelable Class Overwrites
    public static final Parcelable.Creator<ArtistSpotify> CREATOR = new Parcelable.Creator<ArtistSpotify>() {
        public ArtistSpotify createFromParcel(Parcel in) {
            return new ArtistSpotify(in);
        }

        public ArtistSpotify[] newArray(int size) {
            return new ArtistSpotify[size];
        }
    };

    public ArtistSpotify(Parcel in) {
        super(in);
        in.readTypedList(topTracks, TrackSpotify.CREATOR);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeTypedList(topTracks);
    }

}
