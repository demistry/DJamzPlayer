package com.djamzplayer.android.utils;

import android.net.Uri;
import android.provider.BaseColumns;
import android.provider.MediaStore;

/**
 * Created by ILENWABOR DAVID on 15/11/2017.
 */

public class ContractClass {
    public static final Uri BASE_PROVIDER_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    public static final Uri ARTIST_PROVIDER_URI = MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI;
    public static final Uri GENRE_PROVIDER_BASE_URI = MediaStore.Audio.Genres.EXTERNAL_CONTENT_URI;
    public static final String SHUFFLE_PREF = "Shuffle";
    public static final String SHUFFLE_STATE = "Shuffling";
    public static final String LOOP_PREF = "Loop";
    public static final String LOOP_STATE = "LoopState";

    public static final String PLAY = "com.djamzplayer.android.play";
    public static final String NEXT = "com.djamzplayer.android.next";
    public static final String PREVIOUS = "com.djamzplayer.android.previous";
    public static final String PAUSE = "com.djamzplayer.android.pause";
    public static final String CANCEL = "com.djamzplayer.android.cancel";

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
