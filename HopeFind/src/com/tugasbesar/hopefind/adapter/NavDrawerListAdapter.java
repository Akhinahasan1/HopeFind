package com.tugasbesar.hopefind.adapter;

import java.util.ArrayList;
import com.tugasbesar.hopefind.R;
import com.tugasbesar.hopefind.model.NavDrawerItem;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class NavDrawerListAdapter extends BaseAdapter {
	private Context context;
	private ArrayList<NavDrawerItem> navDrawItem;

	public NavDrawerListAdapter(Context contex, ArrayList<NavDrawerItem> navDrawItem) {
		// TODO Auto-generated constructor stub
		this.context = contex;
		this.navDrawItem = navDrawItem;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return navDrawItem.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return navDrawItem.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		if(convertView==null){
			LayoutInflater mInfliter = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
			convertView= mInfliter.inflate(R.layout.drawer_list_item, null);
			
		}
		
		ImageView imgIcon = (ImageView) convertView.findViewById(R.id.icon);
		TextView txtTitle = (TextView) convertView.findViewById(R.id.title);
		TextView txtCount = (TextView) convertView.findViewById(R.id.counter);
		
		imgIcon.setImageResource(navDrawItem.get(position).getIcon());
		txtTitle.setText(navDrawItem.get(position).getTitle());
		
		if (navDrawItem.get(position).getCounterVisible()){
			
			txtCount.setText(navDrawItem.get(position).getCount());
			
		}else {
			txtCount.setVisibility(View.GONE);
		}
		
		
		return convertView;
	}
	

}
