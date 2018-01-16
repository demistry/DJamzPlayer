package com.djamzplayer.android.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.djamzplayer.android.adapters.OnlineAdapter;
import com.djamzplayer.android.models.Song;
import com.djamzplayer.android.views.EmptyRecyclerView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistOnlineFragment extends Fragment {

    private EmptyRecyclerView emptyRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private OnlineAdapter onlineAdpater;
    private View emptyView;


    public ArtistOnlineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.djamzplayer.android.R.layout.fragment_artist_online, container, false);

        emptyView = view.findViewById(com.djamzplayer.android.R.id.empty_root);
        emptyRecyclerView = (EmptyRecyclerView) view.findViewById(com.djamzplayer.android.R.id.rv_artist_online);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        onlineAdpater = new OnlineAdapter(Song.genDummySongs(100), getContext());

        emptyRecyclerView.setLayoutManager(gridLayoutManager);
        emptyRecyclerView.setAdapter(onlineAdpater);
        emptyRecyclerView.setEmptyView(emptyView);

        return view;
    }

}
