package com.android.djamzplayer.fragments;


import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.djamzplayer.R;
import com.android.djamzplayer.adapters.LocalAdapter;
import com.android.djamzplayer.adapters.LocalGenresRecyclerViewAdapter;
import com.android.djamzplayer.models.Genres;
import com.android.djamzplayer.models.Song;
import com.android.djamzplayer.utils.ContractClass;
import com.android.djamzplayer.utils.LocalGenreQueryProvider;
import com.android.djamzplayer.views.EmptyRecyclerView;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class GenreLocalFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private ArrayList<Genres> genreArrayList;
    private EmptyRecyclerView emptyRecyclerView;
    private LinearLayoutManager linearLayoutManager;
    private LocalGenresRecyclerViewAdapter localAdapter;
    private View emptyView;

    public GenreLocalFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_genre_local, container, false);

        emptyView = view.findViewById(R.id.empty_root);
        emptyRecyclerView = (EmptyRecyclerView) view.findViewById(R.id.rv_genre_local);
        //linearLayoutManager = new LinearLayoutManager(getContext());
        //localAdapter = new LocalAdapter(Song.genDummySongs(100), getContext());

        emptyRecyclerView.setLayoutManager(new GridLayoutManager(this.getContext(), 2));
        emptyRecyclerView.setEmptyView(emptyView);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getLoaderManager().initLoader(0, null, this).forceLoad();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String [] projections = {MediaStore.Audio.Genres.NAME};
       // String query = " _id in (select genre_id from audio_genres_map where audio_id in (select _id from audio_meta where is_music != 0))";
        return new CursorLoader(this.getContext(), ContractClass.GENRE_PROVIDER_BASE_URI,projections,null,
                null,null
                );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        genreArrayList = LocalGenreQueryProvider.queryGenreProvider(data);
        localAdapter = new LocalGenresRecyclerViewAdapter(genreArrayList);
        emptyRecyclerView.setAdapter(localAdapter);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }
}
