package com.example.android.spotifystreamer.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

import kaaes.spotify.webapi.android.models.Image;

/**
 * Created by carolinamarin on 6/21/15.
 * This Class has to implement parceable in order to send an array list of objects from the main activity to
 * the tracks activity
 */
public abstract class SpotifyObject implements Parcelable {

    private String id;
    private String name;
    private String url;

    public String getUrlThumb() {
        return urlThumb;
    }

    public void setUrlThumb(String urlThumb) {
        this.urlThumb = urlThumb;
    }

    private String urlThumb;
    private String albumName;

    public SpotifyObject(String id, String name, String url, String albumName,String urlThum) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.albumName = albumName;
        this.urlThumb=urlThumb;
    }


    public String getId() {return id;}

    public void setId(String id) {this.id = id;}

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(String albumName) {this.albumName = albumName;}

    public String getName() {
        return name;
    }

    public void setName(String name) {this.name = name;}

    public String getImage(List<Image> images,String type) {
        if (images != null && images.size() > 0) {
            if (images.size() == 1) {
                return images.get(0).url;
            } else {
                int position = 0;
                for (int cont = 0; cont < images.size(); cont++) {
                    Image img = images.get(cont);
                    if(type=="thumb"){
                        if (img.width == 64) {
                            position = cont;
                        }
                    }
                    if(type=="regular"){
                        if (img.width == 300) {
                            position = cont;
                        }
                    }


                }
                return images.get(position).url;
            }
        } else {
            return "";
        }
    }
    //Parcel Overwrites
    public void writeToParcel(Parcel out, int flags) {
        out.writeString(id);
        out.writeString(name);
        out.writeString(url);
        out.writeString(albumName);
        out.writeString(urlThumb);
    }
    public SpotifyObject(Parcel in) {
        this.id = in.readString();
        this.name = in.readString();
        this.url = in.readString();
        this.albumName = in.readString();
        this.urlThumb=in.readString();

    }
    public int describeContents() {
        return 0;
    }

}
