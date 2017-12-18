package com.android.djamzplayer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.djamzplayer.R;
import com.android.djamzplayer.models.Songs;

import java.util.ArrayList;

/**
 * Created by ILENWABOR DAVID on 02/11/2017.
 */

public class LocalSongsRecyclerViewAdapter extends RecyclerView.Adapter<LocalSongsRecyclerViewAdapter.LocalSongsViewHolder> {
    private ArrayList<Songs> songsArrayList;
    private SongClickedInterface songClickedInterface;

    public interface SongClickedInterface{
        void onSongClicked(Songs song, int position);
    }
    public LocalSongsRecyclerViewAdapter(ArrayList<Songs> songsArrayList, SongClickedInterface songClickedInterface){
        this.songsArrayList = songsArrayList;
        this.songClickedInterface = songClickedInterface;
    }

    @Override
    public LocalSongsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.local_item_song, parent, false);
        return new LocalSongsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final LocalSongsViewHolder holder, int position) {
        final Songs currentSong = songsArrayList.get(position);
        holder.songTitle.setText(currentSong.getSongTitle());
        holder.songTitle.setSelected(true);
        holder.songDuration.setText(currentSong.getSongDuration());
        holder.artistName.setText(currentSong.getArtistName());
        if(currentSong.getAlbumCover()!=null)holder.albumCover.setImageBitmap(currentSong.getAlbumCover());

        holder.relativeLayout.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        songClickedInterface.onSongClicked(currentSong, holder.getAdapterPosition());
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return songsArrayList.size();
    }

    class LocalSongsViewHolder extends RecyclerView.ViewHolder{
        private TextView songTitle, artistName, songDuration;
        private ImageView albumCover;
        private RelativeLayout relativeLayout;
        private LocalSongsViewHolder(View itemView) {
            super(itemView);
            songTitle = (TextView) itemView.findViewById(R.id.mini_song_title);
            artistName = (TextView) itemView.findViewById(R.id.mini_artist_name);
            songDuration = (TextView) itemView.findViewById(R.id.mini_time);
            albumCover = (ImageView) itemView.findViewById(R.id.mini_album_cover);
            relativeLayout = (RelativeLayout) itemView.findViewById(R.id.local_songs_rootview);
        }
    }
}
