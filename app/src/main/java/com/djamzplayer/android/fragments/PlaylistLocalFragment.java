package com.djamzplayer.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.djamzplayer.android.adapters.LocalAdapter;
import com.djamzplayer.android.models.Song;
import com.djamzplayer.android.views.EmptyRecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlaylistLocalFragment extends Fragment {
    private ArrayList<Song> songArrayList;
    private EmptyRecyclerView emptyRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private LocalAdapter localAdapter;
    private View emptyView;
    public PlaylistLocalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.djamzplayer.android.R.layout.fragment_playlist_local, container, false);

        emptyView = view.findViewById(com.djamzplayer.android.R.id.empty_root);
        emptyRecyclerView = (EmptyRecyclerView) view.findViewById(com.djamzplayer.android.R.id.rv_playlist_local);
        linearLayoutManager = new LinearLayoutManager(getContext());
        localAdapter = new LocalAdapter(Song.genDummySongs(0), getContext());

        emptyRecyclerView.setLayoutManager(linearLayoutManager);
        emptyRecyclerView.setAdapter(localAdapter);
        emptyRecyclerView.setEmptyView(emptyView);

        return view;
    }

}
