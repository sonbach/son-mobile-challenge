package com.son.poi;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class POIAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private List<POI> pois;

	public POIAdapter(Context context, List<POI> pois) {
		this.pois = pois;
		inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return pois.size();
	}

	@Override
	public Object getItem(int position) {
		return pois.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (vi == null)
			vi = inflater.inflate(R.layout.row, null);
		TextView text = (TextView) vi.findViewById(R.id.business_name);
		text.setText(pois.get(position).getBusinessName());
		TextView description = (TextView) vi.findViewById(R.id.address);
		description.setText(pois.get(position).getAddress());
		TextView counter = (TextView) vi.findViewById(R.id.counter);
		counter.setText(pois.get(position).getVisits() + " visits");
		ImageView image = (ImageView) vi.findViewById(R.id.imageView1);
		image.setImageResource(R.drawable.ic_launcher);
		return vi;
	}

}
