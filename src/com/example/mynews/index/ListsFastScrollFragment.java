package com.example.mynews.index;

import org.holoeverywhere.ArrayAdapter;
import org.holoeverywhere.app.ListFragment;
import org.holoeverywhere.widget.ListView;

import android.view.View;
import android.widget.ListAdapter;

import com.example.mynews.R;


public class ListsFastScrollFragment extends ListFragment {
	private ListView mList;

	protected CharSequence getTitle() {
		return null;
	}

	  @Override
	    public void onResume() {
	        super.onResume();
	        CharSequence title = getTitle();
	        if (title != null) {
	            getSupportActionBar().setSubtitle(title);
	        }
	    }

	protected ListAdapter onObtainData() {
		return ArrayAdapter.createFromResource(getActivity(),
				R.array.countries, R.layout.simple_list_item_1);
	}


	protected void onPrepareList(ListView list) {
		list.setFastScrollAlwaysVisible(true);
		list.setVerticalScrollbarPosition(View.SCROLLBAR_POSITION_RIGHT);
	}

	@Override
	public void onViewCreated(View view) {
		super.onViewCreated(view);
		mList = getListView();
		mList.setFastScrollEnabled(true);
		onPrepareList(mList);
		setListAdapter(onObtainData());
		mList.setOnItemClickListener(null);
		mList.setClickable(false);
	}
}
