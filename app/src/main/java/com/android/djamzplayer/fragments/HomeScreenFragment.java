package com.android.djamzplayer.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.android.djamzplayer.R;
import com.android.djamzplayer.adapters.ViewPagerAdapterGenre;
import com.android.djamzplayer.adapters.ViewPagerAdapterPlaylist;
import com.android.djamzplayer.adapters.ViewPagerAdapterSong;
import com.android.djamzplayer.adapters.ViewPagerAdapterArtist;
import com.android.djamzplayer.utils.BottomNavigationViewHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeScreenFragment extends Fragment implements  BottomNavigationView.OnNavigationItemSelectedListener{
    FragmentStatePagerAdapter pagerAdapter;
    TabLayout tabLayout;
    BottomNavigationView bottomNavigationView;
    private ViewPager viewPager;
    public HomeScreenFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home_screen, container, false);
        tabLayout = (TabLayout) view.findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Local"));
        tabLayout.addTab(tabLayout.newTab().setText("Online"));

        bottomNavigationView = (BottomNavigationView) view.findViewById(R.id.bottom_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);




        viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        pagerAdapter = new ViewPagerAdapterSong(getFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(
                new TabLayout.OnTabSelectedListener() {
                    @Override
                    public void onTabSelected(TabLayout.Tab tab) {
                        viewPager.setCurrentItem(tab.getPosition());
                    }

                    @Override
                    public void onTabUnselected(TabLayout.Tab tab) {

                    }

                    @Override
                    public void onTabReselected(TabLayout.Tab tab) {

                    }
                }
        );
        return view;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        //ViewPagerAdapterSong pager = new ViewPagerAdapterSong(getFragmentManager(), tabLayout.getTabCount());
        switch(item.getItemId()){
            case R.id.songs:
                pagerAdapter = new ViewPagerAdapterSong(getFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(pagerAdapter);
                return true;
            case R.id.artist:
                pagerAdapter = new ViewPagerAdapterArtist(getFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(pagerAdapter);
                return true;
            case R.id.genre:
                pagerAdapter = new ViewPagerAdapterGenre(getFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(pagerAdapter);
                return true;
            case R.id.playlist:
                pagerAdapter = new ViewPagerAdapterPlaylist(getFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(pagerAdapter);
                return true;
        }
        return false;
    }
}
