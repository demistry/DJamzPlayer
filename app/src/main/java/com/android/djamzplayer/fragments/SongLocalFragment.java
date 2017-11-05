package com.android.djamzplayer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.djamzplayer.R;
import com.android.djamzplayer.adapters.LocalAdapter;
import com.android.djamzplayer.models.Song;
import com.android.djamzplayer.views.EmptyRecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongLocalFragment extends Fragment {
    private ArrayList<Song> songArrayList;
    private EmptyRecyclerView emptyRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private LocalAdapter localAdapter;
    private View emptyView;

    public SongLocalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_song_local, container, false);

        emptyView = view.findViewById(R.id.empty_root);
        emptyRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.rv_song_local);
        linearLayoutManager = new LinearLayoutManager(getContext());
        localAdapter = new LocalAdapter(Song.genDummySongs(100), getContext());

        emptyRecyclerView.setLayoutManager(linearLayoutManager);
        emptyRecyclerView.setAdapter(localAdapter);
        emptyRecyclerView.setEmptyView(emptyView);

        return view;
    }

}
