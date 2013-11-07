package com.example.ucscbusbuddy;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;

public class ClosestStopActivity extends Activity {

	private LocationListener mlocListener;
	private LocationManager mlocManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_closest_stop);
		// Show the Up button in the action bar.
		setupActionBar();
		setTitle ("Closest Stop");
		
		mlocManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
		mlocListener = new MyLocationListener();
		
		mlocManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER,
				0,
				0,
				mlocListener);
	
	}
	
	@Override
	public void onPause() {
	    mlocManager.removeUpdates(mlocListener);
	    super.onPause();
	}

	@Override
	public void onResume() {
		mlocManager.requestLocationUpdates(
				LocationManager.GPS_PROVIDER,
				0,
				0,
				mlocListener);
	    super.onResume();
	}
	/**
	 * Set up the {@link android.app.ActionBar}, if the API is available.
	 */
	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	private void setupActionBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			getActionBar().setDisplayHomeAsUpEnabled(true);
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.closest_stop, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			// This ID represents the Home or Up button. In the case of this
			// activity, the Up button is shown. Use NavUtils to allow users
			// to navigate up one level in the application structure. For
			// more details, see the Navigation pattern on Android Design:
			//
			// http://developer.android.com/design/patterns/navigation.html#up-vs-back
			//
			NavUtils.navigateUpFromSameTask(this);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public class MyLocationListener implements LocationListener{
		
		@Override
		public void onLocationChanged(Location loc){
		
			String test = "latitude: "+loc.getLatitude()+"\n"+
						  "longitude: "+loc.getLongitude();
		
			Toast.makeText( getApplicationContext(),
							test,
							Toast.LENGTH_SHORT).show();
		
		}
		
		@Override
		public void onProviderDisabled(String provider){
			Toast.makeText( getApplicationContext(),
					        "GPS Disabled",
					        Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onProviderEnabled(String provider){
			Toast.makeText( getApplicationContext(),
			                "GPS Disabled",
			                Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onStatusChanged(String provider, int status, Bundle extras){}
	}
}