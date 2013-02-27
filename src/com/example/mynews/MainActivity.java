package com.example.mynews;

import java.util.List;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.app.ListFragment;
import org.holoeverywhere.widget.AdapterView;
import org.holoeverywhere.widget.AdapterView.OnItemClickListener;
import org.holoeverywhere.widget.Toast;



import com.actionbarsherlock.app.ActionBar.OnMenuVisibilityListener;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.widget.SearchView;
import com.example.mynews.index.ListsFastScrollWithSectionsFragment;
import com.example.mynews.pageindicator.TestFragmentAdapter;
import com.example.mynews.service.INgnHttpClientService;
import com.example.mynews.service.INgnSqliteService;
import com.example.mynews.service.impl.HttpService;
import com.example.mynews.service.impl.NgnSqliteService;
import com.example.mynews.utils.ILog;
import com.example.mynews.utils.StringUtils;
import com.viewpagerindicator.TitlePageIndicator;


import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements OnPageChangeListener{

	protected static final String uri = "http://192.168.11.30:8080/simplepie/demo/base.db";
	protected static final int OK = 0;
	protected static final int NOT_OK = 1;
	private TestFragmentAdapter mAdapter;
	private ViewPager mPager;
	private TitlePageIndicator mIndicator;
	private Handler handler;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Holo config = Holo.defaultConfig();
		config.requireSlidingMenu = false;
		init(config);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.simple_titles_bottom);
		Intent intent=getIntent();
		position= intent.getIntExtra("position", 0);
		if(1==position){
			getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		}
		findViewById(android.R.id.home).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				if(null!=mIndicator){
					mIndicator.setCurrentItem(0);
				}
				
				
			}
		});
		handler = new Handler(new Handler.Callback() {

			@Override
			public boolean handleMessage(Message msg)
			{
				if (OK == msg.what) {
					Print();
					
					return true;
				} else if (NOT_OK == msg.what) {
					
					return true;
				}
				return false;
			}
		});
		testRss();
		mAdapter = new TestFragmentAdapter(getSupportFragmentManager());
		
		ListsFastScrollWithSectionsFragment fragment=(ListsFastScrollWithSectionsFragment) mAdapter.getItem(0);
		
		
		mPager = (ViewPager) findViewById(R.id.pager);
		mPager.setAdapter(mAdapter);
		
		

		mIndicator = (TitlePageIndicator) findViewById(R.id.indicator);
		mIndicator.setViewPager(mPager);
		mIndicator.setOnPageChangeListener(this);
		mIndicator.setCurrentItem(position);
		

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

	protected void Print() {
		INgnSqliteService sql=new NgnSqliteService();
		sql.start();
		 List<ContentValues> lists=	sql.queryAll(NgnSqliteService.TB_NAME_CONTACTS);
		 ILog.d("只是否", lists.size()+"");
		 for(int i=0;i<lists.size();i++){
			 
			 ILog.d("只是否", lists.get(i).getAsString(NgnSqliteService.POST_TITLE)+"");
		 }
		
		
	}

	private void testRss() {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				INgnHttpClientService service=new HttpService();
				service.start();
				
				service.getFile(uri);
				handler.sendEmptyMessage(OK);
				
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

	        menu.add("Search")
	            .setIcon(isLight ? R.drawable.ic_search_inverse : R.drawable.abs__ic_search)
	            .setActionView(searchView)
	            .setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM | MenuItem.SHOW_AS_ACTION_COLLAPSE_ACTION_VIEW);
//	        menu.add("Search")
//	        .setIcon(isLight ? R.drawable.ic_search_inverse : R.drawable.abs__ic_search)
//	        .setActionView(searchView)
//	        .setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);

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

	

	@Override
	public void onPageScrollStateChanged(int position) {
		Toast.makeText(getApplicationContext(), "导航"+position, Toast.LENGTH_SHORT).show();
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		
		
	}

	@Override
	public void onPageSelected(int position) {
		
			getSupportActionBar().setTitle(mAdapter.getPageTitle(position));
			if(0==position){
				getSupportActionBar().setDisplayHomeAsUpEnabled(false);
			}else{
				getSupportActionBar().setDisplayHomeAsUpEnabled(true);
			}
	
	}

}
