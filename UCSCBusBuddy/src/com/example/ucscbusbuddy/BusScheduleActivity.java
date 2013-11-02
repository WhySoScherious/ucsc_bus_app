package com.example.ucscbusbuddy;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.NavUtils;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;

public class BusScheduleActivity extends Activity {
    public void get10(View view) {
        Intent intent = new Intent(BusScheduleActivity.this, TenActivity.class );
        startActivity( intent );
    }

    public void get15(View view) {
        Intent intent = new Intent(BusScheduleActivity.this, FifteenActivity.class );
        startActivity( intent );
    }
    
    public void get16(View view) {
        Intent intent = new Intent(BusScheduleActivity.this, SixteenActivity.class );
        startActivity( intent );
    }
    
    public void get19(View view) {
        Intent intent = new Intent(BusScheduleActivity.this, NineteenActivity.class );
        startActivity( intent );
    }
    
    public void get20(View view) {
        Intent intent = new Intent(BusScheduleActivity.this, TwentyActivity.class );
        startActivity( intent );
    }
    
    public void getNC(View view) {
        Intent intent = new Intent(BusScheduleActivity.this, NCActivity.class );
        startActivity( intent );
    }

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
}
