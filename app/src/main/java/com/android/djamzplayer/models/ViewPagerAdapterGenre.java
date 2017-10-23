package com.android.djamzplayer.models;

import android.database.DataSetObserver;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.djamzplayer.fragments.GenreLocalFragment;
import com.android.djamzplayer.fragments.GenreOnlineFragment;

/**
 * Created by ILENWABOR DAVID on 12/10/2017.
 */

public class ViewPagerAdapterGenre extends FragmentStatePagerAdapter {
    private  Fragment onLineFragment;
    private Fragment localFragment;
    private int tabCOunt;
    public static String FRAG_NO = "FragNo";
    public ViewPagerAdapterGenre(FragmentManager fm, int tabCount){
        super(fm);
        this.tabCOunt = tabCount;

    }

    @Override
    public int getCount() {
        return tabCOunt;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new GenreLocalFragment();
            case 1:
                return new GenreOnlineFragment();
        }
        return null;
    }

}
