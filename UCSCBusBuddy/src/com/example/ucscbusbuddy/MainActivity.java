package com.example.ucscbusbuddy;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;

public class MainActivity extends Activity {

	public void busSchedule(View view) {
	    Intent intent = new Intent(MainActivity.this, BusScheduleActivity.class );
	    startActivity( intent );
	}

	public void closestStop(View view) {
	    Intent intent = new Intent(MainActivity.this, ClosestStopActivity.class );
	    startActivity( intent );
	}

	public void selectStop(View view) {
	    Intent intent = new Intent(MainActivity.this, SelectStopActivity.class );
	    startActivity( intent );
	}
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
