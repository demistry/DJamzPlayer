package com.android.djamzplayer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
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
        View view = inflater.inflate(R.layout.fragment_artist_online, container, false);

        emptyView = view.findViewById(R.id.empty_root);
        emptyRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.rv_artist_online);
        gridLayoutManager = new GridLayoutManager(getContext(), 2);
        onlineAdpater = new OnlineAdapter(Song.genDummySongs(100), getContext());

        emptyRecyclerView.setLayoutManager(gridLayoutManager);
        emptyRecyclerView.setAdapter(onlineAdpater);
        emptyRecyclerView.setEmptyView(emptyView);

        return view;
    }

}
