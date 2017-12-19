package com.android.djamzplayer.fragments;



import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.android.djamzplayer.R;
import com.android.djamzplayer.activities.NowPlayingActivity;
import com.android.djamzplayer.singletons.SongsArrayHolder;
import com.android.djamzplayer.adapters.LocalSongsRecyclerViewAdapter;
import com.android.djamzplayer.models.Songs;
import com.android.djamzplayer.utils.LocalSongsQueryProvider;

import java.util.ArrayList;



/**
 * A simple {@link Fragment} subclass.
 */
public class SongLocalFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>, LocalSongsRecyclerViewAdapter.SongClickedInterface{
    public static final int LOCAL_SONG_LOADER_ID = 0;
    private RecyclerView songsRecyclerView;
    private ArrayList<Songs> songsArrayList;
    private LocalSongsRecyclerViewAdapter adapter;
    private RelativeLayout emptyRootView;
    private Cursor cursorLoaded;
    private clickedSongFragmentInterface songFragmentInterface;

    public SongLocalFragment() {
        // Required empty public constructor
    }

    public interface clickedSongFragmentInterface{
        void songFromFragment(Songs song, int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.v("Log", "On Attach");
       // this.songFragmentInterface =(clickedSongFragmentInterface) context;
        getLoaderManager().initLoader(LOCAL_SONG_LOADER_ID, null, this).forceLoad();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_song_local, container, false);
        songsRecyclerView = (RecyclerView) view.findViewById(R.id.rv_song_local);
        emptyRootView = (RelativeLayout) view.findViewById(R.id.empty_root);
        emptyRootView.setVisibility(View.GONE);


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
        if (cursorLoaded != null)
            songsArrayList = LocalSongsQueryProvider.getLocalSongsArrayList(cursorLoaded, this.getContext());
        else
            songsArrayList = LocalSongsQueryProvider.getLocalSongsArrayList(data, this.getContext());
        SongsArrayHolder.getInstance().setArrayList(songsArrayList);
        songsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
        adapter = new LocalSongsRecyclerViewAdapter(songsArrayList, this);
        songsRecyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        this.cursorLoaded = data;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SongsArrayHolder.getInstance().getArrayList()!=null){
            getLoaderManager().initLoader(LOCAL_SONG_LOADER_ID, null, this).stopLoading();
            songsArrayList =  SongsArrayHolder.getInstance().getArrayList();
            songsRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
            adapter = new LocalSongsRecyclerViewAdapter(songsArrayList, this);
            songsRecyclerView.setAdapter(adapter);
            //adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        loader.reset();
    }

    @Override
    public void onSongClicked(Songs song, int position) {
        Intent intent = new Intent(getActivity(), NowPlayingActivity.class);
        intent.putExtra("Position", position);
        //intent.putParcelableArrayListExtra(ContractClass.PARCELABLE_ARRAY_LIST, songsArrayList);
        startActivity(intent);
    }
}
