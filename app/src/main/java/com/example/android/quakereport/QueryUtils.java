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
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper methods related to requesting and receiving earthquake data from USGS.
 */

public class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * Create a private constructor because no one should ever create a {@link QueryUtils} object.
     * This class is only meant to hold static variables and methods, which can be accessed
     * directly from the class name QueryUtils (and an object instance of QueryUtils is not needed).
     */
    private QueryUtils() {
    }

    public static List<EarthquakeData> fetchEarthquakes(String stringUrl) {

        //Convert url string into a url object
        URL url = urlConverter(stringUrl);

        List<EarthquakeData> earthquakeList = new ArrayList<EarthquakeData>();

        String jsonResponse = "";

        //now that we have url we can make the request to the server
        //once we made the server request we get returned a string of data
        //that needs to be put into a input stream and into a string builder
        //then that data needs to be parsed with json

        try {
            jsonResponse = makeHTTPRequest(url);
        } catch (IOException e) {
            Log.i(LOG_TAG, "error with http request");
        }

        earthquakeList = extractEarthquakes(jsonResponse);


        return earthquakeList;
    }

    private static String makeHTTPRequest(URL url) throws IOException { //add throws so that the one calling this mehtode has to add a ty catch statement

        String response = "";

        if (url == null) {
            return response;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                response = inputStreamBuilder(inputStream);
            } else {
                Log.i(LOG_TAG, "error in connection" + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                inputStream.close();
            }

        }

        return response;

    }

    private static String inputStreamBuilder(InputStream inputStream) throws IOException {

        StringBuilder stringBuilder = null;

        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            //takes the inputstream and is able to cycle through more characters
            BufferedReader reader = new BufferedReader(inputStreamReader);

            String line = reader.readLine();

            //use a while loop to loop through the buffered reader to get all the data
            while (line != null) {
                stringBuilder.append(line);

                //use this line of code as a check to make sure we dont go into an infinite loop
                line = reader.readLine();
            }
        }

        return stringBuilder.toString();
    }

    private static URL urlConverter(String str) {

        URL url = null;

        try {
            url = new URL(str);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return url;
    }


    /**
     * Return a list of {@link EarthquakeData} objects that has been built up from
     * parsing a JSON response.
     */
    private static ArrayList<EarthquakeData> extractEarthquakes(String strJson) {

        // Create an empty ArrayList that we can start adding earthquakes to
        ArrayList<EarthquakeData> earthquakes = new ArrayList<>();

        // Try to parse the SAMPLE_JSON_RESPONSE. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {

            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of Earthquake objects with the corresponding data.

            //creating JSON object from the hard coded JSON string
            JSONObject jsonRootObject = new JSONObject(strJson);

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
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }

        // Return the list of earthquakes to the arraylist
        return earthquakes;
    }

}
