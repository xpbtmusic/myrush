package com.example.mynews;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import com.example.mynews.menu.MenuFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

public class MenuActivity extends Activity {
	private MenuFragment grid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Holo config = Holo.defaultConfig();
		config.requireSlidingMenu = false;
		init(config);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		grid = new MenuFragment();
		
		replaceFragment(grid);

	}

	public void replaceFragment(Fragment fragment) {
		replaceFragment(fragment, null);
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
