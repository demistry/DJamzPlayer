package com.android.djamzplayer.services;

import android.app.Service;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.widget.Toast;


import com.android.djamzplayer.activities.NowPlayingActivity;
import com.android.djamzplayer.models.Songs;
import com.android.djamzplayer.utils.ContractClass;


import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by ILENWABOR DAVID on 19/11/2017.
 */

public class LocalSongsService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener,
                                                            MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener{
    //private SimpleExoPlayer exoPlayer;
    private MediaPlayer mediaPlayer;
    private ArrayList<Songs> songsArrayList;
    private static int songPosition;
    private final MusicBinder musicBinder = new MusicBinder();
    private static int currentPosition;
    private static int currentPlayingSong;
    private State mState = State.Retrieving;

    private enum State{
        Retrieving, // the MediaRetriever is retrieving music
        Stopped, // media player is stopped and not prepared to play
        Preparing, // media player is preparing...
        Playing, // playback active (media player ready!). (but the media player may actually be
        // paused in this state if we don't have audio focus. But we stay in this state
        // so that we know we have to resume playback once we get focus back)
        Paused,
        // playback paused (media player ready!)
        Created
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("Log", "Service created");
        songPosition = 0;
        mediaPlayer = new MediaPlayer();
        mState = State.Created;
        //exoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        initializeMediaPlayer();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) { return this.musicBinder; }



    public class MusicBinder extends Binder {
        public LocalSongsService getService(){
            return LocalSongsService.this;
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        //mState = State.Stopped;
        return false;
    }

    //service callback methods
    public void setSongsSource(ArrayList<Songs> songsArrayList){
        this.songsArrayList = songsArrayList;
    }
    public void initializeMediaPlayer(){
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        mediaPlayer.setLooping(true);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);

        //exoPlayer.addListener(this);
        //exoPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    }
    public void playSong(int position){
        if (!mState.equals(State.Paused) || currentPlayingSong != position || mState.equals(State.Stopped) ){
            mediaPlayer.reset();
            songPosition = position;
            Songs currentSong = songsArrayList.get(position);
            long songId = currentSong.getSongId();
            Uri songUri = ContentUris.withAppendedId(ContractClass.BASE_PROVIDER_URI, songId);
            try{
                mediaPlayer.setDataSource(getApplicationContext(), songUri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();

            }
            catch (IOException e){
                e.printStackTrace();
            }

            mState = State.Preparing;
        }
        else {
            mediaPlayer.start();
            mediaPlayer.seekTo(currentPosition);
            mState = State.Playing;
        }


    }
    public void pauseSong(){
        if (mState.equals(State.Playing)){
            mediaPlayer.pause();
            currentPosition = mediaPlayer.getCurrentPosition();
            currentPlayingSong = songPosition;
            mState = State.Paused;
        }
    }
    public void playPrev(){
        if (songPosition>0 && songPosition<=songsArrayList.size()){
            songPosition--;
            playSong(songPosition);
        }
        else {
            playSong(songPosition);
            Toast.makeText(getApplicationContext(), "No more previous Song", Toast.LENGTH_SHORT).show();
        }
    }
    public void playNext(){
        songPosition++;
        if (songPosition<songsArrayList.size()){
            playSong(songPosition);
        }
        else{
            songPosition=0;
            playSong(songPosition);
        }
        NowPlayingActivity.setViews(songPosition);
    }
    public int returnCurrentSong(){
        return songPosition;
    }
    public boolean getPlayingState(){
        if (mState.equals(State.Playing))
        return true;
        else
        return false;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer!=null){
            //mediaPlayer.reset();
            //mediaPlayer.stop();
            mediaPlayer.release();
           // mediaPlayer = null;
        }
        mState = State.Retrieving;
    }


    public void seekProgressOfSong(double progress){

            mediaPlayer.start();
            mediaPlayer.seekTo((int)progress);
            mState = State.Playing;

            //mediaPlayer.seekTo(progress);

    }
    public int getMaxDurationOfSong(){
        return mediaPlayer.getDuration();
    }
    public int getCurrentPlaybackPosition(){
        return mediaPlayer.getCurrentPosition();
    }

    //MediaPlayer Callback methods
    @Override
    public void onCompletion(MediaPlayer mp) {
        if(mediaPlayer.getCurrentPosition()!=0)
        {
            mp.reset();
            playNext();
        }
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        mp.reset();
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
        mState = State.Playing;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }
}
