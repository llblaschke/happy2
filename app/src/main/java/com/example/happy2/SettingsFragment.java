package com.example.happy2;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.widget.TimePicker;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import androidx.preference.SwitchPreference;

public class SettingsFragment extends PreferenceFragmentCompat implements TimePickerDialog.OnTimeSetListener {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);

        final Preference preferenceTimerOn = getPreferenceManager().findPreference("switchPrefSelectedTime");
        preferenceTimerOn.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                ((SwitchPreference) preference).setChecked((boolean) newValue);
                return true;
            }
        });

        final Preference preferenceAbout = getPreferenceManager().findPreference("editTextbout");
        preferenceAbout.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                return false;
            }
        });
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}