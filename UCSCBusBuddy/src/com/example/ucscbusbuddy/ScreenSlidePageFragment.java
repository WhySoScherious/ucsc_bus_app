package com.example.ucscbusbuddy;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        // TextView of bus times
        TextView tv = (TextView) rootView.findViewById(R.id.busTimes);
        
        // TextView of title printing the bus route number
        TextView title = (TextView) rootView.findViewById(R.id.title);

        String busRoute = getArguments().getString("busRoute");
        ArrayList<String> busTimes = getArguments().getStringArrayList("times");
        
        int position = getArguments().getInt("position");
        int pageCount = getArguments().getInt("pageCount");

        if( position != 0){
            ImageView la = (ImageView) rootView.findViewById(R.id.left_arrow);
            la.setImageDrawable(getResources().getDrawable(R.drawable.left_arrow));
        }
        
        title.setText ("Bus " + busRoute + " Times");
        for (int index = 0; index < busTimes.size(); index++) {
            tv.append(busTimes.get(index) + "\n");
        }
        
        if( position < pageCount -1 ){
            ImageView ra = (ImageView) rootView.findViewById(R.id.right_arrow);
            ra.setImageDrawable(getResources().getDrawable(R.drawable.right_arrow));
        }
        
        return rootView;
    }

    public static ScreenSlidePageFragment newInstance(ArrayList<Calendar> times, String busRoute, int pageCount, int position ) {

        ScreenSlidePageFragment f = new ScreenSlidePageFragment();
        Bundle b = new Bundle();
        b.putString("busRoute", busRoute);
        b.putStringArrayList ("times", strGetBusTimes (times));
        b.putInt("pageCount",pageCount);
        b.putInt("position", position);

        f.setArguments(b);

        return f;
    }

    /*
     * From the passed in list of Calendar objects representing bus times,
     * converts the Calendar objects to a time of a String format,
     * and returns them as an arraylist.
     */
    public static ArrayList<String> strGetBusTimes (ArrayList<Calendar> busTimes) {
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        // If the current day is M-F, look for times with Monday
        // Else, look for times with Sunday
        if (currentDay > 1 && currentDay < 7) {
            currentDay = Calendar.MONDAY;
        } else {
            currentDay = Calendar.SUNDAY;
        }

        ArrayList<String> printBusTimes = new ArrayList<String>();

        for (int index = 0; index < busTimes.size(); index++) {
            Calendar time = busTimes.get(index);
            if (time.get(Calendar.DAY_OF_WEEK) == currentDay) {
                printBusTimes.add(BusStop.printTime (time));
            }
        }
        
        return printBusTimes;
    }
}