package com.android.djamzplayer.utils;



import android.widget.ImageView;
import android.widget.TextView;

import com.android.djamzplayer.models.Songs;

import java.util.ArrayList;

/**
 * Created by ILENWABOR DAVID on 18/12/2017.
 */

public class UpdateNowPlayingUI {
    private TextView songName;
    private TextView artistName;
    private TextView maxPositionTextView;
    private ImageView albumCover;

    public void setViews(int currentPosition, TextView songName, TextView artistName, TextView maxPositionTextView,
                                ImageView albumCover, ArrayList<Songs> songsArrayList){
        songName.setText(songsArrayList.get(currentPosition).getSongTitle());
        artistName.setText(songsArrayList.get(currentPosition).getArtistName());
        maxPositionTextView.setText(songsArrayList.get(currentPosition).getSongDuration());
        albumCover.setImageBitmap(songsArrayList.get(currentPosition).getAlbumCover());
    }
}
