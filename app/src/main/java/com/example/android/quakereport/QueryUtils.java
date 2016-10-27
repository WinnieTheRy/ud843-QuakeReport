package com.example.android.quakereport;


import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */

public class QueryUtils {

    /*
    TODO: Make the connection methode;
    TODO: modify queryutils class to make a mehtode for the json response
      TODO: make a methode for the input stream
      TODO: Create URL methode
     */


    private static final String LOG_TAG = QueryUtils.class.getName();


    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    /**
     * Return a list of {@link EarthquakeData} objects that has been built up from
     * parsing a JSON response.
     */

    public static ArrayList<EarthquakeData> fetchEarthquakeData(String requestUrl) {
        String jsonResponse = "";

        URL url = makeURL(requestUrl);

        Log.v(LOG_TAG, "TEST: fetchEarthquakeData methode");

        try {
            jsonResponse = makeHTTPConnection(url);

        } catch (IOException e) {
            Log.i(LOG_TAG, "Error closing input stream", e);
        }

        ArrayList<EarthquakeData> earthquakes = extractEarthquakes(jsonResponse);

        return earthquakes;

    }

    private static URL makeURL(String stringURL) {

        URL url = null;
        try {
            url = new URL(stringURL);
        } catch (MalformedURLException e) {
            Log.i(LOG_TAG, "url malformed exception", e);
        }

        Log.v(LOG_TAG, "TEST: makeURL methode");
        return url;
    }


    private static String makeHTTPConnection(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;


        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromInputStream(inputStream);
            }

        } catch (IOException e) {
            Log.i(LOG_TAG, "TEST: error creating url Connection", e);
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }
        }

        Log.v(LOG_TAG, "TEST: makeHTTPConnection methode");

        return jsonResponse;

    }

    private static String readFromInputStream(InputStream inputStream) throws IOException {

        StringBuilder output = new StringBuilder();

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();

            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        Log.v(LOG_TAG, "TEST: readFromInputStream methode");
        return output.toString();
    }

    private static ArrayList<EarthquakeData> extractEarthquakes(String json) {

        // Create an empty ArrayList to add isntances of a class called EarthquakeData
        ArrayList<EarthquakeData> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

            //creating JSON object from the hard coded JSON string
            JSONObject jsonRootObject = new JSONObject(json);

            //getting the json array with the key value type features
            JSONArray jsonArray = jsonRootObject.getJSONArray("features");

            //creating a for loop to loop through each object in the json features array
            for (int i = 0; i < jsonArray.length(); i++) {
                //getting the object in the jason array one at a time starting at 0 and ending at 9
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                //getting the json obect key value pair properties
                JSONObject propertiesJsonObject = jsonObject.getJSONObject("properties");

                //getting the key value pairs for magnitude place and time
                double magnitude = propertiesJsonObject.optDouble("mag");
                String place = propertiesJsonObject.optString("place");
                //using long since unix time is at 13 digits
                long time = propertiesJsonObject.optLong("time");
                //get the url of each earthquke
                String url = propertiesJsonObject.optString("url");

                //adding the date,time and place value to the earthqaukedatas
                earthquakes.add(new EarthquakeData(magnitude, place, time, url));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes to the arraylist

        Log.v(LOG_TAG, "TEST: extractEarthquakes methode");
        return earthquakes;
    }

}
