package com.karaokeapp;

import java.util.Locale;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.SearchManager;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

@SuppressLint("NewApi")
public class ActivityMain extends FragmentActivity implements OnEditorActionListener{

	private GridAdapter mAdapter;
	private MenuItem mSearchItem;
	
	private  FragmentManager fm = getSupportFragmentManager();
	private String [] mNavigationDrawerItemTitle;
	
	private DrawerLayout mDlNavigation;
	private ListView mLvDrawer;
	private ActionBarDrawerToggle mDrawerToggle;
	
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//adding Navigation drawer to activity
		mNavigationDrawerItemTitle = getResources().getStringArray(R.array.items_navigation_drawer);
		mLvDrawer = (ListView) findViewById(R.id.lv_drawer);		
		mLvDrawer.setAdapter(new ArrayAdapter<String>(this, R.layout.item_navigation_drawer_list, mNavigationDrawerItemTitle));
		mDlNavigation = (DrawerLayout) findViewById(R.id.dl_navigation);
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
				case 0:
					fragment = new FragmentFavoritesSongs();
					getActionBar().setTitle("Favorites");
					break;
				case 1: 
					fragment = new FragmentAllSongsByArtist();
					getActionBar().setTitle("All songs by Artist");
					break;
				case 2:
					fragment = new FragmentAllSongsByName();
					getActionBar().setTitle("All songs by Name");
					break;
				case 3:
					fragment = new FragmentQueueOfSongs();
					getActionBar().setTitle("Queue of songs");
					break;
				case 4:
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
