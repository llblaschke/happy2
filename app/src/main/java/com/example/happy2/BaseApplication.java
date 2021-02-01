package com.example.happy2;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class BaseApplication extends Application {
    public static final String CHANNEL_ID_DAILY_NOTIFICATION = "Channel_daily_notification";
    public static final String CHANNEL_ID_UNHAPPY_ALERT = "Channel_unhappy";

    @Override
    public void onCreate() {
        super.onCreate();
        createNotificationChannels();
    }

    private void createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel_daily = new NotificationChannel(
                    CHANNEL_ID_DAILY_NOTIFICATION,
                    getString(R.string.channel_name_daily_notification),
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel_daily.setDescription(getString(R.string.channel_description_daily_notification));

            NotificationChannel channel_unhappy = new NotificationChannel(
                    CHANNEL_ID_UNHAPPY_ALERT,
                    getString(R.string.channel_name_alert_unhappy),
                    NotificationManager.IMPORTANCE_DEFAULT
            );
            channel_unhappy.setDescription(getString(R.string.channel_description_alert_unhappy));

            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel_daily);
            manager.createNotificationChannel(channel_unhappy);
        }
    }
}
