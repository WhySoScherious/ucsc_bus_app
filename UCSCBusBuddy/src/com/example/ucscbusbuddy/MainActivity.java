package com.example.ucscbusbuddy;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

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
/*
<<<<<<< HEAD
    private void setButtonClickListener(int idButton) {
        Button toggleButton = (Button)findViewById(idButton);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (v.getId() == R.id.bus_schedule)
                    busSchedule (v);
                else if (v.getId() == R.id.closest_stop)
                    closestStop (v);
                else
                    selectStop (v);
            }
        });
=======
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
>>>>>>> refs/heads/cole
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
