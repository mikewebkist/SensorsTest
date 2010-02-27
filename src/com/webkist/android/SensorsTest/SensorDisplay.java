package com.webkist.android.SensorsTest;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorManager;
import android.hardware.SensorEventListener;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class SensorDisplay extends Activity implements SensorEventListener {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		SensorManager sm = (SensorManager) getSystemService(SENSOR_SERVICE);
		Sensor sensor = sm.getDefaultSensor(Sensor.TYPE_ORIENTATION);
		if (sensor == null) {
			Log.v("Temperature", "No sensor!!!");
		} else {
			sm.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {
		// Nothing to do here.
	}

	@Override
	public void onSensorChanged(SensorEvent event) {
//		((TextView) findViewById(R.id.Temp)).setText(String.format("(%.1f, %.1f, %.1f)", event.values[0], event.values[1],
//				event.values[2]));
	}

}
