package com.karaokeapp;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.Toast;

public class DataManager {
	
	private static final int ID_POSITION = 0;
	private static final int ARTIST_POSITION = 1;
	private static final int SONG_NAME_POSITION = 2;
	private static final int ALBUM_COVER_POSITION = 3;
	private static final int DURATION_POSITION = 4;
	private static final int LYRIC_POSITION = 5;
	private static final int REMARKS_POSITION = 6;
	private static final int VERSION_POSITION = 7;
	private static final String ALL_SONGS_TAG = "FragmentAllSongsByArtist";
	private static final String FAVORITES_SONGS_TAG = "FragmentFavoritesSongs";
	private static final String FILE_NAME_FAVORITES = "data_songs_favorites.csv";
	private static File file;
	  
	public static ArrayList<Song> getSongsList(Context context, String TAG){
		String [] next = {};
		Drawable albumCover;
		Drawable albumCoverDefault = context.getResources().getDrawable(R.drawable.defcover);
		ArrayList<Song> list= new ArrayList<Song>();
		Song song;
		file = new File(context.getExternalFilesDir("data"), FILE_NAME_FAVORITES);

		
		CSVReader csvReader = null;
		try {
				if (TAG.equals(ALL_SONGS_TAG)){
					csvReader = new CSVReader(new InputStreamReader(context.getApplicationContext().getAssets().open("data_songs_e.csv")));					
				} else if (TAG.equals(FAVORITES_SONGS_TAG)){
					FileInputStream fin = new FileInputStream(file);
					csvReader = new CSVReader(new InputStreamReader(fin));
				}
				next = csvReader.readNext();
					while(next!=null){
						song = new Song();
						song.setId(next[ID_POSITION]);
						song.setArtist(next[ARTIST_POSITION]);
						song.setSongName(next[SONG_NAME_POSITION]);
						if (!next[ALBUM_COVER_POSITION].equals("")){
							int id = context.getResources().getIdentifier(next[ALBUM_COVER_POSITION], "drawable", context.getPackageName());
							albumCover = context.getResources().getDrawable(id);
							song.setAlbumPicture(albumCover);						
						} else {
							song.setAlbumCover(albumCoverDefault);
						}
						if (!next[DURATION_POSITION].equals("")){
							song.setDuration(next[DURATION_POSITION]);						
						} else {
							song.setDuration("--:--");
						}
						if (!next[LYRIC_POSITION].equals("")){
							song.setLyrics(next[LYRIC_POSITION]);						
						} else {
							song.setLyrics("no lyrics");
						}
						if (!next[VERSION_POSITION].equals("")){
							song.setVersion(next[VERSION_POSITION]);
						} else {
							song.setVersion("---");
						}
						if (!next[REMARKS_POSITION].equals("")){
							song.setRemarks(next[REMARKS_POSITION]);
						} else {
							song.setRemarks("---");
						}
						list.add(song);
						next = csvReader.readNext();
					}
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		return list;
	}

	public void addSongToFavorites(Context context, Song song) throws IOException{
		String resourceName = "defcover";	
		CSVWriter writer = new CSVWriter(new FileWriter(file, true));
		
		String [] writeLine = {song.getId(),song.getArtist(),song.getSongName(),resourceName,song.getDuration(),song.getLyrics(), song.getVersion(),song.getRemarks()};
		writer.writeNext(writeLine);
		writer.flush();
		writer.close();
		Toast.makeText(context.getApplicationContext(), "Song was added to Favorites", Toast.LENGTH_SHORT).show();
	}

	public void removeSongFromFavorites (Context context, Song song){
		
	}
	

	


}
