package com.djamzplayer.android.models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ILENWABOR DAVID on 02/11/2017.
 */

public class Songs implements Parcelable {
    private Bitmap albumCover;
    private String songTitle;
    private String artistName;
    private String songDuration;
    private long songId;

    public Songs(Bitmap albumCover, String songTitle, String artistName, String songDuration, long songId){
        this.albumCover = albumCover;
        this.songTitle = songTitle;
        this.artistName = artistName;
        this.songDuration = songDuration;
        this.songId = songId;
    }

    protected Songs(Parcel in) {
        albumCover = in.readParcelable(Bitmap.class.getClassLoader());
        songTitle = in.readString();
        artistName = in.readString();
        songDuration = in.readString();
        songId = in.readLong();
    }

    public static final Creator<Songs> CREATOR = new Creator<Songs>() {
        @Override
        public Songs createFromParcel(Parcel in) {
            return new Songs(in);
        }

        @Override
        public Songs[] newArray(int size) {
            return new Songs[size];
        }
    };

    public Bitmap getAlbumCover() {
        return albumCover;
    }

    public String getSongTitle() {
        return songTitle;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getSongDuration() {
        return songDuration;
    }

    public long getSongId() {
        return songId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(albumCover, flags);
        dest.writeString(songTitle);
        dest.writeString(artistName);
        dest.writeString(songDuration);
        dest.writeLong(songId);
    }
}
