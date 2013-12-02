package com.example.ucscbusbuddy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    ArrayList<BusStop> scBusStops = new ArrayList<BusStop>();

    /*
     * Called by clicking "Bus Schedule" button from main page.
     */
    public void busSchedule(View view) {
        Intent intent = new Intent(MainActivity.this, BusScheduleActivity.class );
        startActivity( intent );
    }

    /*
     * Called by clicking "Closest Stop" button from main page.
     */
    public void closestStop(View view) {
        Toast.makeText( getApplicationContext(),
                "Calculating closest stop...",
                Toast.LENGTH_SHORT).show();
        
        Intent intent = new Intent(MainActivity.this, StopInfoActivity.class );

        BusStop closestBusStop = getClosestStop ();

        if (closestBusStop != null) {
            // Pass the bus stop object to the stop info activity.
            intent.putExtra("selectedStop", closestBusStop);
            startActivity( intent );
        }
    }

    /*
     * Called by clicking "Select a Stop" button from main page.
     */
    public void selectStop(View view) {
        Intent intent = new Intent(MainActivity.this, SelectStopActivity.class );
        startActivity( intent );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        AssetManager assetManager = getAssets();
        
        scBusStops = BusStop.createBusStopList(assetManager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    /*
     *  Set application criteria for selecting a location provider
     *  for acquiring the user's location.
     */
    private Criteria setCriteria () {
        Criteria criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setAltitudeRequired(false);
        criteria.setBearingRequired(false);
        criteria.setCostAllowed(false);
        criteria.setSpeedRequired(false);
        criteria.setPowerRequirement(Criteria.POWER_LOW);
        
        return criteria;
    }

    /*
     * Returns the closest BusStop of the user, which is obtained from
     * their location coordinates.
     */
    private BusStop getClosestStop () {
        /*
         * Initialize location manager and get the user's location.
         */
        LocationListener mlocListener;
        LocationManager mlocManager;
        
        mlocManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        mlocListener = new MyLocationListener();

        Criteria criteria = setCriteria ();

        mlocManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
                35000, 10, mlocListener);
        String provider = mlocManager.getBestProvider(criteria, true);
        Location myLoc = mlocManager.getLastKnownLocation(provider);

        /*
         * Iterate through all BusStops, computing the shortest distance
         * between the stop and the user.
         */
        double myLat = myLoc.getLatitude();
        double myLong = myLoc.getLongitude();
        float[] calculatedDistance = new float[1];
        float shortestDistance = Float.MAX_VALUE;
        BusStop closestBusStop = null;
  
        for (int index = 0; index < scBusStops.size(); index++) {
            BusStop indexedStop = scBusStops.get(index);
            double stopLat = indexedStop.getLat();
            double stopLong = indexedStop.getLong();
            Location.distanceBetween (myLat, myLong, stopLat, stopLong,
                    calculatedDistance);
            if (calculatedDistance[0] < shortestDistance) {
                shortestDistance = calculatedDistance[0];
                closestBusStop = indexedStop;
            }
        }
        
        return closestBusStop;
    }

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

    /*
     * If the manual is not on the device, download PDF to
     * device.
     */
    private void copyAssets(String filename, String sdCardPath) {
        AssetManager assetManager = getAssets();

        InputStream in = null;
        OutputStream out = null;
        try {
            in = assetManager.open(filename);
            Log.i("assets", "opened file from /assets");
            out = new FileOutputStream(sdCardPath);
            Log.i("assets", "opened output stream to file");

            copyFile(in, out);
            in.close();
            in = null;
            out.flush();
            out.close();
            out = null;
        } catch (Exception e) {
            Log.e("copyAssets()", e.getMessage());
        }
    }

    public void getManual(View view) {
        String filename = "user_manual.pdf";
        String sdCardPath = Environment.getExternalStorageDirectory().getPath() + "/Download/" + filename;
        Log.i("sd card path", sdCardPath);
        File manual = new File(sdCardPath);
        if (!manual.exists())
        {
            Log.i("manual", "user manual does not exist on device. will copy");
            Toast.makeText(this, "Starting download...", Toast.LENGTH_SHORT).show();
            copyAssets(filename, sdCardPath);
        }

        File file = new File(sdCardPath);        

        // Open PDF viewer on device. If no PDF viewer exists, notify
        // user.
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),"application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        try 
        {
            getApplicationContext().startActivity(intent);
        } 
        catch (ActivityNotFoundException e) 
        {
            Toast.makeText(this, "No PDF viewer on device", Toast.LENGTH_SHORT).show();
        }
    }

    public class MyLocationListener implements LocationListener{

        @Override
        public void onLocationChanged(Location loc){
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
                    "GPS Enabled",
                    Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras){}
    }
}
