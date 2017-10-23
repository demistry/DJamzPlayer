package com.android.djamzplayer.models;

import android.content.SharedPreferences;

/**
 * Created by ILENWABOR DAVID on 13/10/2017.
 */

public class HomeFragmentsSelectionHelper {
    private Boolean songSelected;
    private Boolean artistSelected;
    private Boolean genreSelected;
    private Boolean playlistSelected;
    private static SharedPreferences fragmentSelectorPreference;
    private int pageNo;
    public static final String PAGE_NO = "pageNo";
    public HomeFragmentsSelectionHelper(int pageNo){
        this.pageNo = pageNo;
    }

    public  int getPageNo() {
        return pageNo;
    }

    public  void setPageNo(int pageNos) {
        this.pageNo = pageNos;
    }

    /*public HomeFragmentsSelectionHelper(SharedPreferences fragmentSelectorPreferences){
        fragmentSelectorPreference = fragmentSelectorPreferences;
    }

    public void setSongPager(Boolean bool, int pageNumber){
        this.songSelected = bool;
        fragmentSelectorPreference.edit().putInt(PAGE_NO, pageNumber).apply();

    }
    public void setArtistPager(Boolean bool, int pageNumber){
        this.artistSelected = bool;
        fragmentSelectorPreference.edit().putInt(PAGE_NO, pageNumber).apply();
    }
    public void setGenrePager(Boolean bool, int pageNumber){
        this.genreSelected = bool;
        fragmentSelectorPreference.edit().putInt(PAGE_NO, pageNumber).apply();
    }
    public void setPlaylistPager(Boolean bool, int pageNumber){
        this.playlistSelectesd = bool;
        fragmentSelectorPreference.edit().putInt(PAGE_NO, pageNumber).apply();
    }
    public Boolean getSongPagerState(){
        return songSelected;
    }
    public Boolean getArtistPagerState(){
        return artistSelected;
    }
    public Boolean getGenrePagerState(){
        return genreSelected;
    }
    public Boolean getPlaylistPagerState(){
        return playlistSelected;
    }

    public static SharedPreferences getFragmentSelectorPreference(){
        return fragmentSelectorPreference;
    }*/


}