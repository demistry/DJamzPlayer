package com.android.djamzplayer.fragments;



import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.djamzplayer.R;
import com.android.djamzplayer.adapters.LocalSongsRecyclerViewAdapter;
import com.android.djamzplayer.models.Songs;
import com.android.djamzplayer.utils.LocalSongsQueryProvider;
import com.vpaliy.soundcloud.SoundCloud;
import com.vpaliy.soundcloud.SoundCloudService;

import java.util.ArrayList;

import static com.android.djamzplayer.utils.LocalSongsQueryProvider.BASE_PROVIDER_URI;


/**
 * A simple {@link Fragment} subclass.
 */
public class SongLocalFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static final int LOCAL_SONG_LOADER_ID = 0;
    private RecyclerView songsRecyclerView;
    private ArrayList<Songs> songsArrayList;
    private LocalSongsRecyclerViewAdapter adapter;
    private Cursor cursorLoaded;
    public SongLocalFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //SoundCloudService soundCloudService = SoundCloud.create("").createService(this.getContext());
       // soundCloudService..fetchTrack("").
        Log.v("Log", "On Create");
        //getLoaderManager().initLoader(LOCAL_SONG_LOADER_ID, null, this).forceLoad();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v("Log", "On Attach");
        getLoaderManager().initLoader(LOCAL_SONG_LOADER_ID, null, this).forceLoad();
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.v("Log", "On Pause");
        //getLoaderManager().initLoader(LOCAL_SONG_LOADER_ID, null, this).startLoading();
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.v("Log", "On Stop");
        //getLoaderManager().initLoader(LOCAL_SONG_LOADER_ID, null, this).startLoading();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //getLoaderManager().initLoader(LOCAL_SONG_LOADER_ID, null, this).startLoading();
        Log.v("Log", "On Detach");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_local, container, false);
        songsRecyclerView = (RecyclerView) view.findViewById(R.id.song_local_recyclerview);

        //songsArrayList = new ArrayList<>();
        //songsArrayList = LocalSongsQueryProvider.getLocalSongsArrayList(this.getContext());
        return view;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new LocalSongsQueryProvider(this.getContext());
//        return new CursorLoader(this.getContext(), BASE_PROVIDER_URI,
//                null,
//                null,
//                null,
//                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (cursorLoaded!=null)songsArrayList = LocalSongsQueryProvider.getLocalSongsArrayList(cursorLoaded, this.getContext());
        else songsArrayList = LocalSongsQueryProvider.getLocalSongsArrayList(data, this.getContext());
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new LocalSongsRecyclerViewAdapter(songsArrayList);
        songsRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        this.cursorLoaded = data;
    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        loader.reset();
    }

}
