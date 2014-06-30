package com.tugasbesar.hopefind.model;

public class NavDrawerItem {
	private String title;
	private String count="0";
	private int icon;
	
	private boolean isCounterVisible = false;
	

	public NavDrawerItem() {
		// TODO Auto-generated constructor stub
	}
	public NavDrawerItem(String title,int icon){
		this.title = title;
		this.icon = icon;
		
	}
	public NavDrawerItem(String title,int icon,boolean isCounterVisible,String count) {
		
		this.title=title;
		this.icon = icon;
		this.isCounterVisible = isCounterVisible;
		this.setCount(count);
	}



	public String getTitle() {
		return this.title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public boolean getCounterVisible() {
		return this.isCounterVisible;
	}


	public void setCounterVisible(boolean isCounterVisible) {
		this.isCounterVisible = isCounterVisible;
	}


	public int getIcon() {
		return this.icon;
	}


	public void setIcon(int icon) {
		this.icon = icon;
	}
	public String getCount() {
		return count;
	}
	private void setCount(String count) {
		this.count = count;
	}

}
