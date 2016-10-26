package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeLoader extends AsyncTaskLoader<List<EarthquakeData>> {

    public EarthquakeLoader(Context context){
        super(context);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<EarthquakeData> loadInBackground() {

        ArrayList<EarthquakeData> earthquakeList = QueryUtils.fetchEarthquakeData(EarthquakeActivity.USGS_URL);

        return earthquakeList;
    }
}
