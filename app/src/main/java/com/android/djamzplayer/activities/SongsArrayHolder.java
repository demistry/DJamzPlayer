package com.android.djamzplayer.activities;

import com.android.djamzplayer.models.Artists;
import com.android.djamzplayer.models.Songs;

import java.util.ArrayList;

/**
 * Created by ILENWABOR DAVID on 05/12/2017.
 */

public class SongsArrayHolder {
    private static SongsArrayHolder ourInstance;
    private ArrayList<Songs> songsArrayList;
    private ArrayList<Artists> artistsArrayList;

    public synchronized static SongsArrayHolder getInstance() {
        if (ourInstance == null){
            ourInstance = new SongsArrayHolder();
        }
        return ourInstance;
    }

    private SongsArrayHolder() {
    }
    public void setArrayList(ArrayList<Songs> songsArrayList){
        this.songsArrayList = songsArrayList;
    }
    public ArrayList<Songs> getArrayList(){
        return songsArrayList;
    }

    public ArrayList<Artists> getArtistsArrayList() {
        return artistsArrayList;
    }

    public void setArtistsArrayList(ArrayList<Artists> artistsArrayList) {
        this.artistsArrayList = artistsArrayList;
    }
}
