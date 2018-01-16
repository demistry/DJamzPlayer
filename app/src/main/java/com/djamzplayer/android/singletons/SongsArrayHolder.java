package com.djamzplayer.android.singletons;

import com.djamzplayer.android.models.Artists;
import com.djamzplayer.android.models.Songs;

import java.util.ArrayList;

/**
 * Created by ILENWABOR DAVID on 05/12/2017.
 */

public class SongsArrayHolder {
    private static SongsArrayHolder ourInstance;
    private ArrayList<Songs> songsArrayList;
    private ArrayList<Artists> artistsArrayList;
    private int selectedSongPosition;
    private int currentlyPlayingSongPosition;

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

    public int getSelectedSongPosition() {
        return selectedSongPosition;
    }

    public void setSelectedSongPosition(int selectedSongPosition) {
        this.selectedSongPosition = selectedSongPosition;
    }

    public int getCurrentlyPlayingSongPosition() {
        return currentlyPlayingSongPosition;
    }

    public void setCurrentlyPlayingSongPosition(int currentlyPlayingSongPosition) {
        this.currentlyPlayingSongPosition = currentlyPlayingSongPosition;
    }
}
