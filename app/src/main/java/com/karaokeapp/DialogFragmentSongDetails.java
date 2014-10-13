package com.karaokeapp;

import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class DialogFragmentSongDetails extends DialogFragment {

	private static final String SONG_KEY = "THIS_IS_THE_KEY_FOR_SONG";

	protected static final String TAG = DialogFragmentSongDetails.class.getSimpleName();

	Song choosedSong;

	private ImageView ivAlbumCover;
	private TextView tvArtist;
	private TextView tvSongName;
	private TextView tvDuration;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.dialog_fragment_song_details,
				container, false);
		ivAlbumCover = (ImageView) view.findViewById(R.id.iv_album_cover);
//		ivAlbumCover.setImageResource(R.drawable.king);
		tvArtist = (TextView) view.findViewById(R.id.tv_artist);
		tvSongName = (TextView) view.findViewById(R.id.tv_song_name);
		tvDuration = (TextView) view.findViewById(R.id.tv_duration);
//			Song choosedSong = new Song();
		choosedSong = (Song) getArguments().getSerializable(SONG_KEY);
		ivAlbumCover.setImageDrawable(choosedSong.getAlbumCover());
		tvArtist.setText(choosedSong.getArtist());
		tvSongName.setText(choosedSong.getSongName());
		tvDuration.setText(choosedSong.getDuration());
			
		getDialog().getWindow().requestFeature(STYLE_NO_TITLE);
		return view;
	}

	public static DialogFragmentSongDetails getInstance(Song choosedSong) {
		Bundle bundle = new Bundle();
		bundle.putSerializable(SONG_KEY, choosedSong);
		DialogFragmentSongDetails fragment = new DialogFragmentSongDetails();
		fragment.setArguments(bundle);
		return fragment;
	}
	

}
