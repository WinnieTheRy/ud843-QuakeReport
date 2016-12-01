package com.example.android.quakereport;

import android.content.SharedPreferences;
import android.preference.ListPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
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
    public static class EarthquakePreferenceFragment extends PreferenceFragment implements Preference.OnPreferenceChangeListener {

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            addPreferencesFromResource(R.xml.settings_main);

            /*Given the key of a preference, we can use PreferenceFragment's findPreference() method
            to get the Preference object, and setup the preference using a helper method
            called bindPreferenceSummaryToValue().*/
            Preference minMagnitude = findPreference(getString(R.string.settings_min_magnitude_key));
            bindPreferenceSummaryToValue(minMagnitude);

            Preference orderBy = findPreference(getString(R.string.settings_order_by_key));
            bindPreferenceSummaryToValue(orderBy);
        }


        /**
         * Called when a Preference has been changed by the user. This is
         * called before the state of the Preference is about to be updated and
         * before the state is persisted.
         *
         * @param preference The changed Preference.
         * @param value The new value of the Preference.
         * @return True to update the state of the Preference with the new value.
         */
        @Override
        public boolean onPreferenceChange(Preference preference, Object value) {
            String stringValue = value.toString(); //new value user entered converted to string
            if (preference instanceof ListPreference) {
                ListPreference listPreference = (ListPreference) preference;
                int prefIndex = listPreference.findIndexOfValue(stringValue);
                if (prefIndex >= 0) {
                    CharSequence[] labels = listPreference.getEntries();
                    preference.setSummary(labels[prefIndex]);
                }
            } else {
                preference.setSummary(stringValue);
            }
            return true;
        }


        /**
         * Now we need to define the bindPreferenceSummaryToValue() helper method to set the current
        // EarhtquakePreferenceFragment instance as the listener on each preference. We also read the current value of the
        // preference stored in the SharedPreferences on the device, and display that in the preference summary
        (so that the user can see the current value of the preference).
         */
        private void bindPreferenceSummaryToValue(Preference preference) {
            preference.setOnPreferenceChangeListener(this);
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(preference.getContext());
            String preferenceString = preferences.getString(preference.getKey(), "");
            onPreferenceChange(preference, preferenceString);
        }

    }


}
