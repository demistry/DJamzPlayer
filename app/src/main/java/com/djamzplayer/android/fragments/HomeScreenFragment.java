package com.djamzplayer.android.fragments;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.djamzplayer.android.adapters.ViewPagerAdapterGenre;
import com.djamzplayer.android.adapters.ViewPagerAdapterPlaylist;
import com.djamzplayer.android.adapters.ViewPagerAdapterSong;
import com.djamzplayer.android.adapters.ViewPagerAdapterArtist;
import com.djamzplayer.android.utils.BottomNavigationViewHelper;

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

    public static HomeScreenFragment newInstance() {
        
        Bundle args = new Bundle();
        
        HomeScreenFragment fragment = new HomeScreenFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(com.djamzplayer.android.R.layout.fragment_home_screen, container, false);
        tabLayout = (TabLayout) view.findViewById(com.djamzplayer.android.R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Local"));
        tabLayout.addTab(tabLayout.newTab().setText("Online"));

        bottomNavigationView = (BottomNavigationView) view.findViewById(com.djamzplayer.android.R.id.bottom_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);




        viewPager = (ViewPager) view.findViewById(com.djamzplayer.android.R.id.viewpager);
        pagerAdapter = new ViewPagerAdapterSong(getChildFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(
                new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(
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
            case com.djamzplayer.android.R.id.songs:
                pagerAdapter = new ViewPagerAdapterSong(getChildFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(pagerAdapter);
                return true;
            case com.djamzplayer.android.R.id.artist:
                pagerAdapter = new ViewPagerAdapterArtist(getChildFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(pagerAdapter);
                return true;
            case com.djamzplayer.android.R.id.genre:
                pagerAdapter = new ViewPagerAdapterGenre(getChildFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(pagerAdapter);
                return true;
            case com.djamzplayer.android.R.id.playlist:
                pagerAdapter = new ViewPagerAdapterPlaylist(getChildFragmentManager(), tabLayout.getTabCount());
                viewPager.setAdapter(pagerAdapter);
                return true;
        }
        return false;
    }
}
