package com.android.djamzplayer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.djamzplayer.R;
import com.android.djamzplayer.models.Genres;

import java.util.ArrayList;

/**
 * Created by ILENWABOR DAVID on 19/11/2017.
 */

public class LocalGenresRecyclerViewAdapter extends RecyclerView.Adapter<LocalGenresRecyclerViewAdapter.LocalGenresViewHolder> {
    private ArrayList<Genres> genresArrayList;
    public LocalGenresRecyclerViewAdapter(ArrayList<Genres> genresArrayList){
        this.genresArrayList = genresArrayList;
    }

    @Override
    public LocalGenresViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grid,parent, false);
        return new LocalGenresViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LocalGenresViewHolder holder, int position) {
        if (genresArrayList.get(position).getGenreName()!=null)holder.genreName.setText(genresArrayList.get(position).getGenreName());
        else holder.genreName.setText("Unknown");
        //if (genresArrayList.get(position).getGenreCover()!=null)
    }

    @Override
    public int getItemCount() {
        return genresArrayList.size();
    }

    class LocalGenresViewHolder extends RecyclerView.ViewHolder{
        //private ImageView genreCover;
        private TextView genreName;
        public LocalGenresViewHolder(View itemView) {
            super(itemView);
            genreName = (TextView) itemView.findViewById(R.id.tv_song_name);
        }
    }
}
