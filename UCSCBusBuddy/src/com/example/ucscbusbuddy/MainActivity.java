package com.example.ucscbusbuddy;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void busSchedule( View view ){
    	Intent intent = new Intent( this, BusScheduleActivity.class );
    	startActivity( intent );
    }
    
    public void selectStop( View view ){
    	Intent intent = new Intent( this, SelectStopActivity.class );
    	startActivity( intent );
    }
    
    public void closestStop( View view ){
    	Intent intent = new Intent( this, ClosestStopActivity.class );
    	startActivity( intent );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
