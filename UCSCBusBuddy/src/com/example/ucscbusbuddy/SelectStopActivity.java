package com.example.ucscbusbuddy;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

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
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;

public class SelectStopActivity extends Activity {
    ArrayList<BusStop> scBusStops;
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
                // Format number to cut off digits > 6.
                NumberFormat df = new DecimalFormat ("#.000000");
                double lat = Double.parseDouble(df.format(marker.getPosition().latitude));
                double longitude = Double.parseDouble(df.format(marker.getPosition().longitude));
                Intent i = new Intent(SelectStopActivity.this,
                        StopInfoActivity.class);
                Log.d ("Finding Stop", "Lat: " + lat + "  Long: " +
                        longitude);
                BusStop clickedBusStop = findBusStop (lat, longitude);
                //Log.d ("Intent", "Passing" + clickedBusStop.toString() +
                //        "to activity");
                i.putExtra("selectedStop", clickedBusStop);
                i.putParcelableArrayListExtra("busStops", scBusStops);
                startActivity(i);
                return true;
            }
        });
    }

    private BusStop findBusStop (double lat, double longitude) {
        for (int index = 0; index < scBusStops.size(); index++) {
            BusStop stop = scBusStops.get(index);
            Log.d ("Finding Stop", "Lat: " + stop.getLat() + "  Long: " +
                    stop.getLong());
            if (stop.getLat() == lat && stop.getLong() == longitude) {
                return stop;
            }
        }

        return null;
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
