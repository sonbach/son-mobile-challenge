package com.son.poi;

import java.util.List;

import android.widget.ListView;

public class POIView {

	private MainActivity mainActivity;
	private ListView listview;

	public POIView(MainActivity mainActivity) {
		this.mainActivity = mainActivity;
	}

	public void initialize() {
        listview = (ListView) mainActivity.findViewById(R.id.listview);
	}

	public void initializeAdapter(List<POI> pois) {
        listview.setAdapter(new POIAdapter(mainActivity, pois));
	}

}
