package com.example.mynews;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import org.mcsoxford.rss.RSSFeed;
import org.mcsoxford.rss.RSSReader;
import org.mcsoxford.rss.RSSReaderException;


import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.example.mynews.pageindicator.TestFragmentAdapter;
import com.example.mynews.utils.StringUtils;
import com.viewpagerindicator.TitlePageIndicator;

import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;

public class MainActivity extends Activity {

	private TestFragmentAdapter mAdapter;
	private ViewPager mPager;
	private TitlePageIndicator mIndicator;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Holo config = Holo.defaultConfig();
		config.requireSlidingMenu = false;
		init(config);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_titles_bottom);
		testRss();
		mAdapter = new TestFragmentAdapter(getSupportFragmentManager());
		

		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);

		mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);

		// findViewById(R.id.bt).setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// Intent intent=new Intent(MainActivity.this,MenuActivity.class);
		// startActivity(intent);
		//
		// }
		// });

	}

	private void testRss() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				RSSReader reader = new RSSReader();
//				  String uri = "http://news.163.com/special/00011K6L/rss_newstop.xml";
				  String uri = "http://news.baidu.com/n?cmd=4&class=civilnews&tn=rss";
				  try {
					RSSFeed feed = reader.load(uri);
					Log.d("得到", feed.getDescription());
					Log.d("得到1", feed.getTitle());
					Log.d("得到2", feed.getTitle());
					for(int i=0;i<feed.getItems().size();i++){
						Log.d("得到3", feed.getItems().get(i).getContent()+"===");
						Log.d("得到3", feed.getItems().get(i).getTitle()+"===");
						Log.d("得到3", feed.getItems().get(i).getDescription()+"===");
					}
					
				} catch (RSSReaderException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			}
		}).start();
		
	}

	public void replaceFragment(Fragment fragment) {
		replaceFragment(fragment, null);
	}
	
	 @Override
	    public boolean onCreateOptionsMenu(Menu menu) {
	        //Used to put dark icons on light action bar
	        boolean isLight = true;

	        //Create the search view
	        SearchView searchView = new SearchView(getSupportActionBar().getThemedContext());
	        searchView.setQueryHint(StringUtils.getString(this, R.string.search_hint_text));

//	        menu.add("Search")
//	            .setIcon(isLight ? R.drawable.ic_search_inverse : R.drawable.abs__ic_search)
//	            .setActionView(searchView)
//	            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
	        menu.add("Search")
	        .setIcon(isLight ? R.drawable.ic_search_inverse : R.drawable.abs__ic_search)
	        .setActionView(searchView)
	        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

	        return true;
	    }
	public void replaceFragment(Fragment fragment, String backStackName) {
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ft.replace(R.id.content, fragment);
		if (backStackName != null) {
			ft.addToBackStack(backStackName);
		}
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}

}
