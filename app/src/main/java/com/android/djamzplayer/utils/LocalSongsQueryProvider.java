package com.android.djamzplayer.utils;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.CursorLoader;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.Loader;

import com.android.djamzplayer.models.Songs;
import com.android.djamzplayer.storage.LocalSongsDbHelper;
import com.android.djamzplayer.utils.ContractClass.SongEntry;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Locale;

import static com.android.djamzplayer.utils.ContractClass.BASE_PROVIDER_URI;

/**
 * Created by ILENWABOR DAVID on 03/11/2017.
 */

public class  LocalSongsQueryProvider extends CursorLoader {
    private static ArrayList<Songs> songsArrayList;
    private Context context;
    private static Cursor songsCursor;
    private Cursor savedCursor;
    private ProgressDialog dialog;
    private static SQLiteDatabase database;
    private static ContentValues contentValues;

    public LocalSongsQueryProvider(Context context) {
        super(context);
        this.context = context;
    }


    public static ArrayList<Songs> getLocalSongsArrayList(Cursor cursor, Context context){

        songsArrayList = new ArrayList<>();
        //Cursor songsCursor = queryAudioContentProvider(context);
        songsCursor = cursor;
        if (songsCursor!=null && songsCursor.moveToFirst()){
            int songsTitleColumn = songsCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int artistNameColumn = songsCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int albumIdColumn = songsCursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID);
            int songIdColumn = songsCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int songDurationColumn = songsCursor.getColumnIndex(MediaStore.Audio.Media.DURATION);
            do {
                String songTitle = songsCursor.getString(songsTitleColumn);
                String artistName = songsCursor.getString(artistNameColumn);
                Bitmap albumCover = getBitmapFromAlbumId(songsCursor.getLong(albumIdColumn), context);
                long songId = songsCursor.getLong(songIdColumn);
                String songDuration = getFormattedSongDuration(songsCursor.getLong(songDurationColumn));
                songsArrayList.add(new Songs(albumCover,songTitle,artistName,songDuration,songId));
                //saveSongsToDatabase(songTitle,artistName,songDuration,songId, context);
            }while(songsCursor.moveToNext());
        }
        //sort songs in alphabetical order with their title
        Collections.sort(songsArrayList, new Comparator<Songs>() {
            @Override
            public int compare(Songs songA, Songs songB) {
                return songA.getSongTitle().compareTo(songB.getSongTitle());
            }
        });
        return songsArrayList;
    }
//    private static void saveSongsToDatabase(String songTitle, String artistName, String songDuration, long songId, Context context){
//        LocalSongsDbHelper dbHelper = new LocalSongsDbHelper(context);
//        database = dbHelper.getWritableDatabase();
//        contentValues = new ContentValues();
//        contentValues.put(SongEntry.SONG_TITLE, songTitle);
//        contentValues.put(SongEntry.ARTIST_NAME, artistName);
//        contentValues.put(SongEntry.SONG_DURATION, songDuration);
//        contentValues.put(SongEntry.SONG_ID, songId);
//        database.insert(SongEntry.SONG_TABLE, null, contentValues);
//        database.close();
//    }
//    private static ArrayList<Songs> retrieveSongsFromDatabase(Context context){
//        LocalSongsDbHelper dbHelper = new LocalSongsDbHelper(context);
//        SQLiteDatabase database = dbHelper.getReadableDatabase();
//        Cursor cursor = database.query(SongEntry.SONG_TABLE, null, null, null, null, null, null);
//        ArrayList<Songs> arrayList = new ArrayList<>();
//        if (cursor!=null && cursor.moveToFirst()){
//            do {
//                String songTitle = cursor.getString(cursor.getColumnIndex(SongEntry.SONG_TITLE));
//                String artistName = cursor.getString(cursor.getColumnIndex(SongEntry.ARTIST_NAME));
//                //Bitmap albumCover = getBitmapFromAlbumId(cursor.getLong(albumIdColumn), context);
//                long songId = cursor.getLong(cursor.getColumnIndex(SongEntry.SONG_ID));
//                String songDuration = cursor.getString(cursor.getColumnIndex(SongEntry.SONG_DURATION));
//                arrayList.add(new Songs(null,songTitle,artistName,songDuration,songId));
//            }while(cursor.moveToNext());
//            cursor.close();
//        }
//        return arrayList;
//    }

    private static String getFormattedSongDuration(long songDuration) {
        long totalSeconds = songDuration/1000;
        long seconds = totalSeconds%60;
        long minutes = (totalSeconds%3600)/60;
        return String.format(Locale.ENGLISH,"%02d:%02d",minutes,seconds);
    }

    private static Bitmap getBitmapFromAlbumId(long albumID, Context context) {
        Bitmap bitmap = null;
        Uri uri = ContentUris.withAppendedId(Uri.parse("content://media/external/audio/albumart"), albumID);
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(uri);
            bitmap = BitmapFactory.decodeStream(inputStream);
        }catch(FileNotFoundException f){
            f.printStackTrace();
        }
        return bitmap;
    }

    private static Cursor queryAudioContentProvider(Context context) {
        ContentResolver resolver = context.getContentResolver();
        String [] projections = {MediaStore.Audio.Media.TITLE, MediaStore.Audio.Media.ARTIST,
                MediaStore.Audio.Media.ALBUM_ID, MediaStore.Audio.Media._ID, MediaStore.Audio.Media.DURATION};
        return resolver.query(BASE_PROVIDER_URI,
                projections,
                null,
                null,
                null);
    }

    @Override
    public Cursor loadInBackground() {
        //return getLocalSongsArrayList();
        return queryAudioContentProvider(context);
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        dialog = new ProgressDialog(context);
        dialog.setMessage("Songs Loading");
        dialog.setTitle("Songs");
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        if (savedCursor!= null){
            dialog.show();
            deliverResult(savedCursor);
        }
        else{
            dialog.show();
            forceLoad();
        }


    }

    @Override
    public void deliverResult(Cursor cursor) {
        super.deliverResult(cursor);
        savedCursor = cursor;
        dialog.dismiss();
    }


    @Override
    public void stopLoading() {
        super.stopLoading();
        //songsCursor=null;
    }
}
