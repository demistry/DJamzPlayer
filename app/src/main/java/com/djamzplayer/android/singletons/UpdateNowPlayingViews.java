package com.djamzplayer.android.singletons;

import android.widget.ImageView;
import android.widget.TextView;

import com.djamzplayer.android.models.Songs;

import java.util.ArrayList;

/**
 * Created by ILENWABOR DAVID on 19/12/2017.
 */

public class UpdateNowPlayingViews {
    private static final UpdateNowPlayingViews ourInstance = new UpdateNowPlayingViews();
    private TextView songName;
    private TextView artistName;
    private TextView maxPositionTextView;
    private ImageView albumCover;
    private ArrayList<Songs> songsArrayList;

    public static UpdateNowPlayingViews getInstance() {
        return ourInstance;
    }

    private UpdateNowPlayingViews() {
    }
    public void setQueuedSongPosition(int position){
        setViews(position, songName, artistName, maxPositionTextView, albumCover,SongsArrayHolder.getInstance().getArrayList());
    }

    public void setSongName(TextView songName) {
        this.songName = songName;
    }

    public void setArtistName(TextView artistName) {
        this.artistName = artistName;
    }

    public void setMaxPositionTextView(TextView maxPositionTextView) {
        this.maxPositionTextView = maxPositionTextView;
    }

    public void setAlbumCover(ImageView albumCover) {
        this.albumCover = albumCover;
    }
    public void setViews(int currentPosition, TextView songName, TextView artistName, TextView maxPositionTextView,
                         ImageView albumCover, ArrayList<Songs> songsArrayList){
        songName.setText(songsArrayList.get(currentPosition).getSongTitle());
        artistName.setText(songsArrayList.get(currentPosition).getArtistName());
        maxPositionTextView.setText(songsArrayList.get(currentPosition).getSongDuration());
        albumCover.setImageBitmap(songsArrayList.get(currentPosition).getAlbumCover());
    }

    public void setSongsArrayList(ArrayList<Songs> songsArrayList) {
        this.songsArrayList = songsArrayList;
    }
}
