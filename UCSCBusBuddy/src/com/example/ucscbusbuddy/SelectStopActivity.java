package com.example.ucscbusbuddy;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.GoogleMapOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.MapFragment;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class SelectStopActivity extends Activity {
    static final LatLng SANTACRUZ = new LatLng(36.9720, -122.0263);
    private GoogleMap map;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_select_stop);
		
		// Show the Up button in the action bar.
		setupActionBar();
		setTitle ("Select a Stop");

		initMaps();
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

	/*
	 * Creates markers over the map overlay and sets up marker click
	 * listener.
	 */
	private void createMarkers() {
	    map.addMarker(new MarkerOptions()
            .position(SANTACRUZ)
            .title("Bus Stop Title Here"));

	    map.setOnMarkerClickListener(new OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                // Bundle will pass over variables to the stop
                // information activity.
                Bundle extras = new Bundle();
                extras.putDouble("long", marker.getPosition().longitude);
                extras.putDouble("lat", marker.getPosition().latitude);
                extras.putString("stopTitle", marker.getTitle());
                
                Intent i = new Intent(SelectStopActivity.this, StopInfoActivity.class);
                i.putExtra("extras", extras);
                startActivity(i);
                return true;
            }
        });
	}

	/*
     * Initializes the Google Maps overlay.
     */
	private void initMaps () {
	    if (map == null) {
	        map = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
	                .getMap();
	        if (map != null) {
	            // Start camera over Santa Cruz, CA
	            map.moveCamera(CameraUpdateFactory.newLatLngZoom(SANTACRUZ, 13));

	            GoogleMapOptions options = new GoogleMapOptions();
	            options.mapType(GoogleMap.MAP_TYPE_SATELLITE)
    	            .compassEnabled(true)
    	            .rotateGesturesEnabled(true)
    	            .tiltGesturesEnabled(true);

	            map.setMyLocationEnabled(true);
	            
	            createMarkers();
	        }
	    }
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.select_stop, menu);
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

}
