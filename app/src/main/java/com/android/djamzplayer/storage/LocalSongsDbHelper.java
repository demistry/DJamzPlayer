package com.android.djamzplayer.storage;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.android.djamzplayer.utils.ContractClass.SongEntry;

/**
 * Created by ILENWABOR DAVID on 15/11/2017.
 */

public class LocalSongsDbHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "SongsDatabase";

    public LocalSongsDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createDatabase = "CREATE TABLE "+ SongEntry.SONG_TABLE + "("  +
                SongEntry.TABLE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                SongEntry.SONG_TITLE + " TEXT," +
                SongEntry.ARTIST_NAME + " TEXT," +
                SongEntry.SONG_DURATION + " TEXT," +
                SongEntry.SONG_ID + " INTEGER);";
        db.execSQL(createDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
