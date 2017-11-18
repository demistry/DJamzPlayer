package com.android.djamzplayer.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Cyberman on 11/5/2017.
 */

public class Song implements Parcelable {
    private String songName;
    private String coverUrl;

    public static ArrayList<Song> genDummySongs(int number){
        ArrayList<Song> songArrayList = new ArrayList<>();
        if(number == 0){
            return songArrayList;
        }
        for (int i = 0; i < number ; i++) {
            Song song = new Song();
            String songName = i%2 == 0 ? "Palus, diatria, et bursa." : "Bi-color adiurators ducunt ad elogium.";
            song.setSongName(songName);
            songArrayList.add(song);
        }
        return songArrayList;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.songName);
        dest.writeString(this.coverUrl);
    }

    public Song() {
    }

    protected Song(Parcel in) {
        this.songName = in.readString();
        this.coverUrl = in.readString();
    }

    public static final Parcelable.Creator<Song> CREATOR = new Parcelable.Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}
