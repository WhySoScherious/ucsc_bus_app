package com.example.ucscbusbuddy;

import java.util.ArrayList;
import java.util.Calendar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class ScreenSlidePageFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.fragment_screen_slide_page, container, false);

        TextView tv = (TextView) rootView.findViewById(R.id.busTimes);
        ArrayList<String> busTimes = getArguments().getStringArrayList("times");

        for (int index = 0; index < busTimes.size(); index++) {
            tv.append(busTimes.get(index) + "\n");
        }

        return rootView;
    }

    public static ScreenSlidePageFragment newInstance(ArrayList<Calendar> times) {

        ScreenSlidePageFragment f = new ScreenSlidePageFragment();
        Bundle b = new Bundle();
        b.putStringArrayList ("times", strGetBusTimes (times));

        f.setArguments(b);

        return f;
    }
    
    public static ArrayList<String> strGetBusTimes (ArrayList<Calendar> busTimes) {
        int currentDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);

        // If the current day is M-F, look for times with Monday
        // Else, look for times with Sunday
        if (currentDay > 0 && currentDay < 6) {
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