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

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class EarthquakeActivity extends AppCompatActivity {

    private static final String LOG_TAG = EarthquakeActivity.class.getName();

    private static final String USGS_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";

    private EarthquakeAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);


        // Find a reference to the {@link ListView} in the layout
        //Shows a list of items on screen such as the names of each city
        ListView earthquakeListView = (ListView) findViewById(R.id.list);

        // Create a new adapter that takes an empty list of earthquakes as input
        mAdapter = new EarthquakeAdapter(this, new ArrayList<EarthquakeData>());

        //poplate the listview with the Arraylist adapter class
        earthquakeListView.setAdapter(mAdapter);

        earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //find the current earthquake that was clicked on
                EarthquakeData earthquakePositionInList = mAdapter.getItem(position);

                //Calls the helper methode openBroser to send the string url into it
                openBrowser(earthquakePositionInList.getURL());
            }
        });

        new EarthquakeAsync().execute(USGS_URL);


    }

    private class EarthquakeAsync extends AsyncTask<String, Void, List<EarthquakeData>> {

        @Override
        protected List<EarthquakeData> doInBackground(String... urls) {

            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<EarthquakeData> earthquakesList = QueryUtils.fetchEarthquakes(urls[0]);

            return earthquakesList;
        }

        @Override
        protected void onPostExecute(List<EarthquakeData> earthquakeDatas) {

            mAdapter.clear();


            // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (earthquakeDatas != null && !earthquakeDatas.isEmpty()) {
                mAdapter.addAll(earthquakeDatas);
            }

        }
    }

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
