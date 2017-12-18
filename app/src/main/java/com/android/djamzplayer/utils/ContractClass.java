package com.android.djamzplayer.utils;

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
    public static final String SONG_INTENT_EXTRA_NAME = "song";
    public static final String SONG_INTENT_EXTRA_ARTIST = "artist";
    public static final String PARCELABLE_ARRAY_LIST = "ParcelableArrayList";

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
