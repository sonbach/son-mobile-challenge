package com.son.poi;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

public class POIService extends Service implements LocationListener {

	private static final int MIN_DISTANCE = 1000;
	private static final long SIXTY_MINUTES = 60 * 60 * 1000;
	private static final long ONE_MINUTE = 1 * 60 * 1000;
	private LocationManager locationManager;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_BUSINESS,
			MySQLiteHelper.COLUMN_ADDRESS, MySQLiteHelper.COLUMN_VISITS };
	private MySQLiteHelper mySQLiteHelper;
	private SQLiteDatabase database;
	private int _id = -1;

	@Override
	public void onCreate() {
		super.onCreate();
		mySQLiteHelper = new MySQLiteHelper(this);
		database = mySQLiteHelper.getWritableDatabase();
		startGps();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mySQLiteHelper.close();
		stopGps();
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return Service.START_STICKY;

	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public void onLocationChanged(Location location) {
		Geocoder geocoder = new Geocoder(this, Locale.getDefault());
		double latitude = location.getLatitude();
		double longitude = location.getLongitude();
		String address = reverseGeocode(geocoder, latitude, longitude);
		String businessName = getBusinessNameFromLatLong(latitude, longitude);

		writeToDB(address, businessName);
	}

	private void writeToDB(String address, String businessName) {
		ContentValues values = new ContentValues();
		POI poi = getPOIFromServer(address);
		if (poi == null) {
			poi = new POI();
			poi.setAddress(address);
			poi.setBusinessName(businessName);
			poi.setVisits(1);
			values.put(MySQLiteHelper.COLUMN_ADDRESS, poi.getAddress());
			values.put(MySQLiteHelper.COLUMN_BUSINESS, poi.getBusinessName());
			values.put(MySQLiteHelper.COLUMN_VISITS, poi.getVisits());
			database.insert(MySQLiteHelper.TABLE_POIS, null, values);
		} else {
			values.put(MySQLiteHelper.COLUMN_ADDRESS, poi.getAddress());
			values.put(MySQLiteHelper.COLUMN_BUSINESS, poi.getBusinessName());
			values.put(MySQLiteHelper.COLUMN_VISITS, poi.getVisits());
			String whereClause = MySQLiteHelper.COLUMN_ID + " =" + _id;
			database.update(MySQLiteHelper.TABLE_POIS, values, whereClause, null);
			_id = -1;
		}
	}

	private POI getPOIFromServer(String address) {
		Cursor cursor = database.query(MySQLiteHelper.TABLE_POIS, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			POI poi = cursorToPOI(cursor);
			if (poi.getAddress().equals(address)) {
				poi.setVisits(poi.getVisits() + 1);
				_id = cursor.getInt(0);
				return poi;
			}
			cursor.moveToNext();
		}
		cursor.close();

		return null;
	}

	private POI cursorToPOI(Cursor cursor) {
		POI poi = new POI();
		poi.setBusinessName(cursor.getString(1) == null ? "" : cursor.getString(1));
		poi.setAddress(cursor.getString(2));
		poi.setVisits(cursor.getInt(3));
		return poi;
	}

	private String getBusinessNameFromLatLong(double latitude, double longitude) {
		// User Google Places API to get Business info
		return null;
	}

	@Override
	public void onStatusChanged(String provider, int status, Bundle extras) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String provider) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderDisabled(String provider) {
		// TODO Auto-generated method stub

	}

	private void startGps() {
		if (locationManager == null)
			locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		if (locationManager != null) {
			locationManager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, ONE_MINUTE, MIN_DISTANCE, this);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, SIXTY_MINUTES, MIN_DISTANCE, this);
		}
	}

	private void stopGps() {
		if (locationManager != null)
			locationManager.removeUpdates((LocationListener) this);
		locationManager = null;
	}

	public String reverseGeocode(Geocoder geocoder, double latitude, double longitude) {
		Address address = null;
		try {
			List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
			address = addresses.get(0);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return address.getAddressLine(0);
	}

}
