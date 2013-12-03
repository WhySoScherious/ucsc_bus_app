package com.example.ucscbusbuddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import android.content.res.AssetManager;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

/*
 * Implements Parcelable so that BusStops can be passed from one intent
 * to another.
 */
public class BusStop implements Parcelable{
    private String name;                    // Name of bus stop
    private double latitude;                // Latitude of bus stop
    private double longitude;               // Longitude of bus stop
    private ArrayList<Calendar> bus10Times; // List of times for bus 10 route
    private ArrayList<Calendar> bus15Times; // List of times for bus 15 route
    private ArrayList<Calendar> bus16Times; // List of times for bus 16 route
    private ArrayList<Calendar> bus19Times; // List of times for bus 19 route
    private ArrayList<Calendar> bus20Times; // List of times for bus 20 route
    private ArrayList<Calendar> busncTimes; // List of times for bus nc route
    private boolean tenHasMF;               // True if route 10 has a MF route
    private boolean tenHasSS;               // True if route 10 has a SS route
    private boolean fifteenHasMF;           // True if route 15 has a MF route
    private boolean fifteenHasSS;           // True if route 15 has a SS route
    private boolean sixteenHasMF;           // True if route 16 has a MF route
    private boolean sixteenHasSS;           // True if route 16 has a SS route
    private boolean nineteenHasMF;          // True if route 19 has a MF route
    private boolean nineteenHasSS;          // True if route 19 has a SS route
    private boolean twentyHasMF;            // True if route 20 has a MF route
    private boolean twentyHasSS;            // True if route 20 has a SS route
    
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

    /*
     * Returns a list of bus times for a particular route passed.
     */
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

    /*
     * Adds a bus time to the list of times for a given bus route.
     */
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

    /*
     * Prints the time passed in 12-hour hh:mm format.
     */
    public static String printTime (Calendar time) {
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm", Locale.US);
        String test = sdf.format(time.getTime());
        return test;
    }

    /*
     * Returns an arraylist of Bus Stop objects located in the assets folder.
     * This module begins the processes of parsing through bus stop files and
     * created a list of bus stops.
     */
    public static ArrayList<BusStop> createBusStopList (AssetManager assetManager) {
        ArrayList<BusStop> busStops = new ArrayList<BusStop>();
        String[] busStopList = null;    // List of bus stop filenames

        try {
            // Open assets/bus_stop_files/.
            // Prints to log if folder not found.
            Log.d("BusStopList", "Access files in bus_stop_files/ folder");
            busStopList = assetManager.list("bus_stop_files");
            Log.d("BusStopList", "Get bus stops");
            return getBusStops (busStops, busStopList, assetManager);
        } catch (IOException e) {
            Log.e("assets", "bus_stop_files/ not found");
        }

        return busStops;
    }

    /*
     * Iterates through bus stop files, parsing through the files and
     * creating BusStop objects, complete with buses times at the particular
     * stops. 
     * Returns arraylist of BusStop objects with times.
     */
    private static ArrayList<BusStop> getBusStops (ArrayList<BusStop> busStops,
            String[] busStopFileNames, AssetManager assetManager) {

        for (int index = 0; index < busStopFileNames.length; index++) {
            // Prints to logger if the filename was not found.
            try {
                BufferedReader reader = new BufferedReader
                        (new InputStreamReader(assetManager.
                                open("bus_stop_files/" + busStopFileNames[index])));
                BusStop stop = new BusStop (busStopFileNames[index]);
                Log.d("BusStopList", "Call parseFile");
                stop = parseFile (stop, reader);
                Log.d("BusStopList", "Add " + busStopFileNames[index] + " to list");
                if (stop != null)
                    busStops.add(stop);
            } catch (IOException e) {
                Log.e("assets",
                        "bus_stop_files/" + busStopFileNames[index] + " not found");
            }

        }

        return busStops;
    }

    /*
     * Parses through file, generating BusStop objects with the name of
     * the bus stop and its corresponding times.
     * Returns a complete BusStop object for the bus stop specified from
     * the file.
     */
    private static BusStop parseFile (BusStop stop,
            BufferedReader reader) throws IOException {
        // First line of file is the GPS coordinates of stop
        String coordinates = reader.readLine();

        try {
            int commaIndex = coordinates.indexOf(',');
            double latitude = Double.parseDouble(coordinates.substring(0, commaIndex));
            double longitude = Double.parseDouble(coordinates.substring(commaIndex + 1));
            stop.setLat (latitude);
            stop.setLong(longitude);
        } catch (NumberFormatException e) {
            Log.e ("parseFile", stop.getName() + ": Invalid GPS coordinate, skipping file");
            return null;
        } catch (StringIndexOutOfBoundsException e) {
            Log.e ("parseFile", stop.getName() + ": Invalid GPS coordinate, skipping file");
            return null;
        }

        for (String line = reader.readLine(); line != null;
                line = reader.readLine()) {
            String busNumber = new String (line);

            // Get the route of the bus.
            /*
             * Note: A 'mf' represents M-F route time while
             *       a 'ss' represents Sat-Sun route time.
             */
            String route = reader.readLine();

            for (line = reader.readLine(); line != null &&
                    line.compareTo ("stop") != 0; line = reader.readLine()) {
                Calendar busTime = setTime (line);

                if (route.compareTo("mf") == 0) {
                    busTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                } else if (route.compareTo("ss") == 0) {
                    busTime.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
                } else {
                    Log.e ("parseFile", stop.getName() +
                            ": Invalid day of week route in file");
                }

                if (stop.addBusTime(busNumber, busTime) == 1) {
                    Log.e("bus time", line + ": Invalid bus time");
                }
            }
        }
        return stop;
    }

    /*
     * Given a 24-hour formatted HH:mm time, convert it to a Calendar
     * object and return it.
     */
    private static Calendar setTime (String time) {
        Calendar busTime = Calendar.getInstance();
        int startOfHourIndex = 0;
        int endOfHourIndex = 2;
        int startOfMinuteIndex = 3;

        int hour = Integer.parseInt(time.substring(startOfHourIndex, endOfHourIndex));
        int minute = Integer.parseInt(time.substring(startOfMinuteIndex));
        busTime.set(Calendar.AM_PM, Calendar.AM);
        busTime.set(Calendar.HOUR_OF_DAY, hour);
        busTime.set(Calendar.MINUTE, minute);

        return busTime;
    }

    @Override
    public String toString () {
        return name;
    }
}
