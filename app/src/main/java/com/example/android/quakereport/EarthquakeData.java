package com.example.android.quakereport;


public class EarthquakeData {

    private double mMagnitude;

    private String mCityName;

    private long mTimeInMIllisSeconds;

    private String mURL;

    //Constructor
    public EarthquakeData(double magnitude, String cityName, long timeInMillisSeconds, String URL){

        mMagnitude = magnitude;
        mCityName = cityName;
        mTimeInMIllisSeconds = timeInMillisSeconds;
        mURL = URL;

    }

    //getter methods
    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getmCityName() {
        return mCityName;
    }

    public long getmTimeinMillisSeconds() {
        return mTimeInMIllisSeconds;
    }

    public String getURL() {
        return mURL;
    }

}
