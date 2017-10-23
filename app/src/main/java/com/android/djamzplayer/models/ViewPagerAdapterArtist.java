package com.android.djamzplayer.models;

import android.database.DataSetObserver;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.djamzplayer.fragments.ArtistLocalFragment;
import com.android.djamzplayer.fragments.ArtistOnlineFragment;

/**
 * Created by ILENWABOR DAVID on 12/10/2017.
 */

public class ViewPagerAdapterArtist extends FragmentStatePagerAdapter {
    private  Fragment onLineFragment;
    private Fragment localFragment;
    private int tabCOunt;
    public static String FRAG_NO = "FragNo";
    public ViewPagerAdapterArtist(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCOunt = tabCount;

    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new ArtistLocalFragment();
            case 1:
                return new ArtistOnlineFragment();
        }
        return null;
    }

    @Override
    public int getCount() {
        return tabCOunt;
    }

}
