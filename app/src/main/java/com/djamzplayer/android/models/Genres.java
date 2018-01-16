package com.djamzplayer.android.models;

import android.graphics.Bitmap;

/**
 * Created by ILENWABOR DAVID on 19/11/2017.
 */

public class Genres {
    private Bitmap genreCover;
    private String genreName;
    public Genres(Bitmap genreCover, String genreName){
        this.genreCover = genreCover;
        this.genreName = genreName;
    }

    public Bitmap getGenreCover() {
        return genreCover;
    }

    public String getGenreName() {
        return genreName;
    }
}
