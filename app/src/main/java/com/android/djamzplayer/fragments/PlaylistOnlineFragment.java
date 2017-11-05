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
public class PlaylistOnlineFragment extends Fragment {
    private EmptyRecyclerView emptyRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private OnlineAdapter onlineAdapter;
    private View emptyView;

    public PlaylistOnlineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_playlist_online, container, false);

        emptyView = view.findViewById(R.id.empty_root);
        emptyRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.rv_playlist_online);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        onlineAdapter = new OnlineAdapter(Song.genDummySongs(0), getContext());

        emptyRecyclerView.setLayoutManager(gridLayoutManager);
        emptyRecyclerView.setAdapter(onlineAdapter);
        emptyRecyclerView.setEmptyView(emptyView);


        return view;
    }

}
