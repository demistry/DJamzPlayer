package com.android.djamzplayer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.djamzplayer.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NowPlayingFragment extends Fragment {


    public NowPlayingFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.now_playing_layout, container, false);
        return view;
    }

}
