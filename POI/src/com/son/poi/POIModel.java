package com.son.poi;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

public class POIModel {

	private MySQLiteHelper dbHelper;
	private SQLiteDatabase database;
	private String[] allColumns = { MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_BUSINESS,
			MySQLiteHelper.COLUMN_ADDRESS, MySQLiteHelper.COLUMN_VISITS };

	public POIModel(MySQLiteHelper mySQLiteHelper) {
		dbHelper = mySQLiteHelper;
	}

	public void openDataSource() throws SQLException {
		database = dbHelper.getWritableDatabase();

		clearData();
		mockData();
	}

	private void clearData() {
		database.delete(MySQLiteHelper.TABLE_POIS, null, null);
	}

	private void mockData() {
		createPOI("123 test st", "Apple Inc.", 10);
		createPOI("435 test st", "", 3);
		createPOI("356 test st", "", 21);
		createPOI("62 test st", "Google Inc.", 15);
		createPOI("15423 test st", "Microsoft Corp.", 17);
		createPOI("346 test st", "Qualcomm Inc.", 11);
		createPOI("5973 Avenida Encinas Ste 101, Carlsbad, CA 92008", "Verve Mobile", 145);
		createPOI("4614 test st", "", 19);
		createPOI("2456 test st", "Intuit", 12);
		createPOI("56 test st", "GoPro", 51);
	}

	public void closeDataSource() {
		dbHelper.close();
	}

	public POI createPOI(String address, String business, int visits) {
		ContentValues values = new ContentValues();
		values.put(MySQLiteHelper.COLUMN_ADDRESS, address);
		values.put(MySQLiteHelper.COLUMN_BUSINESS, business);
		values.put(MySQLiteHelper.COLUMN_VISITS, visits);
		long insertId = database.insert(MySQLiteHelper.TABLE_POIS, null, values);
		Cursor cursor = database.query(MySQLiteHelper.TABLE_POIS, allColumns,
				MySQLiteHelper.COLUMN_ID + " = " + insertId, null, null, null, null);
		cursor.moveToFirst();
		POI newPOI = cursorToPOI(cursor);
		cursor.close();
		return newPOI;
	}

	public List<POI> getPOIs() {
		List<POI> pois = new ArrayList<POI>();

		Cursor cursor = database.query(MySQLiteHelper.TABLE_POIS, allColumns, null, null, null, null, null);

		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			POI poi = cursorToPOI(cursor);
			pois.add(poi);
			cursor.moveToNext();
		}
		cursor.close();
		return pois;
	}

	private POI cursorToPOI(Cursor cursor) {
		POI poi = new POI();
		poi.setBusinessName(cursor.getString(1) == null ? "" : cursor.getString(1));
		poi.setAddress(cursor.getString(2));
		poi.setVisits(cursor.getInt(3));
		return poi;
	}

}
