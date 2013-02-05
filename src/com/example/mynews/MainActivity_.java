package com.example.mynews;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.widget.ListView;



import android.os.Bundle;

import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MainActivity_ extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		  Holo config = Holo.defaultConfig();
	        config.requireSlidingMenu = true;
	        init(config);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		
//		 final SlidingMenuA sm = requireSlidingMenu();
//	       
//	        sm.setContent(R.layout.content);
//	        sm.setBehindContentView(makeMenuView(savedInstanceState));
//	        
//	       sm.getSlidingMenu().setMode(SlidingMenu.LEFT);
//	        sm.getSlidingMenu().setBehindWidth(computeMenuWidth());
	}

//	 public SlidingMenuA requireSlidingMenu() {
//	        return requireAddon(org.holoeverywhere.addon.SlidingMenu.class).activity(this);
//	    }
	 private final class ListAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 9;
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 9;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(null==convertView){
				convertView=getLayoutInflater().inflate(R.layout.menu_list_item);
			}
			return convertView;
		}
		 
	 }
	 private View makeMenuView(Bundle savedInstanceState) {
	        View view = getLayoutInflater().inflate(R.layout.menu);
	        
	        ListView list = (ListView) view.findViewById(R.id.list);
	        list.setAdapter(new ListAdapter());
//	        list.setOnItemClickListener(navigationAdapter);
	        return view;
	    }
	  public void replaceFragment(Fragment fragment) {
	        replaceFragment(fragment, null);
	    }

	    public void replaceFragment(Fragment fragment,
	            String backStackName) {
	        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	        ft.replace(R.id.content, fragment);
	        if (backStackName != null) {
	            ft.addToBackStack(backStackName);
	        }
	        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
	        ft.commit();
	    }

	    private int computeMenuWidth() {
	        return (int) getResources().getFraction(R.dimen.demo_menu_width,
	                getResources().getDisplayMetrics().widthPixels, 1);
	    }
}
