package com.example.happy2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_happy);
        this.getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);
    }

}