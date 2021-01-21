package com.example.happy2;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class NotificationActivity extends AppCompatActivity {

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
            }
        }
    };
}