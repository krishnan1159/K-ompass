package com.krishnan.kuruk;

import java.io.BufferedReader;
import android.app.Activity;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

public class DispActivity extends Activity {

	  private static SensorManager sensorService;
	  private view compassView;
	  private Sensor sensor;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	    compassView = new view(this);
	    setContentView(compassView);

	    sensorService = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
	    sensor = sensorService.getDefaultSensor(Sensor.TYPE_ORIENTATION);
	    if (sensor != null) {
	      sensorService.registerListener(mySensorEventListener, sensor,
	          SensorManager.SENSOR_DELAY_NORMAL);
	      Log.i("Compass MainActivity", "Registerered for ORIENTATION Sensor");

	    } else {
	      Log.e("Compass MainActivity", "Registerered for ORIENTATION Sensor");
	      Toast.makeText(this, "ORIENTATION Sensor not found",
	          Toast.LENGTH_LONG).show();
	      finish();
	    }
		setContentView(R.layout.activity_disp);
		String route=getIntent().getExtras().getString("route");
		((TextView)findViewById(R.id.textView5)).setText(route);
		
	}
	 private SensorEventListener mySensorEventListener = new SensorEventListener() {

		    @Override
		    public void onAccuracyChanged(Sensor sensor, int accuracy) {
		    }

		    @Override
		    public void onSensorChanged(SensorEvent event) {
		      // angle between the magnetic north directio
		      // 0=North, 90=East, 180=South, 270=West
		      float azimuth = event.values[0];
		      compassView.updateData(azimuth);
		    }
		  };

		  @Override
		  protected void onDestroy() {
		    super.onDestroy();
		    if (sensor != null) {
		      sensorService.unregisterListener(mySensorEventListener);
		    }
		  }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.disp, menu);
		return true;
	}
	
	

}
