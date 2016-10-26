package com.example.android.quakereport;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.List;

/**
 * Created by Ryan PC on 2016-10-26.
 */

public class BackgroundNetworkConnection extends AsyncTaskLoader<List<EarthquakeData>> {

    public BackgroundNetworkConnection(Context context) {
        super(context);
    }


    @Override
    public List<EarthquakeData> loadInBackground() {

        String url = EarthquakeActivity.USGS_URL;

        if(url == null) {
            return null;
        }



        return null;
    }
}
