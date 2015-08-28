package com.son.poi;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	private POIView poiView;
	private POIModel poiModel;
	private POIController poiController;

	public MainActivity() {
		poiView = new POIView(this);
		poiModel = new POIModel(new MySQLiteHelper(this));
		poiController = new POIController(poiView, poiModel);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
	}

	@Override
	protected void onResume() {
		super.onResume();
		poiModel.openDataSource();
		poiView.initialize();
		poiController.initialize();

	}

	@Override
	protected void onPause() {
		super.onPause();
		poiModel.closeDataSource();
	}
}
