package com.karaokeapp;


import android.content.Context;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataManager {

    public static final String SONG_ID = "SONG_ID";
    public static final String ARTIST_NAME = "ARTIST_NAME";
    public static final String SONG_NAME = "SONG_NAME";
    public static final String ALBUM_COVER_URL = "ALBUM_COVER_URL";
    public static final String DURATION = "DURATION";
    public static final String LYRICS_URL = "LYRICS_URL";
    public static final String VERSION = "VERSION";
    public static final String REMARKS = "REMARKS";
    private static ArrayList<Song> songArrayList;

    public interface dataIteractionListener {
        public void dataLoaded(ArrayList<Song> songs);
    }

    public static void loadSongList(final Context context, String TAG,
                                    final dataIteractionListener mListener) {
        songArrayList = new ArrayList<Song>();
        ParseQuery<ParseObject> query = ParseQuery.getQuery("Song");
        query.findInBackground(new FindCallback<ParseObject>() {

            public void done(List<ParseObject> songList, ParseException e) {
                if (e == null) {
                    Log.d("Parse SDK Song", "Retrieved " + songList.size() + " songs");
                    for (ParseObject parseObject : songList) {

                        Song song = new Song(
                                parseObject.getString(SONG_ID),
                                parseObject.getString(ARTIST_NAME),
                                parseObject.getString(SONG_NAME),
//                                parseObject.getString(ALBUM_COVER_URL),
                                context.getResources().getDrawable(R.drawable.defcover),
                                parseObject.getString(DURATION),
                                parseObject.getString(LYRICS_URL),
                                parseObject.getString(VERSION),
                                parseObject.getString(REMARKS)
                        );
                        songArrayList.add(song);
                    }
                    mListener.dataLoaded(songArrayList);

                } else {
                    Log.d("Parse SDK Song", "Error: " + e.getMessage());
                }
            }
        });
    }

    //used only to import songs from csv files.
    //do not use it regulary
    private static void importSongsToParse(final ArrayList<Song> list) {

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                int i = 0;
                for (Song song1 : list) {
                    i++;
                    ParseObject songObject = new ParseObject("Song");
                    songObject.put("SONG_ID", song1.getId());
                    songObject.put("ARTIST_NAME", song1.getArtist());
                    songObject.put("SONG_NAME", song1.getSongName());
                    songObject.put("ALBUM_COVER_URL", "http://");
                    songObject.put("DURATION", song1.getDuration());
                    songObject.put("LYRICS_URL", "http://");
                    songObject.put("VERSION", song1.getVersion());
                    songObject.put("REMARKS", song1.getRemarks());
                    songObject.saveInBackground();
                    if (i == 29) {
                        try {
                            Thread.sleep(1000);
                            i = 0;
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
        thread.start();


    }


    public void addSongToFavorites(Context context, Song song) throws IOException {
    }

    public void removeSongFromFavorites(Context context, Song song) {
    }


}
