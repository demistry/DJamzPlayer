package com.djamzplayer.android.fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.djamzplayer.android.singletons.SongsArrayHolder;
import com.djamzplayer.android.adapters.LocalArtistsRecyclerViewAdapter;
import com.djamzplayer.android.models.Artists;
import com.djamzplayer.android.utils.ContractClass;
import com.djamzplayer.android.utils.LocalArtistQueryProvider;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistLocalFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private RecyclerView emptyRecyclerView;
    private GridLayoutManager gridLayoutManager;
    private LocalArtistsRecyclerViewAdapter localArtistsAdapter;
    private View emptyView;
    private ArrayList<Artists> artistsArrayList;

    public ArtistLocalFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getLoaderManager().initLoader(12, null, this).startLoading();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(com.djamzplayer.android.R.layout.fragment_artist_local, container, false);
        emptyView = view.findViewById(com.djamzplayer.android.R.id.empty_root);
        emptyRecyclerView = (RecyclerView) view.findViewById(com.djamzplayer.android.R.id.rv_artist_local);

        //localAdapter = new LocalAdapter(Song.genDummySongs(100), getContext());
        //emptyRootView.setVisibility(View.GONE);

        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] artistProjections = {MediaStore.Audio.Albums.ARTIST,MediaStore.Audio.Albums.NUMBER_OF_SONGS, MediaStore.Audio.Albums.ALBUM_ART};
        return new CursorLoader(getContext(), ContractClass.ARTIST_PROVIDER_URI, artistProjections, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        artistsArrayList = LocalArtistQueryProvider.queryCursor(data);
        SongsArrayHolder.getInstance().setArtistsArrayList(artistsArrayList);
        //Log.v("Log", "Cursor finished loading with array size of " + String.valueOf(artistsArrayList.size()));
        gridLayoutManager = new GridLayoutManager(this.getContext(),2);
        emptyRecyclerView.setLayoutManager(gridLayoutManager);
        localArtistsAdapter = new LocalArtistsRecyclerViewAdapter(artistsArrayList);
        emptyRecyclerView.setAdapter(localArtistsAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SongsArrayHolder.getInstance().getArtistsArrayList()!=null){
            getLoaderManager().initLoader(12, null, this).stopLoading();
            artistsArrayList = SongsArrayHolder.getInstance().getArtistsArrayList();
            gridLayoutManager = new GridLayoutManager(this.getContext(),2);
            emptyRecyclerView.setLayoutManager(gridLayoutManager);
            localArtistsAdapter = new LocalArtistsRecyclerViewAdapter(artistsArrayList);
            emptyRecyclerView.setAdapter(localArtistsAdapter);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
