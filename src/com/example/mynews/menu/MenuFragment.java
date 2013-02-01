package com.example.mynews.menu;

import org.holoeverywhere.app.GridFragment;
import org.holoeverywhere.widget.GridView;
import com.example.mynews.R;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

public class MenuFragment extends GridFragment {
  
   
 

	private final class menuAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			
			return 40;
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
   


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setGridAdapter(new menuAdapter());
    }

    @Override
    public void onGridItemClick(GridView l, View v, int position, long id) {
        
    }

    @Override
    public void onResume() {
        super.onResume();
      
    }

   
}
