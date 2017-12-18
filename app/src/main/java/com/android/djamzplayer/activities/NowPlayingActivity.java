package com.android.djamzplayer.activities;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.android.djamzplayer.R;
import com.android.djamzplayer.models.Songs;
import com.android.djamzplayer.utils.LocalSongsQueryProvider;
import com.android.djamzplayer.services.LocalSongsService;


import java.util.ArrayList;


public class NowPlayingActivity extends AppCompatActivity implements ServiceConnection, SeekBar.OnSeekBarChangeListener
{

    private LocalSongsService localSongsService;
    private Intent playSongIntent;
    private int songPosition;
    private boolean isMusicBound = false;
    private static TextView songName;
    private static TextView artistName;
    private TextView currentPositionTextView;
    private static TextView maxPositionTextView;
    private static ImageView albumCover;
    private ImageView nextButton;
    private ImageView previousButton;
    private ToggleButton play_pauseButton;
    private SeekBar seekBar;
    private static ArrayList<Songs> songsArrayList;

    private Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.now_playing_layout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.now_playing_toolbar);
        setSupportActionBar(toolbar);

        songName = (TextView) findViewById(R.id.now_playing_song_name);
        artistName = (TextView) findViewById(R.id.now_playing_artist_name);
        currentPositionTextView = (TextView) findViewById(R.id.current_playback_textview);
        maxPositionTextView = (TextView) findViewById(R.id.max_playback_textview);
        albumCover = (ImageView) findViewById(R.id.now_playing_album_cover);
        nextButton = (ImageView) findViewById(R.id.next_button);
        previousButton = (ImageView) findViewById(R.id.previous_button);
        play_pauseButton = (ToggleButton) findViewById(R.id.playpause_button);
        seekBar = (SeekBar) findViewById(R.id.song_progress);


        seekBar.setOnSeekBarChangeListener(this);



        play_pauseButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(play_pauseButton.isChecked()){
                            localSongsService.pauseSong();
                            //playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_play_circle_outline_black_48dp));
                        }
                        else{
                            localSongsService.playSong(localSongsService.returnCurrentSong());
                            //playButton.setImageDrawable(getResources().getDrawable(R.drawable.ic_pause_circle_outline_black_24dp));
                        }

                    }
                }
        );
        nextButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        localSongsService.playNext();
                        setViews(localSongsService.returnCurrentSong());
                    }
                }
        );
        previousButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        localSongsService.playPrev();
                        setViews(localSongsService.returnCurrentSong());
                    }
                }
        );

        Bundle bundle = getIntent().getExtras();
        songPosition = bundle.getInt("Position");
        songsArrayList = SongsArrayHolder.getInstance().getArrayList();
        songName.setText(songsArrayList.get(songPosition).getSongTitle());
        artistName.setText(songsArrayList.get(songPosition).getArtistName());
        maxPositionTextView.setText(songsArrayList.get(songPosition).getSongDuration());
        albumCover.setImageBitmap(songsArrayList.get(songPosition).getAlbumCover());
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (playSongIntent == null){
            playSongIntent = new Intent(NowPlayingActivity.this, LocalSongsService.class);
            bindService(playSongIntent, this, Context.BIND_AUTO_CREATE);
            startService(playSongIntent);
        }
    }

    public static void setViews(int currentPosition){
        songName.setText(songsArrayList.get(currentPosition).getSongTitle());
        artistName.setText(songsArrayList.get(currentPosition).getArtistName());
        maxPositionTextView.setText(songsArrayList.get(currentPosition).getSongDuration());
        albumCover.setImageBitmap(songsArrayList.get(currentPosition).getAlbumCover());
    }

    @Override
    public void onServiceConnected(ComponentName name, IBinder service) {
        LocalSongsService.MusicBinder musicBinder = (LocalSongsService.MusicBinder) service;
        localSongsService = musicBinder.getService();
        Log.v("Log", "Service connection created");
        localSongsService.setSongsSource(songsArrayList);
        localSongsService.playSong(songPosition);
        isMusicBound = true;
        seekBar.setMax(100);
        //int maxposition = localSongsService.getMaxDurationOfSong()/1000;
        int playbackposition = getProgressPercentage(localSongsService.getCurrentPlaybackPosition(), localSongsService.getMaxDurationOfSong());
        seekBar.setProgress(playbackposition);
        currentPositionTextView.setText(LocalSongsQueryProvider.getFormattedSongDuration(localSongsService.getCurrentPlaybackPosition()));
        handler.removeCallbacks(moveSeekBar);
        handler.postDelayed(moveSeekBar, 100);
    }


    @Override
    public void onServiceDisconnected(ComponentName name) {

        isMusicBound = false;
    }


    private Runnable moveSeekBar = new Runnable() {
        @Override
        public void run() {
            if (localSongsService.getPlayingState()){
                int newCurrentPosition = getProgressPercentage(localSongsService.getCurrentPlaybackPosition(), localSongsService.getMaxDurationOfSong());
                int newMaxPosition = localSongsService.getMaxDurationOfSong();
                //seekBar.setMax(newCurrentPosition);
                currentPositionTextView.setText(LocalSongsQueryProvider.getFormattedSongDuration(localSongsService.getCurrentPlaybackPosition()));
                seekBar.setProgress(newCurrentPosition);

                Log.d("Log", "Max duration thread " +String.valueOf(localSongsService.getMaxDurationOfSong()));
                Log.d("Log", "Current time position is thread "+String.valueOf(localSongsService.getCurrentPlaybackPosition()));
                Log.d("Log", "Current time position of Seekbar thread "+seekBar.getProgress());
            }
            handler.postDelayed(moveSeekBar, 100);
        }

    };

    public int getProgressPercentage(long currentDuration1, long totalDuration1) {
        Double percentage = (double) 0;

        long currentSeconds = (int) (currentDuration1 / 1000);
        long totalSeconds = (int) (totalDuration1 / 1000);

        // calculating percentage
        percentage = (((double) currentSeconds) / totalSeconds) * 100;

        // return percentage
        return percentage.intValue();
    }

    //Methods for attaching Seekbar Changes to audio playing
    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        Log.d("Tag", "Max duration " +String.valueOf(localSongsService.getMaxDurationOfSong()));
        Log.d("Tag", "Current time position is "+String.valueOf(localSongsService.getCurrentPlaybackPosition()));
        Log.d("Tag", "Current time position of Seekbar "+seekBar.getProgress());
        Log.d("Tag", "progress of Seekbar "+progress);
        Log.d("Tag", "progress multiplier of Seekbar "+((progress*localSongsService.getMaxDurationOfSong())/100));
        if(fromUser){
            localSongsService.seekProgressOfSong((double)((progress*localSongsService.getMaxDurationOfSong())/100));
            currentPositionTextView.setText(LocalSongsQueryProvider.getFormattedSongDuration(progress*1000));
            seekBar.setProgress(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}
