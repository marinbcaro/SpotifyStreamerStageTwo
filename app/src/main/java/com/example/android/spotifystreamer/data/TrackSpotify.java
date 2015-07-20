package com.example.android.spotifystreamer.data;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by carolinamarin on 6/24/15.
 *
 */
public class TrackSpotify extends SpotifyObject {

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }

    private String previewUrl;

    //Parcelable Class Overwrites
    public static final Parcelable.Creator<TrackSpotify> CREATOR = new Parcelable.Creator<TrackSpotify>() {
        public TrackSpotify createFromParcel(Parcel in) {
            return new TrackSpotify(in);
        }

        public TrackSpotify[] newArray(int size) {
            return new TrackSpotify[size];
        }
    };

    public TrackSpotify(String id, String name, String url, String albumName, String urlThumb,String prev) {
        super(id, name, url, albumName,urlThumb);
        this.previewUrl=prev;

    }

    private TrackSpotify(Parcel in) {
        super(in);
        this.previewUrl = in.readString();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel out, int flags) {
        super.writeToParcel(out, flags);
        out.writeString(previewUrl);

    }
}