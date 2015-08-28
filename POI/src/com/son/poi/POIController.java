package com.son.poi;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class POIController {

	private POIView poiView;
	private POIModel poiModel;

	public POIController(POIView poiView, POIModel poiModel) {
		this.poiView = poiView;
		this.poiModel = poiModel;
	}

	public void initialize() {
		poiView.initializeAdapter(sortPOIs(poiModel.getPOIs()));
	}

	public List<POI> sortPOIs(List<POI> pois) {
		List<POI> sortedPOIs = pois;

		if (pois.size() > 0) {
			Collections.sort(sortedPOIs, new Comparator<POI>() {

				@Override
				public int compare(POI poiOne, POI poiTwo) {
					return poiOne.getVisits() > poiTwo.getVisits() ? -1
							: poiOne.getVisits() == poiTwo.getVisits() ? 0 : 1;
				}
			});
		}
		return sortedPOIs;
	}
}
