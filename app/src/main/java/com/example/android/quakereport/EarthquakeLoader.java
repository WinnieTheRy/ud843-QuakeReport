package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;


public class EarthquakeLoader extends AsyncTaskLoader<List<EarthquakeData>> {


    private String mUrl;

    public EarthquakeLoader(Context context, String url){
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<EarthquakeData> loadInBackground() {

        ArrayList<EarthquakeData> earthquakeList = QueryUtils.fetchEarthquakeData(mUrl);

        return earthquakeList;
    }
}
