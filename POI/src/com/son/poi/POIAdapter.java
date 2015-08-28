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

	private Context context;
	private LayoutInflater inflater;
	private List<POI> pois;

	public POIAdapter(Context context, List<POI> pois) {
        // TODO Auto-generated constructor stub
        this.context = context;
        this.pois = pois;
        inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return pois.size();
	}

	@Override
	public Object getItem(int position) {
		return pois.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		 View vi = convertView;
	        if (vi == null)
	            vi = inflater.inflate(R.layout.row, null);
	        TextView text = (TextView) vi.findViewById(R.id.header);
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
