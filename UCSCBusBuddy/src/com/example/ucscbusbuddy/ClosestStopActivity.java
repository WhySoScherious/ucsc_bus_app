package com.example.ucscbusbuddy;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class ClosestStopActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_closest_stop);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.closest_stop, menu);
		return true;
	}

}
