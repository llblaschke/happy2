package com.example.happy2;

import android.annotation.TargetApi;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;



public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelDailyNotification";
    public static final String channelName = "Daily Notification";
    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannel();
        }
    }
    @TargetApi(Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        getManager().createNotificationChannel(channel);
    }
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }
    public NotificationCompat.Builder getChannelNotification() {
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(getResources().getString(R.string.question_are_you_happy))
                .setSmallIcon(R.drawable.ic_app_icon_white)
                .addAction(
                        R.drawable.ic_happy_white,
                        getResources().getString(R.string.happy),
                        PendingIntent.getActivity(
                                getBaseContext(),
                                01,
                                new Intent(getBaseContext(), AddActivity.class),
                                0)
                )

                .addAction(
                        R.drawable.ic_unhappy_white,
                        getResources().getString(R.string.unhappy),
                        PendingIntent.getActivity(
                                getBaseContext(),
                                01,
                                new Intent(getBaseContext(), MainActivity.class),
                                0)
                );
    }
}
