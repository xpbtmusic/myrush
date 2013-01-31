package com.example.mynews;

import org.holoeverywhere.app.Activity;
import org.holoeverywhere.app.Fragment;
import org.holoeverywhere.app.GridFragment;

import com.example.mynews.menu.MenuFragment;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MenuActivity extends Activity {
	private MenuFragment grid;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Holo config = Holo.defaultConfig();
		config.requireSlidingMenu = false;
		init(config);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu);
		grid=new MenuFragment();
		grid.setGridAdapter(new menuAdapter());
		replaceFragment(grid);
		
		
		
        

	}
	private final class menuAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			
			return 10;
		}

		@Override
		public Object getItem(int position) {
			
			return null;
		}

		@Override
		public long getItemId(int position) {
			
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if(null==convertView){
				convertView=getLayoutInflater().inflate(R.layout.menu_list_item);
			}
			return convertView;
		}
		
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
}
