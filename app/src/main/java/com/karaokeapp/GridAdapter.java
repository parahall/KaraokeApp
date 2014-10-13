package com.karaokeapp;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GridAdapter extends BaseAdapter {

	private static final String ALL_SONGS_TAG = "FragmentAllSongsByArtist";
	private static final String FAVORITES_SONGS_TAG = "FragmentFavoritesSongs";

	private LayoutInflater mInflater;
	private ArrayList<Song> mList;
	private Context mContext;
	private ArrayList<Song> mSongList;
	private String TAG;

	public GridAdapter(Context context, ArrayList<Song> songList, String TAG) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mList = songList;
		this.mSongList = new ArrayList<Song>();
		this.mSongList.addAll(mList);
		this.TAG = TAG;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Song getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	private class ViewHolder {
		public TextView artist;
		public TextView songName;
		public ImageView albumCover;
		public TextView duration;
		public ImageView addToFavorites;
		public ImageView removeFromFavorites;
	}

	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {
		ViewHolder holder = null;
		if (convertView == null) {
			LayoutInflater mInflater = ((Activity) mContext)
					.getLayoutInflater();
			convertView = mInflater.inflate(R.layout.item_song_list, parent,
					false);

			holder = new ViewHolder();
			holder.artist = (TextView) convertView
					.findViewById(R.id.tv_artist_item);
			holder.songName = (TextView) convertView
					.findViewById(R.id.tv_song_name_item);
			holder.duration = (TextView) convertView
					.findViewById(R.id.tv_duration_item);
			holder.albumCover = (ImageView) convertView
					.findViewById(R.id.iv_album_cover_item);
			holder.addToFavorites = (ImageView) convertView.findViewById(R.id.btn_add_to_favorite_item);
			holder.removeFromFavorites = (ImageView) convertView.findViewById(R.id.btn_remove_from_favorites);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.artist.setText(mList.get(position).getArtist());
		holder.songName.setText(mList.get(position).getSongName());
		holder.duration.setText(mList.get(position).getDuration());
		holder.albumCover.setImageDrawable(mList.get(position).albumCover);
		
		if (TAG.equals(ALL_SONGS_TAG)){
			holder.removeFromFavorites.setVisibility(View.GONE);
			
		} else if (TAG.equals(FAVORITES_SONGS_TAG)){
			holder.addToFavorites.setVisibility(View.GONE);
		}
		
		holder.addToFavorites.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GridView mGvSong = (GridView) parent.findViewById(R.id.gv_song);
				Song song = (Song) mGvSong.getItemAtPosition(position);
				DataManager dataManager = new DataManager();
				try {
					dataManager.addSongToFavorites(mContext, song);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
		
		holder.removeFromFavorites.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				GridView mGvSong = (GridView) parent.findViewById(R.id.gv_song);
				Song song = (Song) mGvSong.getItemAtPosition(position);
				DataManager dataManager = new DataManager();
				dataManager.removeSongFromFavorites(mContext, song);
			}
		});
		return convertView;
	}

	public void filter(String searchText) {
		searchText = searchText.toLowerCase();
		mList.clear();
		if (searchText.length() == 0) {
			mList.addAll(mSongList);
		} else {
			for (Song songSearch : mSongList) {
				if (songSearch.getArtist().toLowerCase().contains(searchText)
						| songSearch.getSongName().toLowerCase()
								.contains(searchText)) {
					mList.add(songSearch);
				}
			}
		}
		notifyDataSetChanged();
	}
}
