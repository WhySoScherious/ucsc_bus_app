package com.example.ucscbusbuddy;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import android.os.Parcel;
import android.os.Parcelable;

public class BusStop implements Parcelable{
    private String name;
    private double latitude;
    private double longitude;
    private List<Calendar> bus10Times;
    private List<Calendar> bus15Times;
    private List<Calendar> bus16Times;
    private List<Calendar> bus19Times;
    private List<Calendar> bus20Times;
    private List<Calendar> busncTimes;

    public static final Parcelable.Creator<BusStop> CREATOR =
            new Parcelable.Creator<BusStop>() {

        @Override
        public BusStop createFromParcel(Parcel source) {
            return new BusStop(source);
        }

        @Override
        public BusStop[] newArray(int size) {
            return new BusStop[size];
        }
    };

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

    private BusStop (Parcel source) {
        readFromParcel (source);
    }

    public String getName () {
        return name;
    }

    public double getLat () {
        return latitude;
    }

    public double getLong () {
        return longitude;
    }

    public void setLat (double aLat) {
        latitude = aLat;
    }

    public void setLong (double aLong) {
        longitude = aLong;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeDouble(latitude);
        dest.writeDouble(longitude);
        dest.writeList(bus10Times);
        dest.writeList(bus15Times);
        dest.writeList(bus16Times);
        dest.writeList(bus19Times);
        dest.writeList(bus20Times);
        dest.writeList(busncTimes);
    }

    public void readFromParcel(Parcel source) {
        name = source.readString();
        latitude = source.readDouble();
        longitude = source.readDouble();
        bus10Times = new ArrayList<Calendar>();
        source.readList(bus10Times, null);
        bus15Times = new ArrayList<Calendar>();
        source.readList(bus15Times, null);
        bus16Times = new ArrayList<Calendar>();
        source.readList(bus16Times, null);
        bus19Times = new ArrayList<Calendar>();
        source.readList(bus19Times, null);
        bus20Times = new ArrayList<Calendar>();
        source.readList(bus20Times, null);
        busncTimes = new ArrayList<Calendar>();
        source.readList(busncTimes, null);
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
