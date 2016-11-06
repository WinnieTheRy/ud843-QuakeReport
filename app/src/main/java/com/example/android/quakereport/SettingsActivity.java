package com.example.android.quakereport;

import android.preference.PreferenceFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //this opens the xml layout for the settings the user can change
        setContentView(R.layout.activity_settings);
    }

    /**
     * A non-static nested class in Java contains an implicit reference to an instance of the parent class. ...
     * Non-static nested classes (inner classes) have access to other members of the enclosing class,
     * even if they are declared private. Static nested classes do not have access to other members of the enclosing class.
     */
    public static class EarthquakePreferenceFragment extends PreferenceFragment {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);
        }
    }
}
