package com.karaokeapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import android.widget.ListView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import java.util.ArrayList;

@SuppressLint("NewApi")
public class ActivityMain extends FragmentActivity implements OnEditorActionListener{

	private GridAdapter mAdapter;
	private MenuItem mSearchItem;
	
	private  FragmentManager fm = getSupportFragmentManager();
	private String [] mNavigationDrawerTitles;
    private TypedArray mNavigationDrawerIcons;
    private ArrayList<NavDrawerItem> mNavigationDraverItems;
    private NavDrawerAdapter mNavigationAdapter;
	
	private DrawerLayout mDlNavigation;
	private ListView mLvDrawer;
	private ActionBarDrawerToggle mDrawerToggle;

    private final int FAVORITES_POSITION = 0;
    private final int SONGS_BY_ARTIST_POSITION = 1;
    private final int SONGS_BY_NAME_POSITION = 2;
    private final int QUEUE_OF_SONGS_POSITION = 3;
    private final int SETTINGS_POSITION = 4;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//adding Navigation drawer to activity
        mNavigationDrawerTitles = getResources().getStringArray(R.array.navigation_drawer_titles);
        mNavigationDrawerIcons = getResources().obtainTypedArray(R.array.navigation_drawer_icon);
        mDlNavigation = (DrawerLayout)findViewById(R.id.dl_navigation);
        mLvDrawer = (ListView)findViewById(R.id.lv_drawer);
        mNavigationDraverItems = new ArrayList<NavDrawerItem>();
        mNavigationDraverItems.add(new NavDrawerItem(mNavigationDrawerTitles[FAVORITES_POSITION], mNavigationDrawerIcons.getResourceId(FAVORITES_POSITION, -1)));
        mNavigationDraverItems.add(new NavDrawerItem(mNavigationDrawerTitles[SONGS_BY_ARTIST_POSITION], mNavigationDrawerIcons.getResourceId(SONGS_BY_ARTIST_POSITION, -1)));
        mNavigationDraverItems.add(new NavDrawerItem(mNavigationDrawerTitles[SONGS_BY_NAME_POSITION], mNavigationDrawerIcons.getResourceId(SONGS_BY_NAME_POSITION, -1)));
        mNavigationDraverItems.add(new NavDrawerItem(mNavigationDrawerTitles[QUEUE_OF_SONGS_POSITION], mNavigationDrawerIcons.getResourceId(QUEUE_OF_SONGS_POSITION, -1)));
        mNavigationDraverItems.add(new NavDrawerItem(mNavigationDrawerTitles[SETTINGS_POSITION], mNavigationDrawerIcons.getResourceId(SETTINGS_POSITION, -1)));

        //recycle the typed array
        mNavigationDrawerIcons.recycle();

        //setting Navigation Drawer adapter
        mNavigationAdapter = new NavDrawerAdapter(this, mNavigationDraverItems);
        mLvDrawer.setAdapter(mNavigationAdapter);


        //opening and closing Navigation Drawer with app icon
		mDrawerToggle = new ActionBarDrawerToggle(this, mDlNavigation, R.drawable.ic_navigation_drawer, R.string.drawer_open, R.string.drawer_close);
		mDlNavigation.setDrawerListener(mDrawerToggle);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		//onItemClickListener on navigation drawer item
		mLvDrawer.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Fragment fragment = null;
				switch (position) {
				    case FAVORITES_POSITION:
					    fragment = new FragmentFavoritesSongs();
					    getActionBar().setTitle("Favorites");
					    break;
				    case SONGS_BY_ARTIST_POSITION:
					    fragment = new FragmentAllSongsByArtist();
					    getActionBar().setTitle("All songs by Artist");
					    break;
				    case SONGS_BY_NAME_POSITION:
					    fragment = new FragmentAllSongsByName();
					    getActionBar().setTitle("All songs by Name");
					    break;
				    case QUEUE_OF_SONGS_POSITION:
					    fragment = new FragmentQueueOfSongs();
					    getActionBar().setTitle("Queue of songs");
					    break;
				    case SETTINGS_POSITION:
					    break;
				    default:
					    break;
				}
				if (fragment!=null){
					fm.beginTransaction().replace(R.id.fl_content, fragment).commit();
					mDlNavigation.closeDrawer(mLvDrawer);					
				} else {
					Log.e("ActivityMain", "Error in creating fragment");
					mDlNavigation.closeDrawer(mLvDrawer);
				}
				
			}
		});
		
		//starting home fragment
		Fragment fragmentAllSongsByArtist = new FragmentAllSongsByArtist();
		getActionBar().setTitle("All songs by Artist");
		fm.beginTransaction().replace(R.id.fl_content, fragmentAllSongsByArtist).commit();
	}
	
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
		mDrawerToggle.onConfigurationChanged(newConfig);
	}
	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);
		mDrawerToggle.syncState();
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (mDrawerToggle.onOptionsItemSelected(item)){
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	@SuppressLint("NewApi")
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
//		MenuInflater inflater = getMenuInflater();
//		inflater.inflate(R.menu.activity_main_actions, menu);
//
//		mSearchItem = menu.findItem(R.id.act_search);
//		SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
//		SearchView searchView = (SearchView) MenuItemCompat.getActionView(mSearchItem);
//		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
//		
//		SearchView.OnQueryTextListener textChangeListener = new SearchView.OnQueryTextListener() {
//			
//			@Override
//			public boolean onQueryTextSubmit(String query) {
//				return false;
//			}
//			
//			@Override
//			public boolean onQueryTextChange(String newText) {
//				String searchText = newText.toLowerCase(Locale.getDefault());
//				mAdapter.filter(searchText);
//				return true;
//			}
//		};
//		searchView.setOnQueryTextListener(textChangeListener);
//		
		return super.onCreateOptionsMenu(menu);
	}
	@Override
	public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	

}
