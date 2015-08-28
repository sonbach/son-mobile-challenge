package com.son.poi;

public class POIController {

	private POIView poiView;
	private POIModel poiModel;

	public POIController(POIView poiView, POIModel poiModel) {
		this.poiView = poiView;
		this.poiModel = poiModel;
	}

	public void initialize() {
		poiView.initializeAdapter(poiModel.getPOIs());
	}
}
