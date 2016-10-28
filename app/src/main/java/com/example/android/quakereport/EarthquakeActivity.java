/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.android.quakereport;

import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<EarthquakeData>> {

    /*TODO: make a inner class for the async tasks
      TODO: make a global variable for the url
      TODO: modify android manifest for connecting to the internet
     */


    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    public static final String USGS_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&orderby=time&minmag=5&limit=10";

    private EarthquakeAdapter mAdapter;

    private TextView mEmptyStateTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        ListView listView = (ListView) findViewById(R.id.list);
        //pass in a empty arraylist
        mAdapter = new EarthquakeAdapter(this, new ArrayList<EarthquakeData>());
        listView.setAdapter(mAdapter);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);

        //sets a text display if there is not list items to update
        listView.setEmptyView(mEmptyStateTextView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                EarthquakeData earthquakePositionInList = mAdapter.getItem(position);

                openBrowser(earthquakePositionInList.getURL());

            }
        });

        //Checking to see if we have network connection
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        //if we are connected to internet run this
        if (networkInfo != null && networkInfo.isConnected()) {
            getLoaderManager().initLoader(0, null, this);
        } else {
            //set the spinner loader to invisible so error message will be visible
            ProgressBar mSpinnerProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
            mSpinnerProgressBar.setVisibility(View.GONE);

            //setting text to "No network connection"
            mEmptyStateTextView.setText(R.string.no_network);
        }


        Log.v(LOG_TAG, "TEST: OnCreate methode");

    }

    @Override
    public Loader<List<EarthquakeData>> onCreateLoader(int id, Bundle args) {

        Log.v(LOG_TAG, "TEST: onCreateLoader methode");

        return new EarthquakeLoader(this, USGS_URL);


    }

    @Override
    public void onLoadFinished(Loader<List<EarthquakeData>> loader, List<EarthquakeData> data) {

        mAdapter.clear();

        if (data != null) {
            mAdapter.addAll(data);
        }

        //set progress bar to invisible when finshed loading data
        //remember that the prgress bar is always being displayed unless
        // we set visibility to gone
        ProgressBar mSpinnerProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mSpinnerProgressBar.setVisibility(View.GONE);

        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        mEmptyStateTextView.setText(R.string.no_earthquake);

        Log.v(LOG_TAG, "TEST: onLoadFinished methode");

    }

    @Override
    public void onLoaderReset(Loader<List<EarthquakeData>> loader) {
        mAdapter.clear();

        Log.v(LOG_TAG, "TEST: onLoaderReset methode");
    }

//    private class BackgroundNetworkConnection extends AsyncTaskLoader<ArrayList<EarthquakeData>> {
//
//
//        public BackgroundNetworkConnection(Context context) {
//            super(context);
//        }
//
//        @Override
//        public ArrayList<EarthquakeData> loadInBackground() {
//
//            if (USGS_URL == null) {
//                return null;
//            }
//
//            ArrayList<EarthquakeData> earthquakeList = QueryUtils.fetchEarthquakeData(USGS_URL);
//
//            return earthquakeList;
//        }

//        @Override
//        protected ArrayList<EarthquakeData> doInBackground(String... url) {
//
//            if (url.length < 1 || url == null) {
//                return null;
//            }
//
//            ArrayList<EarthquakeData> earthquake = QueryUtils.fetchEarthquakeData(url[0]);
//            return earthquake;
//        }
//
//        @Override
//        protected void onPostExecute(final ArrayList<EarthquakeData> earthquakeDatas) {
//
//            ListView earthquakeListView = (ListView) findViewById(R.id.list);
//
//            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                @Override
//                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                    //find the current earthquake that was clicked on
//                    EarthquakeData earthquakePositionInList = earthquakeDatas.get(position);
//
//                    //Calls the helper methode openBroser to send the string url into it
//                    openBrowser(earthquakePositionInList.getURL());
//                }
//            });
//
//            EarthquakeAdapter adapter = new EarthquakeAdapter(EarthquakeActivity.this, earthquakeDatas);
//
//            if (earthquakeDatas != null) {
//                earthquakeListView.setAdapter(adapter);
//            }
//
//
//        }
//    }


    private void openBrowser(String url) {

        //convert the string into a URI object
        Uri webPage = Uri.parse(url);

        Intent webpageIntent = new Intent(Intent.ACTION_VIEW, webPage);
        //If there are no apps on the device that can receive the implicit intent, your app will crash when it calls startActivity()
        if (webpageIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(webpageIntent);
        }

    }

}
