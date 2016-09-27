package com.example.android.quakereport;

/**
 * Created by RyanHaniff on 2016-09-26.
 */

public class EarthquakeData {

    private double mMagnitude;

    private String mCityName;

    private String mDate;


    //Constructor
    public EarthquakeData(double magnitude, String cityName, String date){

        mMagnitude = magnitude;
        mCityName = cityName;
        mDate = date;

    }

    //getter methods
    public double getmMagnitude() {
        return mMagnitude;
    }

    public String getmCityName() {
        return mCityName;
    }

    public String getmDate() {
        return mDate;
    }

    //setter methods
    public void setmDate(String mDate) {
        this.mDate = mDate;
    }

    public void setmMagnitude(double mMagnitude) {
        this.mMagnitude = mMagnitude;
    }

    public void setmCityName(String mCityName) {
        this.mCityName = mCityName;
    }

}
