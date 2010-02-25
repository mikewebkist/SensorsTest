package com.webkist.android.SensorsTest;

import java.util.List;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SensorsTest extends ListActivity {
	private List<Sensor> sensors;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensors = sm.getSensorList(Sensor.TYPE_ALL);
		ArrayAdapter<Sensor> adapter = new MyAdapter(this, sensors);
		setListAdapter(adapter);
	}

    public void onListItemClick(ListView l, View v, int position, long id) {
    	Sensor s = sensors.get(position);
    	
        setResult(RESULT_OK, new Intent().setData(uri));
        finish();
}

	private class MyAdapter extends ArrayAdapter<Sensor> {

		public MyAdapter(Context context, List<Sensor> sensors) {
			super(context, 0, sensors);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			Sensor sensor = this.getItem(position);
			if (convertView == null) {
				convertView = getLayoutInflater().inflate(android.R.layout.two_line_list_item, parent, false);
			}

			((TextView) convertView.findViewById(android.R.id.text1)).setText(sensor.getVendor());
			TextView tv = (TextView) convertView.findViewById(android.R.id.text2);
			tv.setGravity(Gravity.RIGHT);
			tv.setText(String.format("max=%.2f, res=%.2f, type=%d", sensor.getMaximumRange(), sensor.getResolution(), sensor
					.getType()));

			return convertView;

		}
	}
}