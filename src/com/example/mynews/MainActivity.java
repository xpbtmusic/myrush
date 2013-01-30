package com.example.mynews;

import org.holoeverywhere.addon.SlidingMenu.SlidingMenuA;
import org.holoeverywhere.app.Activity;

import org.holoeverywhere.widget.ListView;

import com.slidingmenu.lib.SlidingMenu;

import android.os.Bundle;

import android.view.View;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		  Holo config = Holo.defaultConfig();
	        config.requireSlidingMenu = true;
	        init(config);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		
		 final SlidingMenuA sm = requireSlidingMenu();
	       
	        sm.setContent(R.layout.content);
	        sm.setBehindContentView(makeMenuView(savedInstanceState));
	       sm.getSlidingMenu().setMode(SlidingMenu.RIGHT);
	        sm.getSlidingMenu().setBehindWidth(computeMenuWidth());
	}

	 public SlidingMenuA requireSlidingMenu() {
	        return requireAddon(org.holoeverywhere.addon.SlidingMenu.class).activity(this);
	    }
	 private View makeMenuView(Bundle savedInstanceState) {
	        View view = getLayoutInflater().inflate(R.layout.menu);
	        
	        ListView list = (ListView) view.findViewById(R.id.list);
//	        list.setAdapter();
//	        list.setOnItemClickListener(navigationAdapter);
	        return view;
	    }

	    private int computeMenuWidth() {
	        return (int) getResources().getFraction(R.dimen.demo_menu_width,
	                getResources().getDisplayMetrics().widthPixels, 1);
	    }
}
