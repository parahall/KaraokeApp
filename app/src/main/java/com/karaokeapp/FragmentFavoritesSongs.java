package com.karaokeapp;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.SearchView;

import java.util.Locale;

@SuppressLint("NewApi")
public class FragmentFavoritesSongs extends Fragment {
	
	private GridAdapter mAdapter;
	private FragmentManager fragmentManager = getFragmentManager();
	private MenuItem mSearchItem;
	
	private static final String TAG = FragmentFavoritesSongs.class.getSimpleName();
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		setHasOptionsMenu(true);
		View view = inflater.inflate(R.layout.fragment_favorites_songs, container, false);
		GridView mGvSong = (GridView) view.findViewById(R.id.gv_favorites_songs);
//		mAdapter = new GridAdapter(view.getContext(), DataManager.loadSongList(view.getContext(),
//                TAG), TAG);
//		mGvSong.setAdapter(mAdapter);
//		mGvSong.setOnItemClickListener(new GridView.OnItemClickListener() {
//
//			@Override
//			public void onItemClick(AdapterView<?> parent, View view,
//					int position, long id) {
//				Log.i("GridView Item was pressed", "GridViewItem");
//				Song choosedSong = (Song) mAdapter.getItem(position);
//				DialogFragmentSongDetails songFragment = DialogFragmentSongDetails
//						.getInstance(choosedSong);
//				songFragment.show(getFragmentManager(), "Dialog fragment Song Details");
//			}
//
//		});

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
}
