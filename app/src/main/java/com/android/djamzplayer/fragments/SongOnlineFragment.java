package com.android.djamzplayer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.djamzplayer.R;
import com.android.djamzplayer.adapters.LocalAdapter;
import com.android.djamzplayer.adapters.OnlineAdapter;
import com.android.djamzplayer.models.Song;
import com.android.djamzplayer.views.EmptyRecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongOnlineFragment extends Fragment {

    private EmptyRecyclerView emptyRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private OnlineAdapter onlineAdapter;
    private View emptyView;

    public SongOnlineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_online, container, false);

        emptyView = view.findViewById(R.id.empty_root);
        emptyRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.rv_song_online);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        onlineAdapter = new OnlineAdapter(Song.genDummySongs(100), getContext());

        emptyRecyclerView.setLayoutManager(gridLayoutManager);
        emptyRecyclerView.setAdapter(onlineAdapter);
        emptyRecyclerView.setEmptyView(emptyView);


        return view;
    }

}
