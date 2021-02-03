package com.example.happy2.Notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;
import static com.example.happy2.SettingsActivity.KEY_NOTIFICATION_ON_OFF;
import static com.example.happy2.SettingsActivity.KEY_NOTIFICATION_TIME_MILLIS;
import static com.example.happy2.SettingsActivity.SETTINGS_PREFERENCE;

public class BootCompletedIntentReceiver extends BroadcastReceiver {

    private Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        if ("android.intent.action.BOOT_COMPLETED".equals(intent.getAction())) {
            startAlarm();
        }
    }

    private void startAlarm() {
        // get time to start alarm
        Calendar calendar = Calendar.getInstance();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SETTINGS_PREFERENCE, MODE_PRIVATE);

        if (sharedPreferences.getBoolean(KEY_NOTIFICATION_ON_OFF, true)) {
            Long timeInMillis = sharedPreferences.getLong(KEY_NOTIFICATION_TIME_MILLIS, calendar.getTimeInMillis());
            calendar.setTimeInMillis(timeInMillis);
            while (calendar.before(Calendar.getInstance())) {
                //calendar.add(Calendar.DATE, 1);
                calendar.add(Calendar.MINUTE, 2);
            }
            calendar.add(Calendar.MINUTE, 1);

            // create pendingIntent for alarm
            Intent intent = new Intent(context, AlertReceiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

            // start alarm
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
                    calendar.getTimeInMillis(),
                    AlarmManager.INTERVAL_DAY,
                    pendingIntent);
        }
    }
}