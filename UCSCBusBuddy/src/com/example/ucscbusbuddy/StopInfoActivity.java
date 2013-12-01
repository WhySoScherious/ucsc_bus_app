package com.example.ucscbusbuddy;

import java.util.ArrayList;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.view.Menu;
import android.view.MenuItem;

public class StopInfoActivity extends Activity {
    ArrayList<BusStop> scBusStops;
    static LatLng stop = null;  // Chosen bus stop coordinates
    private GoogleMap map;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_info);
        
        // Show the Up button in the action bar.
        setupActionBar();

        /*
         * Get stop coordinates and bus stop name from callee activity
         * to show.
         */
        Bundle mBundle = getIntent().getBundleExtra("extras");
        if(mBundle != null) {
            setTitle (mBundle.getString("stopTitle"));
            stop = new LatLng(mBundle.getDouble("lat"), mBundle.getDouble("long"));
        }

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
        scBusStops = getIntent().getParcelableArrayListExtra("busStops");
        for (int index = 0; index < scBusStops.size(); index++) {
            BusStop stop = scBusStops.get(index);
            LatLng location = new LatLng(stop.getLat(), stop.getLong());
            map.addMarker(new MarkerOptions()
            .position(location)
            .title(stop.getName()));
        }

        map.setOnMarkerClickListener(new OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                // Bundle will pass over variables to the stop
                // information activity.
                Bundle extras = new Bundle();
                extras.putDouble("long", marker.getPosition().longitude);
                extras.putDouble("lat", marker.getPosition().latitude);
                extras.putString("stopTitle", marker.getTitle());
                
                Intent i = new Intent(StopInfoActivity.this, StopInfoActivity.class);
                i.putExtra("extras", extras);
                i.putParcelableArrayListExtra("busStops", scBusStops);
                startActivity(i);
                finish();
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
            if (map != null && stop != null) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(stop, 15));
                createMarkers();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stop_info, menu);
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
            Intent upIntent = new Intent(this, SelectStopActivity.class);
            upIntent.putParcelableArrayListExtra("busStops", scBusStops);

            NavUtils.navigateUpTo(this, upIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }
}
