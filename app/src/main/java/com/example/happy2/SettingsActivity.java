package com.example.happy2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;

import com.example.happy2.Dialogs.TimePickerFragment;
import com.example.happy2.Notifications.AlertReceiver;

import java.text.DateFormat;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    public static final String SETTINGS_PREFERENCE = "com.example.happy2.SETTINGS_PREFERENCE";
    public static final String KEY_NOTIFICATION_ON_OFF = "com.example.happy2.KEY_NOTIFICATION_ON_OFF";
    public static final String KEY_NOTIFICATION_TIME = "com.example.happy2.KEY_NOTIFICATION_TIME";
    public static final String KEY_NR_DAYS_UNHAPPY_OK = "com.example.happy2.KEY_NR_DAYS_UNHAPPY_OK";
    public static final String KEY_NR_HOURS_STILL_YESTERDAY = "com.example.happy2.KEY_NR_HOURS_STILL_YESTERDAY";

    private Switch switchNotification;
    private TextView tvNotificationTime;
    private TextView tvNotificationTimeChangeHint;
    private ImageButton btnChangeTime;

    private Calendar calendar;
    private SharedPreferences sharedPreferences;

    private boolean notificationOnOff;
    private String notificationTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);

        sharedPreferences = getSharedPreferences(SETTINGS_PREFERENCE, MODE_PRIVATE);
        getViews();
        loadData();
        updateViews();

        switchNotification.setOnClickListener(onSwitchNotificationClick);
        btnChangeTime.setOnClickListener(onTimePickerClick);
    }

    private void getViews() {
        switchNotification = (Switch) findViewById(R.id.switchNotification);
        tvNotificationTime = findViewById(R.id.tvNotificationTime);
        tvNotificationTimeChangeHint = findViewById(R.id.tvNotificationTimeChangeHint);
        btnChangeTime = findViewById(R.id.btnChangeTime);
    }


    private View.OnClickListener onSwitchNotificationClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            notificationOnOff = switchNotification.isChecked();
            setSwitch();
            if (!notificationOnOff) cancelAlarm();
            saveNotificationOnOff();
        }
    };

    private View.OnClickListener onTimePickerClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        }
    };





    /* ***************************************************
    Shared Preferences stuff
    *************************************************** */

    public void saveNotificationTime() {
        sharedPreferences
                .edit()
                .putString(KEY_NOTIFICATION_TIME, getTimeString())
                .apply();
    }

    public void saveNotificationOnOff() {
        sharedPreferences
                .edit()
                .putBoolean(KEY_NOTIFICATION_ON_OFF, notificationOnOff)
                .apply();
    }

    public void loadData() {
        notificationTime = sharedPreferences.getString(KEY_NOTIFICATION_TIME, "20:00");
        notificationOnOff = sharedPreferences.getBoolean(KEY_NOTIFICATION_ON_OFF, true);
    }

    public void updateViews() {
        setSwitch();
        setTimeText();
    }

    private void setSwitch() {
        switchNotification.setChecked(notificationOnOff);
        tvNotificationTime.setEnabled(notificationOnOff);
        tvNotificationTimeChangeHint.setEnabled(notificationOnOff);
        btnChangeTime.setEnabled(notificationOnOff);
        if (notificationOnOff){
            setTimeText();
            tvNotificationTimeChangeHint.setText(getString(R.string.notification_click_to_change));
        } else {
            tvNotificationTime.setText(getString(R.string.notification_off));
            tvNotificationTimeChangeHint.setText(getString(R.string.notification_off_hint));
        }
    }

    private void setTimeText() {
        tvNotificationTime.setText(getString(R.string.notification_time) + " " + notificationTime);
    }



    /* ***************************************************
    Implementation of TimePickerDialog.OnTimeSetListener
    *************************************************** */

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        updateTimeText();
        startAlarm();
        saveNotificationTime();
    }
    private void updateTimeText() {
        String timeText = getResources().getString(R.string.notification_time);
        timeText += getTimeString();
        tvNotificationTime.setText(timeText);
    }
    private String getTimeString() {
        return DateFormat.getTimeInstance(DateFormat.SHORT).format(calendar.getTime());
    }
    private void startAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (calendar.before(Calendar.getInstance())) {
            calendar.add(Calendar.DATE, 1);
        }
        //alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                //AlarmManager.INTERVAL_,
                //
                2*60*1000, pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}