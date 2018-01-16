package com.djamzplayer.android.models;

import android.graphics.Bitmap;

/**
 * Created by ILENWABOR DAVID on 18/11/2017.
 */

public class Artists {
    private Bitmap artistCover;
    private String artistName;
    private String numberOfSongs;
    public Artists(String artistName, String numberOfSongs, Bitmap artistCover){
        this.artistCover = artistCover;
        this.artistName = artistName;
        this.numberOfSongs = numberOfSongs;
    }

    public Bitmap getArtistCover() {
        return artistCover;
    }

    public String getArtistName() {
        return artistName;
    }

    public String getNumberOfSongs() {
        return numberOfSongs;
    }
}
