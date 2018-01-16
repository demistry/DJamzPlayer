package com.djamzplayer.android.utils;

import android.database.Cursor;
import android.provider.MediaStore;

import com.djamzplayer.android.models.Genres;

import java.util.ArrayList;

/**
 * Created by ILENWABOR DAVID on 19/11/2017.
 */

public class LocalGenreQueryProvider {
    private static ArrayList<Genres> genresArrayList;
    public static ArrayList<Genres> queryGenreProvider(Cursor cursor){
        genresArrayList = new ArrayList<>();
        if (cursor!= null && cursor.moveToFirst()){
            int genreNameColumnIndex = cursor.getColumnIndex(MediaStore.Audio.Genres.NAME);
            do{
                String genreName = cursor.getString(genreNameColumnIndex);
                genresArrayList.add(new Genres(null, genreName));
            } while(cursor.moveToNext());
            cursor.close();
        }
        return genresArrayList;
    }
}
