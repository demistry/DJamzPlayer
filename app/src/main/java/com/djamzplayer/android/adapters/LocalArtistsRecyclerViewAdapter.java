package com.djamzplayer.android.adapters;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.djamzplayer.android.R;
import com.djamzplayer.android.models.Artists;


import java.util.ArrayList;

/**
 * Created by ILENWABOR DAVID on 19/11/2017.
 */

public class LocalArtistsRecyclerViewAdapter extends RecyclerView.Adapter<LocalArtistsRecyclerViewAdapter.LocalArtistViewHolder> {
    private ArrayList<Artists> artistsArrayList;

    public LocalArtistsRecyclerViewAdapter(ArrayList<Artists> artistsArrayList){
        this.artistsArrayList = artistsArrayList;
    }
    @Override
    public LocalArtistViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_artist_local, parent, false);
        return new LocalArtistViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalArtistViewHolder holder, int position) {
        if (artistsArrayList.get(position).getArtistCover()!=null)holder.albumArt.setImageBitmap(artistsArrayList.get(position).getArtistCover());
        holder.noOfSongs.setText(artistsArrayList.get(position).getNumberOfSongs());
        holder.artistName.setText(artistsArrayList.get(position).getArtistName());
    }

    @Override
    public int getItemCount() {
        Log.v("Log", String.valueOf(artistsArrayList.size()));
        return artistsArrayList.size();
    }

    class LocalArtistViewHolder extends RecyclerView.ViewHolder {
        private ImageView albumArt;
        private TextView artistName, noOfSongs;
        public LocalArtistViewHolder(View itemView) {
            super(itemView);
            albumArt = (ImageView) itemView.findViewById(R.id.grid_artist_cover);
            artistName = (TextView) itemView.findViewById(R.id.grid_artist_name);
            noOfSongs = (TextView) itemView.findViewById(R.id.grid_artist_song_number);
        }
    }
}
