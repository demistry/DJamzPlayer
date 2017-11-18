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
public class MoodSelectMainFragment extends Fragment {


    public static MoodSelectMainFragment newInstance() {
        
        Bundle args = new Bundle();
        
        MoodSelectMainFragment fragment = new MoodSelectMainFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public MoodSelectMainFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_mood_select_main, container, false);
    }

}
