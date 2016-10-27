package com.example.android.quakereport;


import android.util.Log;

public class EarthquakeData {

    private static final String LOG_TAG = EarthquakeData.class.getName();

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

        Log.v(LOG_TAG, "TEST: EarthqaukeData Contructor");
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
