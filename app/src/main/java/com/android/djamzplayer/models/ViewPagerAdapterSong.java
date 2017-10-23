package com.android.djamzplayer.models;

import android.database.DataSetObserver;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.android.djamzplayer.fragments.ArtistLocalFragment;
import com.android.djamzplayer.fragments.ArtistOnlineFragment;
import com.android.djamzplayer.fragments.GenreLocalFragment;
import com.android.djamzplayer.fragments.GenreOnlineFragment;
import com.android.djamzplayer.fragments.PlaylistLocalFragment;
import com.android.djamzplayer.fragments.PlaylistOnlineFragment;
import com.android.djamzplayer.fragments.SongLocalFragment;
import com.android.djamzplayer.fragments.SongOnlineFragment;
import com.android.djamzplayer.fragments.OnlineFragment;

import static com.android.djamzplayer.models.HomeFragmentsSelectionHelper.PAGE_NO;

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
                return new SongLocalFragment();
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
