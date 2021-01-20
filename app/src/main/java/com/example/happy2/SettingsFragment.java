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
            public boolean onPreferenceChange(Preference preference, Object nv) {
                boolean newValue = (boolean) nv;
                ((SwitchPreference) preference).setChecked(newValue);
                return true;
            }
        });
        preferenceTimerOn.setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() {
            @Override
            public boolean onPreferenceClick(Preference preference) {
//                if(((SwitchPreference) preference).isChecked()){
//                    DialogFragment timePicker = new TimePickerFragment();
//                    timePicker.show(getChildFragmentManager(), "time picker");
//                }
                return false;
            }
        });

    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

    }
}