package com.djamzplayer.android.services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.Toast;


import com.djamzplayer.android.R;
import com.djamzplayer.android.activities.NowPlayingActivity;
import com.djamzplayer.android.models.Songs;
import com.djamzplayer.android.receiver.NowPlayingReceiver;
import com.djamzplayer.android.singletons.SongsArrayHolder;
import com.djamzplayer.android.singletons.UpdateNowPlayingViews;
import com.djamzplayer.android.utils.ContractClass;
import com.djamzplayer.android.utils.ReceiverFilter;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import static com.djamzplayer.android.utils.ContractClass.CANCEL;
import static com.djamzplayer.android.utils.ContractClass.NEXT;
import static com.djamzplayer.android.utils.ContractClass.PAUSE;
import static com.djamzplayer.android.utils.ContractClass.PLAY;
import static com.djamzplayer.android.utils.ContractClass.PREVIOUS;

/**
 * Created by ILENWABOR DAVID on 19/11/2017.
 */

public class LocalSongsService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener,
                                                            MediaPlayer.OnPreparedListener, MediaPlayer.OnSeekCompleteListener
{
    private static final int NOTIFICATION_ID = 100;
    //private SimpleExoPlayer exoPlayer;
    private MediaPlayer mediaPlayer;
    private ArrayList<Songs> songsArrayList;
    private static int songPosition;
    private final MusicBinder musicBinder = new MusicBinder();
    private static int currentPosition;
    private static int currentPlayingSong;
    private State mState = State.Retrieving;
    private RepeatState loopState = RepeatState.LoopOff;
    private boolean shuffle;
    private Random random = new Random();
    private SharedPreferences shufflePref;
    private String shuffleMode;
    private SharedPreferences.Editor shuffleEditor;
    private int loopingSong;
    BroadcastReceiver receiver;
    RemoteViews notifView;
    NotificationCompat.Builder mBuilder;
    private Notification notification;
    Intent notifIntent;
    boolean flag;


    private NotificationManager notificationManager;
    private Context context;


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
    private enum RepeatState{
        LoopOff,
        LoopAll,
        LoopOne
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.v("Log", "Service created");
        songPosition = 0;
        //flag = false;
        notifIntent = new Intent(this, NowPlayingActivity.class);
        shufflePref = getSharedPreferences(ContractClass.SHUFFLE_PREF, MODE_PRIVATE);
        shuffleMode = shufflePref.getString(ContractClass.SHUFFLE_STATE, null);
        if (shuffleMode!=null){
            switch (shuffleMode){
                case "shuffleOn": shuffle = true;
                    break;
                case "shuffleOff": shuffle = false;
                    break;
            }
        }
        else{
            shuffle = false;
        }

        if (mediaPlayer == null){
            mediaPlayer = new MediaPlayer();
        }
        else{
            mediaPlayer.reset();
        }

        mState = State.Created;
        //exoPlayer = ExoPlayerFactory.newSimpleInstance(this, new DefaultTrackSelector());
        initializeMediaPlayer();
        receiver = new NotificationBroadcastReceiver();
        registerReceiver(receiver, ReceiverFilter.getNotificationFilter());
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
        //mediaPlayer.stop();
        //mediaPlayer.release();
        return false;
    }

    //service callback methods
    public void setSongsSource(ArrayList<Songs> songsArrayList){
        this.songsArrayList = songsArrayList;
        //if ()flag = false;
    }
    public void initializeMediaPlayer(){
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);
        //mediaPlayer.setLooping(true);
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);

        //exoPlayer.addListener(this);
        //exoPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);

    }
    public void playSong(int position){
        int currentlyPlayingSongPosition = SongsArrayHolder.getInstance().getCurrentlyPlayingSongPosition();
        //!mState.equals(State.Paused) ||
        if ( (currentPlayingSong != position || mState.equals(State.Stopped)) && currentlyPlayingSongPosition!=position){
            mediaPlayer.reset();
            songPosition = position;
            SongsArrayHolder.getInstance().setCurrentlyPlayingSongPosition(songPosition);
            Songs currentSong = songsArrayList.get(position);
            long songId = currentSong.getSongId();
            Uri songUri = ContentUris.withAppendedId(ContractClass.BASE_PROVIDER_URI, songId);
            try
            {

                mediaPlayer.setDataSource(getApplicationContext(), songUri);
                mediaPlayer.prepare();
                mediaPlayer.start();
                mState = State.Playing;
                //flag = true;

            }
            catch (IOException e){
                e.printStackTrace();
            }

            //mState = State.Preparing;

        }
        else {
            if (loopState.equals(RepeatState.LoopOne) && position!=currentlyPlayingSongPosition){//use to play song if looping option is set
                Songs currentSong = songsArrayList.get(songPosition);
                long songId = currentSong.getSongId();
                Uri songUri = ContentUris.withAppendedId(ContractClass.BASE_PROVIDER_URI, songId);
                try
                {

                    mediaPlayer.setDataSource(getApplicationContext(), songUri);
                    mediaPlayer.prepare();
                    mediaPlayer.start();
                    mState = State.Playing;
                    //flag = true;

                }
                catch (IOException e){
                    e.printStackTrace();
                }
            }
            if (mState.equals(State.Playing))
            {
                //do nothing, this block solves the case of the notification pending intent always restarting
                // the song when it is playing and clicked
                Toast.makeText(getApplicationContext(), "Nothing should be done if this is working" + mState.toString(), Toast.LENGTH_SHORT).show();
            }
            if (mState.equals(State.Paused)){
                Toast.makeText(getApplicationContext(), "Paused and restarted" + mState.toString(), Toast.LENGTH_SHORT).show();
                mediaPlayer.start();
                mediaPlayer.seekTo(currentPosition);
                setNotifPlayVisibility();
                mState = State.Playing;
            }


        }
        setUpNotification();



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
        //flag = false;
        if (songPosition>0 && songPosition<=songsArrayList.size()){
            songPosition--;
            playSong(songPosition);
            //NowPlayingReceiver.setPosition(songPosition);
        }
        else {
            playSong(songPosition);
            Toast.makeText(getApplicationContext(), "No more previous Song", Toast.LENGTH_SHORT).show();
        }
        SongsArrayHolder.getInstance().setSelectedSongPosition(songPosition);
        mState = State.Playing;
    }
    public void playNext(){
        //flag = false;
        if (shuffle)
        {
            int shuffledSong = songPosition;
            while (shuffledSong == songPosition){
                shuffledSong = random.nextInt(songsArrayList.size());
            }
            songPosition = shuffledSong;
            loopingSong = songPosition;
            playSong(songPosition);
        }
        else{
            songPosition++;
            if (songPosition<songsArrayList.size()){
                loopingSong = songPosition;
                playSong(songPosition);
            }
            else{
                if (loopState.equals(RepeatState.LoopAll)){
                    songPosition=0;
                    loopingSong = songPosition;
                    playSong(songPosition);
                }
                if(loopState.equals(RepeatState.LoopOff)){
                    mediaPlayer.stop();
                    songPosition = 0;
                    loopingSong = songPosition;
                    mState = State.Stopped;
                }
                if (loopState.equals(RepeatState.LoopOne)){
                    loopingSong = songPosition;
                    SongsArrayHolder.getInstance().setCurrentlyPlayingSongPosition(songPosition);
                    //playSong(loopingSong);
                    playSong(SongsArrayHolder.getInstance().getCurrentlyPlayingSongPosition());
                }
            }
        }
        SongsArrayHolder.getInstance().setSelectedSongPosition(songPosition);
        UpdateNowPlayingViews.getInstance().setQueuedSongPosition(songPosition);
        mState = State.Playing;
    }
    public void setShuffle(){
        //shufflePref = getSharedPreferences(ContractClass.SHUFFLE_PREF, MODE_PRIVATE);
        shuffleEditor = shufflePref.edit();
        if (shuffle){
            shuffle = false;
            shuffleEditor.putString(ContractClass.SHUFFLE_STATE, "shuffleOff");
        }
        else {
            shuffle = true;
            shuffleEditor.putString(ContractClass.SHUFFLE_STATE, "shuffleOn");
        }
        shuffleEditor.apply();
    }
    public boolean isShuffleOn(){
        return shuffle;
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
//        stopForeground(true);
        notificationManager.cancel(NOTIFICATION_ID);
        unregisterReceiver(receiver);
        stopSelf();
        mState = State.Retrieving;
    }



    public void seekProgressOfSong(double progress){

            mediaPlayer.start();

            mediaPlayer.seekTo((int)progress);
            mState = State.Playing;

            //mediaPlayer.seekTo(progress);

    }
    public void setContext(Context context){

        this.context = context;
    }
    public int getMaxDurationOfSong(){
        return mediaPlayer.getDuration();
    }
    public int getCurrentPlaybackPosition(){
        return mediaPlayer.getCurrentPosition();
    }
    private void setUpNotification(){
        // TODO 1: Handle bugs in notification
        notifView = new RemoteViews(getPackageName(), R.layout.custom_notification_layout);
        notifIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0, notifIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        notifView.setTextViewText(R.id.notification_artist_name, songsArrayList.get(songPosition).getArtistName());
        notifView.setTextViewText(R.id.notification_song_name,songsArrayList.get(songPosition).getSongTitle());
        notifView.setImageViewBitmap(R.id.notification_image, songsArrayList.get(songPosition).getAlbumCover());


        mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.facebook)
                .setContent(notifView)
                .setContentIntent(pendingIntent);
        setListeners(notifView, this);


        notification = mBuilder.build();
        notification.flags |= Notification.FLAG_AUTO_CANCEL;

        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(NOTIFICATION_ID, notification);


            //nm.notify(NOTIFICATION_ID, );
                    //.setOngoing(true)
                    //.setLargeIcon(songsArrayList.get(songPosition).getAlbumCover())
                    //.setTicker(songsArrayList.get(songPosition).getSongTitle())
                    //.setContentIntent(pendingIntent)
                    //.setCustomContentView(notifView);
                    //.setContentText(songsArrayList.get(songPosition).getSongTitle());


        //startForeground(NOTIFICATION_ID,notification.build());
    }


    private void setListeners(RemoteViews notifView, LocalSongsService localSongsService) {
        Intent play = new Intent(PLAY);
        Intent pause = new Intent(PAUSE);
        Intent previous = new Intent(PREVIOUS);
        Intent next = new Intent(NEXT);
        Intent cancel =  new Intent(CANCEL);

        PendingIntent playPending = PendingIntent.getBroadcast(localSongsService,0, play, PendingIntent.FLAG_UPDATE_CURRENT);
        notifView.setOnClickPendingIntent(R.id.notification_play, playPending);

        PendingIntent pausePending = PendingIntent.getBroadcast(localSongsService,0, pause, PendingIntent.FLAG_UPDATE_CURRENT);
        notifView.setOnClickPendingIntent(R.id.notification_pause, pausePending);

        PendingIntent previousPending = PendingIntent.getBroadcast(localSongsService,0, previous, PendingIntent.FLAG_UPDATE_CURRENT);
        notifView.setOnClickPendingIntent(R.id.notification_previous, previousPending);

        PendingIntent nextPending = PendingIntent.getBroadcast(localSongsService,0, next, PendingIntent.FLAG_UPDATE_CURRENT);
        notifView.setOnClickPendingIntent(R.id.notification_next, nextPending);

        PendingIntent cancelPending = PendingIntent.getBroadcast(localSongsService,0, cancel, PendingIntent.FLAG_UPDATE_CURRENT);
        notifView.setOnClickPendingIntent(R.id.notification_cancel, cancelPending);
    }

    public void setLooping(){
        if (loopState.equals(RepeatState.LoopOff)){//set button to loop all songs at first click
            loopState = RepeatState.LoopAll;
            mediaPlayer.setLooping(false);
            Toast.makeText(this, "Loop All", Toast.LENGTH_SHORT).show();
        }
        else{
            if(loopState.equals(RepeatState.LoopAll)){//set button to loop song once at second click

                loopState = RepeatState.LoopOne;
                loopingSong = songPosition;
                mediaPlayer.setLooping(true);
                Toast.makeText(this, "Loop One", Toast.LENGTH_SHORT).show();
            }
            else{
                if(loopState.equals(RepeatState.LoopOne)){//set button to stop looping at third click
                    loopState = RepeatState.LoopOff;
                    mediaPlayer.setLooping(false);
                    Toast.makeText(this, "Looping Off", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    public int getLoopState(){
        switch (loopState){
            case LoopOff: return 0;
            case LoopOne: return 1;
            case LoopAll: return 2;
        }
        return 1;
    }

    //MediaPlayer Callback methods
    @Override
    public void onCompletion(MediaPlayer mp) {
        if(mediaPlayer.getCurrentPosition()!=0 && loopState.equals(RepeatState.LoopOff))
        {
            mp.reset();
            if (songPosition<=songsArrayList.size()){
                playNext();
            }
            else{
                mp.stop();
                songPosition = 0;
            }

        }
        if(mediaPlayer.getCurrentPosition()!=0 && loopState.equals(RepeatState.LoopOne))
        {
            mp.reset();
            //playSong(loopingSong);
            playSong(SongsArrayHolder.getInstance().getCurrentlyPlayingSongPosition());


//            if (songPosition<=songsArrayList.size()){
//                mp.reset();
//                playSong(loopingSong);
//            }
//            else {
//                mp.reset();
//                playSong(songPosition);
//            }
        }
        if(mediaPlayer.getCurrentPosition()!=0 && loopState.equals(RepeatState.LoopAll))//loop all working
        {
            if (songPosition>=songsArrayList.size()){
                mp.reset();
                mp.stop();
            }
            else{
                playNext();
            }

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
        //mediaPlayer.start();
//        setUpNotification();
//        setNotifPlayVisibility();
        mState = State.Playing;
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }

    public class NotificationBroadcastReceiver extends BroadcastReceiver {

        public NotificationBroadcastReceiver(){

        }
        @Override
        public void onReceive(Context context, Intent intent) {
            switch (intent.getAction()){
                case PLAY :
                    playSong(songPosition);
                    setNotifPlayVisibility();
                    Toast.makeText(context, "Play Song", Toast.LENGTH_SHORT).show();
                    break;
                case PAUSE :
                    if (mState.equals(State.Playing)){
                        pauseSong();
                        setNotifPauseVisibility();
                        //mState = State.Paused;
                        Toast.makeText(context, "Pause Song", Toast.LENGTH_SHORT).show();
                    }

                    break;
                case NEXT : playNext();
                    setNotifPlayVisibility();
                    Toast.makeText(context, "Next Song", Toast.LENGTH_SHORT).show();
                    break;
                case PREVIOUS : playPrev();
                    setNotifPlayVisibility();
                    NowPlayingReceiver.setPosition(songPosition);
                    Toast.makeText(context, "Prev Song", Toast.LENGTH_SHORT).show();
                    break;
                case CANCEL: stopForeground(true);
                    System.exit(0);
            }
        }
    }
    public void setNotifPlayVisibility(){
        notifView.setViewVisibility(R.id.notification_pause, View.VISIBLE);
        notifView.setViewVisibility(R.id.notification_play, View.GONE);
        notification = mBuilder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
    public void setNotifPauseVisibility(){
        notifView.setViewVisibility(R.id.notification_pause, View.GONE);
        notifView.setViewVisibility(R.id.notification_play, View.VISIBLE);
        notification = mBuilder.build();
        notificationManager.notify(NOTIFICATION_ID, notification);
    }
}
