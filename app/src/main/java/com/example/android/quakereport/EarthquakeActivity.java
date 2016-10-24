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

public class EarthquakeActivity extends AppCompatActivity {

    /*TODO: make a inner class for the async tasks
      TODO: make a global variable for the url
      TODO: modify android manifest for connecting to the internet
     */


    public static final String LOG_TAG = EarthquakeActivity.class.getName();

    public static final String USGS_URL = "http://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.earthquake_activity);

        //final ArrayList<EarthquakeData> earthquakeDatas = QueryUtils.extractEarthquakes();

        BackgroundNetworkConnection backgroundNetworkCOnnection = new BackgroundNetworkConnection();
        backgroundNetworkCOnnection.execute(USGS_URL);

    }

    private class BackgroundNetworkConnection extends AsyncTask<String, Void, ArrayList<EarthquakeData>> {


        @Override
        protected ArrayList<EarthquakeData> doInBackground(String... url) {

            if (url.length < 1 || url == null) {
                return null;
            }

            ArrayList<EarthquakeData> earthquake = QueryUtils.fetchEarthquakeData(url[0]);
            return earthquake;
        }

        @Override
        protected void onPostExecute(final ArrayList<EarthquakeData> earthquakeDatas) {

            ListView earthquakeListView = (ListView) findViewById(R.id.list);

            earthquakeListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    //find the current earthquake that was clicked on
                    EarthquakeData earthquakePositionInList = earthquakeDatas.get(position);

                    //Calls the helper methode openBroser to send the string url into it
                    openBrowser(earthquakePositionInList.getURL());
                }
            });

            EarthquakeAdapter adapter = new EarthquakeAdapter(EarthquakeActivity.this, earthquakeDatas);

            earthquakeListView.setAdapter(adapter);


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
