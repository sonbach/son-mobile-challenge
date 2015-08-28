package com.son.poi;

import java.util.ArrayList;
import java.util.List;

public class POIModel {

	public POIModel() {
	}

	public List<POI> getPOIs() {
		return getMockData();
	}

	private List<POI> getMockData() {
		List<POI> pois = new ArrayList<POI>();
		pois.add(createPOI("Business One", "123 abc st", 2));
		pois.add(createPOI("Business Two", "345 wret st", 5));
		pois.add(createPOI("Business Three", "678 dfgh st", 1));
		return pois;
	}

	private POI createPOI(String businessName, String address, int visits) {
		POI poi = new POI();
		poi.setAddress(address);
		poi.setBusinessName(businessName);
		poi.setVisits(visits);
		return poi;
	}

}
