package com.example.android.quakereport;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by RyanHaniff on 2016-09-26.
 */

public class EarthquakeAdapter extends ArrayAdapter<EarthquakeData> {

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
        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_for_list_view, parent, false);
        }

        // Get the {@link EarthquakeData} object located at this position in the list
        EarthquakeData currentEarthquake = getItem(position);

        //Setting the magnitude to a textview
        TextView magnitudeTextView = (TextView) listItemView.findViewById(R.id.magnitude_text_view);
        magnitudeTextView.setText(Double.toString(currentEarthquake.getmMagnitude()));

        //setting the location to a textView
        TextView locationTextView = (TextView) listItemView.findViewById(R.id.location_text_view);
        locationTextView.setText(currentEarthquake.getmCityName());

        //setting the date to a textView
        TextView dateTextView = (TextView) listItemView.findViewById(R.id.date_text_view);
        dateTextView.setText(currentEarthquake.getmDate());

        //All for a sing list Item inside the arrayList
        return listItemView;
    }
}
