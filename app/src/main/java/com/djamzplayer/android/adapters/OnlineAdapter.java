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

public class OnlineAdapter extends RecyclerView.Adapter<OnlineAdapter.OnlineViewHolder> {
    // TODO: 11/5/2017 Remove dummy POJO
    private ArrayList<Song> mSongArrayList;
    private Context mContext;

    public OnlineAdapter(ArrayList<Song> mSongArrayList, Context mContext) {
        this.mSongArrayList = mSongArrayList;
        this.mContext = mContext;
    }

    @Override
    public OnlineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new OnlineViewHolder(LayoutInflater.from(mContext).inflate(com.djamzplayer.android.R.layout.item_grid, parent, false));
    }

    @Override
    public void onBindViewHolder(OnlineViewHolder holder, int position) {
        Song song = mSongArrayList.get(position);
        ImageView gridImageView = holder.gridImageView;
        TextView nameTextView = holder.nameTextView;

        nameTextView.setText(song.getSongName());


    }

    @Override
    public int getItemCount() {
        return mSongArrayList.size();
    }

    public class OnlineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
         ImageView gridImageView;
         TextView nameTextView;

        public OnlineViewHolder(View itemView) {
            super(itemView);
            gridImageView = (ImageView) itemView.findViewById(com.djamzplayer.android.R.id.grid_album_cover);
            nameTextView = (TextView) itemView.findViewById(com.djamzplayer.android.R.id.tv_song_name);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
