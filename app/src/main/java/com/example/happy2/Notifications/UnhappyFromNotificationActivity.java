package com.example.happy2.Notifications;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy2.MainActivity;

import static com.example.happy2.MainActivity.KEY_CLOSE_FRAGMENT_AFTER_CREATE;
import static com.example.happy2.MainActivity.KEY_OPEN_NOT_HAPPY;

public class UnhappyFromNotificationActivity extends AppCompatActivity {

    public UnhappyFromNotificationActivity() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra(KEY_OPEN_NOT_HAPPY, true);
        intent.putExtra(KEY_CLOSE_FRAGMENT_AFTER_CREATE, true);

        startActivity(intent);
        finish();
    }
}
