package com.example.ucscbusbuddy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.AssetManager;
import android.os.Build;

public class BusScheduleActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_bus_schedule);
		// Show the Up button in the action bar.
		setupActionBar();
		setTitle ("Bus Schedule");
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
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
		getMenuInflater().inflate(R.menu.bus_schedule, menu);
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

    private void copyFile(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[1024];
        int read;
        while ((read = in.read(buffer)) != -1) {
            out.write(buffer, 0, read);
        }
    }

	/*
	 * If the route schedule is not on the device, download PDF to
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

	/*
	 * Obtains the bus schedule specified by filename and opens it
	 * in the user's PDF viewer app.
	 */
    private void getSchedule(String filename) {
        String sdCardPath = Environment.getExternalStorageDirectory().getPath() + "/Download/" + filename;
        Log.i("sd card path", sdCardPath);
        File busSchedule = new File(sdCardPath);
        if (!busSchedule.exists())
        {
            Log.i("bus schedule", "schedule does not exist on device. will copy");
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

    public void get10(View view) {
        String filename = "rte_10.pdf";
        getSchedule(filename);
    }

    public void get15(View view) {
        String filename = "rte_15.pdf";
        getSchedule(filename);
    }
    
    public void get16(View view) {
        String filename = "rte_16.pdf";
        getSchedule(filename);
    }
    
    public void get19(View view) {
        String filename = "rte_19.pdf";
        getSchedule(filename);
    }
    
    public void get20(View view) {
        String filename = "rte_20.pdf";
        getSchedule(filename);
    }
    
    public void getNC(View view) {
        String filename = "rte_nc.pdf";
        getSchedule(filename);
    }
}
