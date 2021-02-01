package com.example.happy2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.happy2.R;

import java.text.DateFormat;
import java.util.Calendar;

public class SettingsActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener {

    //TODO: check if that would be possible with preference manager:
    // https://stackoverflow.com/questions/51343550/how-to-give-notifications-on-android-on-specific-time-in-android-oreo

    private Switch switchNotification;
    private TextView tvNotificationTime;
    private TextView tvNotificationTimeChangeHint;
    private ImageButton btnChangeTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);

        switchNotification = (Switch) findViewById(R.id.switchNotification);
        tvNotificationTime = findViewById(R.id.tvNotificationTime);
        tvNotificationTimeChangeHint = findViewById(R.id.tvNotificationTimeChangeHint);
        btnChangeTime = findViewById(R.id.btnChangeTime);

        switchNotification.setOnClickListener(onSwitchNotificationClick);
        btnChangeTime.setOnClickListener(onTimePickerClick);
    }



    private View.OnClickListener onSwitchNotificationClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            boolean isChecked = switchNotification.isChecked();
            tvNotificationTime.setEnabled(isChecked);
            tvNotificationTimeChangeHint.setEnabled(isChecked);
            btnChangeTime.setEnabled(isChecked);
            if(isChecked){
                tvNotificationTime.setText(R.string.notification_time);
                tvNotificationTimeChangeHint.setText(R.string.notification_click_to_change);
            }else{
                tvNotificationTime.setText(R.string.notification_off);
                tvNotificationTimeChangeHint.setText(R.string.notification_off_hint);
                cancelAlarm();
            }
        }
    };

    private View.OnClickListener onTimePickerClick = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            DialogFragment timePicker = new TimePickerFragment();
            timePicker.show(getSupportFragmentManager(), "time picker");
        }
    };


    /* Implementation of TimePickerDialog.OnTimeSetListener
    */

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, hourOfDay);
        c.set(Calendar.MINUTE, minute);
        c.set(Calendar.SECOND, 0);
        updateTimeText(c);
        startAlarm(c);
    }
    private void updateTimeText(Calendar c) {
        String timeText = getResources().getString(R.string.notification_time);
        timeText += DateFormat.getTimeInstance(DateFormat.SHORT).format(c.getTime());
        tvNotificationTime.setText(timeText);
    }
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);
    }

    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}