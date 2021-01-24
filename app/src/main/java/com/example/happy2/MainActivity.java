package com.example.happy2;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.example.happy2.DataHandling.HappyViewModel;
import com.example.happy2.DataHandling.IdeaViewModel;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BottomNavigationActivity {

    public BottomNavigationView bottomNavigationView;
    private IdeaViewModel ideaViewModel;
    private HappyViewModel happyViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        ideaViewModel = ViewModelProviders.of(this).get(IdeaViewModel.class);
        happyViewModel = ViewModelProviders.of(this).get(HappyViewModel.class);
    }
}