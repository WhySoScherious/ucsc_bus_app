package com.example.ucscbusbuddy;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends Activity {
    ArrayList<BusStop> scBusStops = new ArrayList<BusStop>();

    public void busSchedule(View view) {
        Intent intent = new Intent(MainActivity.this, BusScheduleActivity.class );
        startActivity( intent );
    }

    public void closestStop(View view) {
        Intent intent = new Intent(MainActivity.this, ClosestStopActivity.class );

        intent.putParcelableArrayListExtra("busStops", scBusStops);
        startActivity( intent );
    }

    public void selectStop(View view) {
        Intent intent = new Intent(MainActivity.this, SelectStopActivity.class );
        
        intent.putParcelableArrayListExtra("busStops", scBusStops);
        startActivity( intent );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("BusStopList", "Start creating bus stop list");
        scBusStops = createBusStopList(scBusStops);
        Log.d("test", scBusStops.get(0).toString());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
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
        
        int commaIndex = coordinates.indexOf(',');
        double latitude = Double.parseDouble(coordinates.substring(0, commaIndex));
        double longitude = Double.parseDouble(coordinates.substring(commaIndex + 1));
        stop.setLat (latitude);
        stop.setLong(longitude);

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
        busTime.set(Calendar.HOUR, hour);
        busTime.set(Calendar.MINUTE, minute);
        
        return busTime;
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


}
