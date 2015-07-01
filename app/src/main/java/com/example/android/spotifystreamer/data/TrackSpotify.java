package com.example.android.spotifystreamer.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by carolinamarin on 6/24/15.
 *
 */
public class TrackSpotify extends SpotifyObject {

    //Parcelable Class Overwrites
    public static final Parcelable.Creator<TrackSpotify> CREATOR = new Parcelable.Creator<TrackSpotify>() {
        public TrackSpotify createFromParcel(Parcel in) {
            return new TrackSpotify(in);
        }

        public TrackSpotify[] newArray(int size) {
            return new TrackSpotify[size];
        }
    };

    public TrackSpotify(String id, String name, String url, String albumName) {
        super(id, name, url, albumName);

    }

    private TrackSpotify(Parcel in) {
        super(in);
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);

    }
}