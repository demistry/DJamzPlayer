package com.android.djamzplayer.models;

import android.graphics.Bitmap;

/**
 * Created by ILENWABOR DAVID on 02/11/2017.
 */

public class Songs {
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
}
