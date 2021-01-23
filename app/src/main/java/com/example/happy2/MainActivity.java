package com.example.happy2;

import android.os.Bundle;
import android.widget.Toast;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

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
        happyViewModel.getAllHappyThings().observe(this, new Observer<List<HappyThing>>() {
            @Override
            public void onChanged(List<HappyThing> happyThings) {
                // update Recycler View
                Toast.makeText(MainActivity.this, "onChanged happyViewModel", Toast.LENGTH_SHORT).show();
            }
        });
    }
}