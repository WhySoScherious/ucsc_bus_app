package com.example.ucscbusbuddy;

import java.util.ArrayList;
import java.util.Calendar;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Build;
import android.os.Bundle;
import android.annotation.TargetApi;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class StopInfoActivity extends FragmentActivity {

    private ViewPager mPager;
    private PagerAdapter mPagerAdapter;

    BusStop selectedStop = null;
    ArrayList<BusStop> scBusStops;
    static LatLng stop = null;  // Chosen bus stop coordinates
    private GoogleMap map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_info);

        // Show the Up button in the action bar.
        setupActionBar();

        scBusStops = getIntent().getParcelableArrayListExtra("busStops");
        /*
         * Get stop coordinates and bus stop name from callee activity
         * to show.
         */
       selectedStop = getIntent().getParcelableExtra("selectedStop");
        if(selectedStop != null) {
            stop = new LatLng(selectedStop.getLat(), selectedStop.getLong());
            setTitle (selectedStop.getName());

            initMaps();
            Log.d ("Map", "Map created");
            createSlidePagerAdapter();
        }
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
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
        map.addMarker(new MarkerOptions()
        .position(stop));

        map.setOnMarkerClickListener(new OnMarkerClickListener() {

            @Override
            public boolean onMarkerClick(Marker marker) {
                return true;
            }
        });
    }

    /*
     * Initializes the Google Maps overlay.
     */
    private void initMaps () {
        if (map == null) {
            SupportMapFragment supportMapFragment = (SupportMapFragment)
                    getSupportFragmentManager().findFragmentById(R.id.map);
            map = supportMapFragment.getMap();
            if (map != null && stop != null) {
                map.moveCamera(CameraUpdateFactory.newLatLngZoom(stop, 15));
                createMarkers();
            }
        }
    }

    private void createSlidePagerAdapter () {
        ArrayList<String> busRoutesRunning = getBusRoutesForDay();
        mPager = (ViewPager) findViewById (R.id.pager);
        mPagerAdapter = new ScreenSlidePagerAdapter
                (getSupportFragmentManager(), selectedStop,
                        busRoutesRunning.size(), busRoutesRunning);
        mPager.setAdapter(mPagerAdapter);
    }

    /*
     * Returns a list of bus routes that are running for the current day.
     * This determines how many pages to create for the screen slider.
     */
    private ArrayList<String> getBusRoutesForDay () {
        ArrayList<String> busRoutes = new ArrayList<String>();
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        // If the current day is M-F, look for times with Monday
        // Else, look for times with Sunday
        if (currentDay > 0 && currentDay < 6) {
            if (selectedStop.get10HasMF()) {
                busRoutes.add("10");
            }
            
            if (selectedStop.get15HasMF()) {
                busRoutes.add("15");
            }
            
            if (selectedStop.get16HasMF()) {
                busRoutes.add("16");
            }
            
            if (selectedStop.get19HasMF()) {
                busRoutes.add("19");
            }
            
            if (selectedStop.get20HasMF()) {
                busRoutes.add("20");
            }
        } else {
            if (selectedStop.get10HasSS()) {
                busRoutes.add("10");
            }
            
            if (selectedStop.get15HasSS()) {
                busRoutes.add("15");
            }
            
            if (selectedStop.get16HasSS()) {
                busRoutes.add("16");
            }
            
            if (selectedStop.get19HasSS()) {
                busRoutes.add("19");
            }
            
            if (selectedStop.get20HasSS()) {
                busRoutes.add("20");
            }
        }

        return busRoutes;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.stop_info, menu);
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
            Intent upIntent = new Intent(this, SelectStopActivity.class);
            upIntent.putParcelableArrayListExtra("busStops", scBusStops);

            NavUtils.navigateUpTo(this, upIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);

    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private BusStop stop;
        private int pageCount;
        private ArrayList<String> routesRunning;

        public ScreenSlidePagerAdapter(FragmentManager fm, BusStop aStop,
                int numRoutes, ArrayList<String> routesRunningToday) {
            super(fm);
            this.stop = aStop;
            this.pageCount = numRoutes;
            this.routesRunning = routesRunningToday;
        }

        @Override
        public Fragment getItem(int position) {
            String busRoute = routesRunning.get(position);
            return ScreenSlidePageFragment.newInstance(stop.getBusTimes(busRoute));
        }

        @Override
        public int getCount() {
            return pageCount;
        }
    }
}
