package com.djamzplayer.android.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.djamzplayer.android.models.Song;

import java.util.ArrayList;

/**
 * Created by Cyberman on 11/5/2017.
 */

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.LocalViewHolder> {
    // TODO: 11/5/2017 Remove dummy POJO
    private ArrayList<Song> mSongArrayList;
    private Context mContext;

    public LocalAdapter(ArrayList<Song> mSongArrayList, Context mContext) {
        this.mSongArrayList = mSongArrayList;
        this.mContext = mContext;
    }

    @Override
    public LocalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LocalViewHolder(LayoutInflater.from(mContext).inflate(com.djamzplayer.android.R.layout.item_local_song, parent, false));
    }

    @Override
    public void onBindViewHolder(LocalViewHolder holder, int position) {
        Song song = mSongArrayList.get(position);
        ImageView gridImageView = holder.gridImageView;
        TextView nameTextView = holder.nameTextView;
        TextView artistTextView = holder.artistTextView;
        TextView durationTextView = holder.durationTextView;

        nameTextView.setText(song.getSongName());
        artistTextView.setText("Caesium de velox solem");
        durationTextView.setText("3:45");


    }

    @Override
    public int getItemCount() {
        return mSongArrayList.size();
    }

    public class LocalViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        ImageView gridImageView;
        TextView nameTextView;
        TextView artistTextView;
        TextView durationTextView;

        public LocalViewHolder(View itemView) {
            super(itemView);
            gridImageView = (ImageView) itemView.findViewById(com.djamzplayer.android.R.id.img_music_cover);
            nameTextView = (TextView) itemView.findViewById(com.djamzplayer.android.R.id.tv_song_name);
            artistTextView = (TextView) itemView.findViewById(com.djamzplayer.android.R.id.tv_artist_name);
            durationTextView = (TextView) itemView.findViewById(com.djamzplayer.android.R.id.tv_duration);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
