package com.djamzplayer.android.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.djamzplayer.android.fragments.SongLocalFragment;
import com.djamzplayer.android.fragments.SongOnlineFragment;

/**
 * Created by ILENWABOR DAVID on 12/10/2017.
 */

public class ViewPagerAdapterSong extends FragmentStatePagerAdapter {
    private  Fragment onLineFragment;
    private Fragment localFragment;
    private int tabCOunt;
    public static String FRAG_NO = "FragNo";
    public ViewPagerAdapterSong(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCOunt = tabCount;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                //final SongLocalFragment songs = new SongLocalFragment();
                return new SongLocalFragment();
                //return songs;
            case 1:
                return new SongOnlineFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCOunt;
    }


}
