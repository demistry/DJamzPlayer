package com.android.djamzplayer.utils;

import android.provider.BaseColumns;

/**
 * Created by ILENWABOR DAVID on 15/11/2017.
 */

public class ContractClass {
    private ContractClass(){

    }
    public static final class SongEntry implements BaseColumns{
        public static final String SONG_TABLE = "SONGTABLE";
        public static final String TABLE_ID = BaseColumns._ID;
        public static final String SONG_TITLE = "songTitle";
        public static final String ARTIST_NAME = "artistName";
        public static final String SONG_DURATION = "songDuration";
        public static final String SONG_ID = "songID";
    }
}
