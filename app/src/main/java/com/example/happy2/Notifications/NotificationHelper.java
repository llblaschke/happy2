package com.example.happy2.Notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.happy2.AddActivity;
import com.example.happy2.MainActivity;
import com.example.happy2.R;

import static com.example.happy2.BaseApplication.CHANNEL_ID_DAILY_NOTIFICATION;
import static com.example.happy2.BaseApplication.CHANNEL_ID_UNHAPPY_ALERT;
import static com.example.happy2.MainActivity.KEY_OPEN_HAPPY_LIST;
import static com.example.happy2.MainActivity.KEY_OPEN_IDEA_LIST;


public class NotificationHelper extends ContextWrapper {
    private NotificationManager mManager;
    private Context context;

    public NotificationHelper(Context base) {
        super(base);
        context = base;
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public void sendOnChannelDaily() {
        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID_DAILY_NOTIFICATION)
                .setContentTitle(getResources().getString(R.string.question_are_you_happy))
                .setSmallIcon(R.drawable.ic_app_icon_white)
                .setCategory(Notification.CATEGORY_REMINDER)
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
                )
                .build();
        NotificationManagerCompat.from(context).notify(1, notification);
    }


    public void sendOnChannelUnhappy() {
        Intent intentHappyList = new Intent(getBaseContext(), MainActivity.class);
        intentHappyList.putExtra(KEY_OPEN_HAPPY_LIST, true);
        Intent intentIdeaList = new Intent(getBaseContext(), MainActivity.class);
        intentIdeaList.putExtra(KEY_OPEN_IDEA_LIST, true);

        Notification notification = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID_UNHAPPY_ALERT)
                .setContentTitle(getResources().getString(R.string.alert_too_many_unhappy_days_part_just_too_many))
                .setSmallIcon(R.drawable.ic_app_icon_white)
                .addAction(
                        R.drawable.ic_happy_white,
                        getResources().getString(R.string.button_show_happy_list),
                        PendingIntent.getActivity(
                                getBaseContext(),
                                01,
                                intentHappyList,
                                0)
                )
                .addAction(
                        R.drawable.ic_idea_white,
                        getResources().getString(R.string.button_show_ideas),
                        PendingIntent.getActivity(
                                getBaseContext(),
                                01,
                                intentIdeaList,
                                0)
                )
                .build();
        NotificationManagerCompat.from(context).notify(2, notification);
    }
}
