package com.android.djamzplayer.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.djamzplayer.R;

import static com.android.djamzplayer.adapters.ViewPagerAdapterSong.FRAG_NO;


/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineFragment extends Fragment {


    public OnlineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_online, container, false);
     /*  // TextView textView = (TextView) view.findViewById(R.id.online_frag_text);
        Bundle bundle = getArguments();
        switch (bundle.getInt(FRAG_NO)){
            case 0 : textView.setText("SONG ONLINE FRAGMENT"); break;
            case 1 : textView.setText("ARTIST ONLINE FRAGMENT"); break;
            case 2 : textView.setText("GENRE ONLINE FRAGMENT"); break;
            case 3 : textView.setText("PLAYLIST ONLINE FRAGMENT"); break;
        }*/
        return view;
    }

}
