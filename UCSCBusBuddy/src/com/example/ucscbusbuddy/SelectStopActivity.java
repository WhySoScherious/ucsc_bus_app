package com.example.ucscbusbuddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Calendar;

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
import android.content.res.AssetManager;
import android.os.Build;

public class SelectStopActivity extends Activity {
    ArrayList<BusStop> scBusStops = new ArrayList<BusStop>();
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
                i.putExtra("selectedStop", clickedBusStop);
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

                Log.d("BusStopList", "Start creating bus stop list");
                scBusStops = createBusStopList(scBusStops);
                Log.d("test", scBusStops.get(0).toString());
                
                createMarkers();
            }
        }
    }

    /*
     * Returns an arraylist of Bus Stop objects.
     */
    private ArrayList<BusStop> createBusStopList (ArrayList<BusStop> busStops) {
        AssetManager assetManager = getAssets();
        String[] busStopList = null;    // List of bus stop filenames

        try {
            // Open assets/bus_stop_files/.
            // Prints to log if folder not found.
            Log.d("BusStopList", "Access files in bus_stop_files/ folder");
            busStopList = assetManager.list("bus_stop_files");
            Log.d("BusStopList", "Get bus stops");
            return getBusStops (busStops, busStopList);
        } catch (IOException e) {
            Log.e("assets", "bus_stop_files/ not found");
        }

        return busStops;
    }

    /*
     * Iterates through bus stop files, parsing through the files and
     * creating BusStop objects, complete with buses times at the particular
     * stops. 
     * Returns arraylist of BusStop objects with times.
     */
    private ArrayList<BusStop> getBusStops (ArrayList<BusStop> busStops,
            String[] busStopFileNames) {

        for (int index = 0; index < busStopFileNames.length; index++) {
            // Prints to logger if the filename was not found.
            try {
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(getAssets().
                                open("bus_stop_files/" + busStopFileNames[index])));
                BusStop stop = new BusStop (busStopFileNames[index]);
                Log.d("BusStopList", "Call parseFile");
                stop = parseFile (stop, reader);
                Log.d("BusStopList", "Add " + busStopFileNames[index] + " to list");
                if (stop != null)
                    busStops.add(stop);
            } catch (IOException e) {
                Log.e("assets",
                        "bus_stop_files/" + busStopFileNames[index] + " not found");
            }

        }

        return busStops;
    }

    /*
     * Parses through file, generating BusStop objects with the name of
     * the bus stop and its corresponding times.
     * Returns a complete BusStop object for the bus stop specified from
     * the file.
     */
    private BusStop parseFile (BusStop stop,
            BufferedReader reader) throws IOException {
        // First line of file is the GPS coordinates of stop
        String coordinates = reader.readLine();

        try {
            int commaIndex = coordinates.indexOf(',');
            double latitude = Double.parseDouble(coordinates.substring(0, commaIndex));
            double longitude = Double.parseDouble(coordinates.substring(commaIndex + 1));
            stop.setLat (latitude);
            stop.setLong(longitude);
        } catch (NumberFormatException e) {
            Log.e ("parseFile", stop.getName() + ": Invalid GPS coordinate, skipping file");
            return null;
        } catch (StringIndexOutOfBoundsException e) {
            Log.e ("parseFile", stop.getName() + ": Invalid GPS coordinate, skipping file");
            return null;
        }

        for (String line = reader.readLine(); line != null;
                line = reader.readLine()) {
            String busNumber = new String (line);

            // Get the route of the bus.
            /*
             * Note: A 'mf' represents M-F route time while
             *       a 'ss' represents Sat-Sun route time.
             */
            String route = reader.readLine();

            for (line = reader.readLine(); line != null &&
                    line.compareTo ("stop") != 0; line = reader.readLine()) {
                Calendar busTime = setTime (line, stop);

                if (route.compareTo("mf") == 0) {
                    busTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                } else if (route.compareTo("ss") == 0) {
                    busTime.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                } else {
                    Log.e ("parseFile", stop.getName() +
                            ": Invalid day of week route in file");
                }

                if (stop.addBusTime(busNumber, busTime) == 1) {
                    Log.e("bus time", line + ": Invalid bus time");
                }
            }
        }
        return stop;
    }

    private Calendar setTime (String time, BusStop stop) {
        Calendar busTime = Calendar.getInstance();
        int startOfHourIndex = 0;
        int endOfHourIndex = 2;
        int startOfMinuteIndex = 3;

        int hour = Integer.parseInt(time.substring(startOfHourIndex, endOfHourIndex));
        int minute = Integer.parseInt(time.substring(startOfMinuteIndex));
        busTime.set(Calendar.AM_PM, Calendar.AM);
        busTime.set(Calendar.HOUR_OF_DAY, hour);
        busTime.set(Calendar.MINUTE, minute);

        return busTime;
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
