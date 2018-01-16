package com.djamzplayer.android.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ToggleButton;


import com.djamzplayer.android.models.Songs;
import com.djamzplayer.android.utils.ContractClass;

import java.util.ArrayList;

public class NowPlayingReceiver extends BroadcastReceiver {
    private static int changedPosition;
    ToggleButton toggleButton;
    private final TextView songName;
    private final TextView artistName;
    private final TextView maxPositionTextView;
    private final ImageView albumCover;
    private final ArrayList<Songs> songsArrayList;
    private final int position;

    public NowPlayingReceiver(ToggleButton toggleButton, TextView songName, TextView artistName, TextView maxPositionTextView,
                              ImageView albumCover, ArrayList<Songs> songsArrayList, int position){
        this.toggleButton = toggleButton;
        this.songName = songName;
        this.artistName = artistName;
        this.maxPositionTextView = maxPositionTextView;
        this.albumCover = albumCover;
        this.songsArrayList = songsArrayList;
        this.position = position;
    }
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()){
            case ContractClass.PLAY :
                toggleButton.setChecked(false);
                break;
            case ContractClass.PAUSE :
                toggleButton.setChecked(true);
                break;
            case ContractClass.NEXT :
                toggleButton.setChecked(false);
                break;
            case ContractClass.PREVIOUS :
                updateViews();
                toggleButton.setChecked(false);
                break;
        }
    }
    public static void setPosition(int position){
        changedPosition = position;
    }
    private void updateViews(){
        songName.setText(songsArrayList.get(changedPosition).getSongTitle());
        artistName.setText(songsArrayList.get(changedPosition).getArtistName());
        maxPositionTextView.setText(songsArrayList.get(changedPosition).getSongDuration());
        albumCover.setImageBitmap(songsArrayList.get(changedPosition).getAlbumCover());

    }
}
