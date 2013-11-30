package com.example.ucscbusbuddy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class BusStop {
    private String name;
    private List<Calendar> bus10Times;
    private List<Calendar> bus15Times;
    private List<Calendar> bus16Times;
    private List<Calendar> bus19Times;
    private List<Calendar> bus20Times;
    private List<Calendar> busncTimes;
    
    public BusStop () {
        name = new String();
        bus10Times = new ArrayList<Calendar>();
        bus15Times = new ArrayList<Calendar>();
        bus16Times = new ArrayList<Calendar>();
        bus19Times = new ArrayList<Calendar>();
        bus20Times = new ArrayList<Calendar>();
        busncTimes = new ArrayList<Calendar>();
    }
    
    public BusStop (String aName) {
        name = aName;
        bus10Times = new ArrayList<Calendar>();
        bus15Times = new ArrayList<Calendar>();
        bus16Times = new ArrayList<Calendar>();
        bus19Times = new ArrayList<Calendar>();
        bus20Times = new ArrayList<Calendar>();
        busncTimes = new ArrayList<Calendar>();
    }
    
    public String getName () {
        return name;
    }
    
    public List<Calendar> getBusTimes (String busNumber) {
        if (busNumber.compareTo("10") == 0)
            return bus10Times;
        
        if (busNumber.compareTo("15") == 0)
            return bus15Times;
        
        if (busNumber.compareTo("16") == 0)
            return bus16Times;
        
        if (busNumber.compareTo("19") == 0)
            return bus19Times;
        
        if (busNumber.compareTo("20") == 0)
            return bus20Times;
        
        if (busNumber.compareTo("nc") == 0)
            return busncTimes;
        
        return null;
    }
    
    public int addBusTime (String busNumber, Calendar busTime) {
        if (busNumber.compareTo("10") == 0) {
            bus10Times.add(busTime);
            return 0;
        } else if (busNumber.compareTo("15") == 0) {
            bus15Times.add(busTime);
            return 0;
        } else if (busNumber.compareTo("16") == 0) {
            bus16Times.add(busTime);
            return 0;
        } else if (busNumber.compareTo("19") == 0) {
            bus19Times.add(busTime);
            return 0;
        } else if (busNumber.compareTo("20") == 0) {
            bus20Times.add(busTime);
            return 0;
        } else if (busNumber.compareTo("nc") == 0) {
            busncTimes.add(busTime);
            return 0;
        }
        
        return 1;
    }

    @Override
    public String toString () {
        return name;
    }
}
