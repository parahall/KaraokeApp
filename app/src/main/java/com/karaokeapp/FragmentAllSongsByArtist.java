package com.karaokeapp;

import com.karaokeapp.data.KaraokeContract.*;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.SearchView;

import java.util.ArrayList;
import java.util.Locale;

public class FragmentAllSongsByArtist extends Fragment implements DataManager
        .dataIteractionListener, LoaderManager.LoaderCallbacks<Cursor> {

    private GridAdapter mAdapter;
    private MenuItem mSearchItem;

    private static final String TAG = FragmentAllSongsByArtist.class.getSimpleName();
    private ProgressDialog progressDialog;
    private GridView mGvSong;

    private static final int KARAOKE_SONGS_LOADER = 0;

    private static final String[] KARAOKE_SONGS_COLUMNS = {
            KaraokeSongsEntry.TABLE_NAME + "." + KaraokeSongsEntry._ID,
            KaraokeSongsEntry.COLUMN_SONG_ID,
            KaraokeSongsEntry.COLUMN_SONG_NAME,
            KaraokeSongsEntry.COLUMN_ALBUM_COVER_URL,
            KaraokeSongsEntry.COLUMN_DURATION,
            KaraokeSongsEntry.COLUMN_LYRICS_URL,
            KaraokeSongsEntry.COLUMN_VERSION,
            KaraokeSongsEntry.COLUMN_REMARKS
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(KARAOKE_SONGS_LOADER,null,this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View view = inflater.inflate(R.layout.fragment_all_songs_by_artist, container, false);
        mGvSong = (GridView) view.findViewById(R.id.gv_song);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.show();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.activity_main_actions, menu);

        mSearchItem = menu.findItem(R.id.act_search);
        SearchView searchView = (SearchView) menu.findItem(R.id.act_search).getActionView();

        SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                String searchText = newText.toLowerCase(Locale.getDefault());
                mAdapter.filter(searchText);
                return true;
            }
        };
        searchView.setOnQueryTextListener(textChangeListener);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public void dataLoaded(ArrayList<Song> songs) {
        progressDialog.hide();
        if(isAdded()) {
            mAdapter = new GridAdapter(getActivity(), songs, TAG);
            mGvSong.setAdapter(mAdapter);
            mGvSong.setOnItemClickListener(new GridView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Song choosedSong = (Song) mAdapter.getItem(position);
                    DialogFragmentSongDetails songFragment = DialogFragmentSongDetails
                            .getInstance(choosedSong);
                    songFragment.show(getFragmentManager(), "Dialog fragment Song Details");
                }

            });
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        // Sort order:  Ascending, by date.

        // Now create and return a CursorLoader that will take care of
        // creating a Cursor for the data being displayed.
        return new CursorLoader(
                getActivity(),
                KaraokeSongsEntry.CONTENT_URI,
                KARAOKE_SONGS_COLUMNS,
                null,
                null,
                null
        );
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data != null && data.moveToFirst() && isAdded()) {
            DataManager.loadSongs(this, data);
        } else {
            DataManager.loadSongList(getActivity(),this);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) { }
}
