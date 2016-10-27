package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeLoader extends AsyncTaskLoader<List<EarthquakeData>> {

    private static final String LOG_TAG = EarthquakeLoader.class.getName();

    private String mUrl;

    public EarthquakeLoader(Context context, String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();

        Log.v(LOG_TAG, "TEST: onSTartLoading methode");
    }

    @Override
    public List<EarthquakeData> loadInBackground() {

        ArrayList<EarthquakeData> earthquakeList = QueryUtils.fetchEarthquakeData(mUrl);

        Log.v(LOG_TAG, "TEST: loadInBackground method");

        return earthquakeList;
    }
}
