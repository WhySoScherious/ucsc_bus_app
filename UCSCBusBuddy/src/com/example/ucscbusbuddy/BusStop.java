package com.example.ucscbusbuddy;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.os.Parcel;
import android.os.Parcelable;

public class BusStop implements Parcelable{
    private String name;
    private double latitude;
    private double longitude;
    private ArrayList<Calendar> bus10Times;
    private ArrayList<Calendar> bus15Times;
    private ArrayList<Calendar> bus16Times;
    private ArrayList<Calendar> bus19Times;
    private ArrayList<Calendar> bus20Times;
    private ArrayList<Calendar> busncTimes;
    private boolean tenHasMF;
    private boolean tenHasSS;
    private boolean fifteenHasMF;
    private boolean fifteenHasSS;
    private boolean sixteenHasMF;
    private boolean sixteenHasSS;
    private boolean nineteenHasMF;
    private boolean nineteenHasSS;
    private boolean twentyHasMF;
    private boolean twentyHasSS;
    
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
        tenHasMF = false;
        tenHasSS = false;
        fifteenHasMF = false;
        fifteenHasSS = false;
        sixteenHasMF = false;
        sixteenHasSS = false;
        nineteenHasMF = false;
        nineteenHasSS = false;
        twentyHasMF = false;
        twentyHasSS = false;
    }

    public BusStop (String aName) {
        name = aName;
        bus10Times = new ArrayList<Calendar>();
        bus15Times = new ArrayList<Calendar>();
        bus16Times = new ArrayList<Calendar>();
        bus19Times = new ArrayList<Calendar>();
        bus20Times = new ArrayList<Calendar>();
        busncTimes = new ArrayList<Calendar>();
        tenHasMF = false;
        tenHasSS = false;
        fifteenHasMF = false;
        fifteenHasSS = false;
        sixteenHasMF = false;
        sixteenHasSS = false;
        nineteenHasMF = false;
        nineteenHasSS = false;
        twentyHasMF = false;
        twentyHasSS = false;
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

    public boolean get10HasMF () {
        return tenHasMF;
    }
    
    public boolean get10HasSS () {
        return tenHasSS;
    }
    
    public boolean get15HasMF () {
        return fifteenHasMF;
    }
    
    public boolean get15HasSS () {
        return fifteenHasSS;
    }
    
    public boolean get16HasMF () {
        return sixteenHasMF;
    }
    
    public boolean get16HasSS () {
        return sixteenHasSS;
    }
    
    public boolean get19HasMF () {
        return nineteenHasMF;
    }
    
    public boolean get19HasSS () {
        return nineteenHasSS;
    }
    
    public boolean get20HasMF () {
        return twentyHasMF;
    }
    
    public boolean get20HasSS () {
        return twentyHasSS;
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
        dest.writeByte((byte) (tenHasMF ? 1 : 0));
        dest.writeByte((byte) (tenHasSS ? 1 : 0));
        dest.writeByte((byte) (fifteenHasMF ? 1 : 0));
        dest.writeByte((byte) (fifteenHasSS ? 1 : 0));
        dest.writeByte((byte) (sixteenHasMF ? 1 : 0));
        dest.writeByte((byte) (sixteenHasSS ? 1 : 0));
        dest.writeByte((byte) (nineteenHasMF ? 1 : 0));
        dest.writeByte((byte) (nineteenHasSS ? 1 : 0));
        dest.writeByte((byte) (twentyHasMF ? 1 : 0));
        dest.writeByte((byte) (twentyHasSS ? 1 : 0));
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
        tenHasMF = source.readByte() != 0;
        tenHasSS = source.readByte() != 0;
        fifteenHasMF = source.readByte() != 0;
        fifteenHasSS = source.readByte() != 0;
        sixteenHasMF = source.readByte() != 0;
        sixteenHasSS = source.readByte() != 0;
        nineteenHasMF = source.readByte() != 0;
        nineteenHasSS = source.readByte() != 0;
        twentyHasMF = source.readByte() != 0;
        twentyHasSS = source.readByte() != 0;
    }

    public ArrayList<Calendar> getBusTimes (String busNumber) {
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
            
            if (busTime.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                tenHasMF = true;
            } else {
                tenHasSS = true;
            }
            return 0;
        } else if (busNumber.compareTo("15") == 0) {
            bus15Times.add(busTime);
            
            if (busTime.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                fifteenHasMF = true;
            } else {
                fifteenHasSS = true;
            }
            return 0;
        } else if (busNumber.compareTo("16") == 0) {
            bus16Times.add(busTime);
            
            if (busTime.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                sixteenHasMF = true;
            } else {
                sixteenHasSS = true;
            }
            return 0;
        } else if (busNumber.compareTo("19") == 0) {
            bus19Times.add(busTime);
            
            if (busTime.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                nineteenHasMF = true;
            } else {
                nineteenHasSS = true;
            }
            return 0;
        } else if (busNumber.compareTo("20") == 0) {
            bus20Times.add(busTime);
            
            if (busTime.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                twentyHasMF = true;
            } else {
                twentyHasSS = true;
            }
            return 0;
        } else if (busNumber.compareTo("nc") == 0) {
            busncTimes.add(busTime);
            return 0;
        }

        return 1;
    }

    public static String printTime (Calendar time) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm", Locale.US);
        String test = sdf.format(time.getTime());
        return test;
    }

    @Override
    public String toString () {
        return name;
    }
}
