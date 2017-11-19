package com.android.djamzplayer.utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import com.android.djamzplayer.models.Artists;

import java.util.ArrayList;

/**
 * Created by ILENWABOR DAVID on 18/11/2017.
 */

public class LocalArtistQueryProvider {
    private static ArrayList<Artists> artistsArrayList;
    public static ArrayList<Artists> queryCursor(Cursor cursor){
        artistsArrayList = new ArrayList<>();
        if (cursor!=null && cursor.moveToFirst()){
            int artistColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Albums.ARTIST);
            int albumArtColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Albums.ALBUM_ART);
            int artistNumberOfSongsIndex = cursor.getColumnIndex(MediaStore.Audio.Albums.NUMBER_OF_SONGS);
            do {
                String artistName = cursor.getString(artistColumnIndex);
                Bitmap albumArt = BitmapFactory.decodeFile(cursor.getString(albumArtColumnIndex));
                String numberOfSongs = String.valueOf(cursor.getInt(artistNumberOfSongsIndex));
                artistsArrayList.add(new Artists(artistName, numberOfSongs, albumArt));
            }while (cursor.moveToNext());
            cursor.close();
        }
        return artistsArrayList;
    }
}
