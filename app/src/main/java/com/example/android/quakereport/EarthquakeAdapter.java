package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.graphics.drawable.GradientDrawable;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by RyanHaniff on 2016-09-26.
 */

public class EarthquakeAdapter extends ArrayAdapter<EarthquakeData> {

    private String splitLocationOffset;
    private String splitPirmaryLocation;

    private static final String LOCATION_SEPERATOR = " of";

    //Conctructor
    //This is our own custom constructor (it doesn't mirror a superclass constructor).
    //The context is used to inflate the layout file, and the list is the data we want
    //to populate into the lists.
    public EarthquakeAdapter(Activity context, ArrayList<EarthquakeData> earthquakeDatas) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, earthquakeDatas);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //used to populate empty list items on startup
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_for_list_view, parent, false);
        }

        // Get the {@link EarthquakeData} object located at this position in the list
        EarthquakeData currentEarthquake = getItem(position);

        //Setting the magnitude to a textview
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude_text_view);
        magnitudeTextView.setText(formatMagnitude(currentEarthquake.getmMagnitude()));

        // Set the proper background color on the magnitude circle.
        // Fetch the background from the TextView, which is a GradientDrawable.
        GradientDrawable magnitudeCircleColor = (GradientDrawable) magnitudeTextView.getBackground();

        // Get the appropriate background color based on the current earthquake magnitude value
        int magnitudeColor = getMagnitudeColor(currentEarthquake.getmMagnitude());

        magnitudeCircleColor.setColor(magnitudeColor);

        if (currentEarthquake.getmCityName().contains(LOCATION_SEPERATOR)) {
            //Splitting the string at the of position, and assigning it to an array
            String[] split = currentEarthquake.getmCityName().split(LOCATION_SEPERATOR);

            //Assinging the fist half of the string to split[0]
            splitLocationOffset = split[split.length - 2] + LOCATION_SEPERATOR;

            //Assinging the second half of the string to split[1]
            splitPirmaryLocation = split[split.length - 1];
        } else {
            //since there is no "of" we assign location ofset to "Near the" and
            //just set the primary location to the city name
            splitLocationOffset = getContext().getString(R.string.near_the);

            //Assinging the second half of the string to the location
            splitPirmaryLocation = currentEarthquake.getmCityName();
        }

        //Setting the offset location to a textview
        TextView locationOffsetTextView = (TextView) listItemView.findViewById(R.id.location_offset_text_view);
        locationOffsetTextView.setText(splitLocationOffset);

        //setting the primary location to a textView
        TextView primaryLocationTextView = (TextView) listItemView.findViewById(R.id.primary_location_text_view);
        primaryLocationTextView.setText(splitPirmaryLocation);

        Date dateObject = new Date(currentEarthquake.getmTimeinMillisSeconds());

        //setting the date to a textView
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(formatDate(dateObject));

        //setting the time to a textView with helper method
        TextView timeTextView = (TextView) listItemView.findViewById(R.id.time_text_view);
        timeTextView.setText(formatTime(dateObject));

        //All for a sing list Item inside the arrayList
        return listItemView;
    }

    private String formatDate(Date date){
        SimpleDateFormat formatDate = new SimpleDateFormat("MMM dd, yyyy");
        return formatDate.format(date);
    }

    private String formatTime(Date time){
        SimpleDateFormat formatTime = new SimpleDateFormat("h:mm a");
        return formatTime.format(time);
    }

    private String formatMagnitude(Double magnitude) {
        //instantiate the DecimalFormatter class to format decimals with two digits, one decimal place
        DecimalFormat decimalFormat = new DecimalFormat("0.0");

        //formats the decimal to have two digits
        return decimalFormat.format(magnitude);
    }

    //used to convert one decimal placing into single digit to be used as switch value
    private String formatMagnitudeColor(double magnitude) {
        DecimalFormat formater = new DecimalFormat("0");

        return formater.format(magnitude);
    }

    private int getMagnitudeColor(double magnitude) {
        int magnitudeColorResourceId = 0;

        //The Math.floor() function returns the largest integer less than or equal to a given number.
        //ex: 45.96 = 45
        int value = (int) Math.floor(magnitude);

        switch (value) {

            case 0:
            case 1:
                magnitudeColorResourceId = R.color.magnitude1;
                break;
            case 2:
                magnitudeColorResourceId = R.color.magnitude2;
                break;
            case 3:
                magnitudeColorResourceId = R.color.magnitude3;
                break;
            case 4:
                magnitudeColorResourceId = R.color.magnitude4;
                break;
            case 5:
                magnitudeColorResourceId = R.color.magnitude5;
                break;
            case 6:
                magnitudeColorResourceId = R.color.magnitude6;
                break;
            case 7:
                magnitudeColorResourceId = R.color.magnitude7;
                break;
            case 8:
                magnitudeColorResourceId = R.color.magnitude8;
                break;
            case 9:
                magnitudeColorResourceId = R.color.magnitude9;
                break;
            case 10:
                magnitudeColorResourceId = R.color.magnitude10plus;
                break;

        }

        //get Context: Returns the context the view is currently running in. Usually the currently active Activity.
        //converts the resource id into a color resource id
        return ContextCompat.getColor(getContext(), magnitudeColorResourceId);
    }

}
