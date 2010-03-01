package com.webkist.android.SensorsTest;

import java.util.List;

import android.app.Dialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.app.AlertDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class SensorsTest extends ListActivity implements SensorEventListener {
	private List<Sensor> sensors;
	private Sensor selectedSensor;
	private SensorManager sm;
	private TextView sensorView;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		sensors = sm.getSensorList(Sensor.TYPE_ALL);
		ArrayAdapter<Sensor> adapter = new MyAdapter(this, sensors);
		setListAdapter(adapter);
	}

	public void onListItemClick(ListView l, View v, int position, long id) {
		selectedSensor = sensors.get(position);
		showDialog(selectedSensor.getType());
	}

	protected Dialog onCreateDialog(int id) {
		LayoutInflater factory = LayoutInflater.from(this);
		final View layout = factory.inflate(R.layout.main, null);
		sensorView = (TextView) layout.findViewById(R.id.sensorValues);
		return new AlertDialog.Builder(SensorsTest.this).setTitle(R.string.app_name).setView(layout).setMessage(
				sensorTypeString(selectedSensor)).setPositiveButton("Done", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int whichButton) {
				sm.unregisterListener(SensorsTest.this);
			}
		}).create();
	}

	public void onPrepareDialog(int id, Dialog dialog) {
		sensorView.setText("waiting...");
		sm.registerListener(this, selectedSensor, SensorManager.SENSOR_DELAY_NORMAL);
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
		String display;

		switch (selectedSensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			display = String.format("(%.1f, %.1f, %.1f) m/s^2", event.values[0], event.values[1], event.values[2]);
			break;
		case Sensor.TYPE_GYROSCOPE:
			display = String.format("(%.1f, %.1f, %.1f)", event.values[0], event.values[1], event.values[2]);
			break;
		case Sensor.TYPE_LIGHT:
			display = String.format("%.1f Si lux", event.values[0]);
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			display = String.format("(%.1f, %.1f, %.1f) uT", event.values[0], event.values[1], event.values[2]);
			break;
		case Sensor.TYPE_ORIENTATION:
			display = String.format("(%.1f¼, %.1f¼, %.1f¼) A,P,R", event.values[0], event.values[1], event.values[2]);
			break;
		case Sensor.TYPE_PRESSURE:
			display = String.format("%.1f", event.values[0]);
			break;
		case Sensor.TYPE_PROXIMITY:
			display = String.format("%.1fcm", event.values[0]);
			break;
		case Sensor.TYPE_TEMPERATURE:
			display = String.format("%.1f¼C", event.values[0]);
			break;
		default:
			display = "Unknown sensor type!";
		}
		sensorView.setText(display);
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// TODO Auto-generated method stub

	}

	private String sensorTypeString(Sensor sensor) {
		String type;
		switch (sensor.getType()) {
		case Sensor.TYPE_ACCELEROMETER:
			type = "Accelerometer";
			break;
		case Sensor.TYPE_GYROSCOPE:
			type = "Gyroscope";
			break;
		case Sensor.TYPE_LIGHT:
			type = "Light";
			break;
		case Sensor.TYPE_MAGNETIC_FIELD:
			type = "Magnetic Field";
			break;
		case Sensor.TYPE_ORIENTATION:
			type = "Orientation";
			break;
		case Sensor.TYPE_PRESSURE:
			type = "Pressure";
			break;
		case Sensor.TYPE_PROXIMITY:
			type = "Proximity";
			break;
		case Sensor.TYPE_TEMPERATURE:
			type = "Temperature";
			break;
		default:
			type = String.format("Unknown Sensor: %d", sensor.getType());
		}
		return type;
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

			((TextView) convertView.findViewById(android.R.id.text1)).setText(sensorTypeString(sensor));
			TextView tv = (TextView) convertView.findViewById(android.R.id.text2);
			tv.setGravity(Gravity.RIGHT);
			tv.setText(String.format("max=%f, res=%f", sensor.getMaximumRange(), sensor.getResolution()));

			return convertView;

		}
	}
}