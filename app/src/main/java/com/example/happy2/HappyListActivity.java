package com.example.happy2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HappyListActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_happy_list);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setSelectedItemId(R.id.navigation_happy_list);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod = new
            BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem menuItem){
                    Intent intent = null;
                    switch (menuItem.getItemId()) {
                        case R.id.navigation_happy_list:
                            intent = new Intent(getApplicationContext(), HappyListActivity.class);
                            break;
                        case R.id.navigation_home:
                            intent = new Intent(getApplicationContext(), MainActivity.class);
                            break;
                        case R.id.navigation_ideas:
                            intent = new Intent(getApplicationContext(), IdeaListActivity.class);
                            break;
                    }

                    startActivity(intent);
                    return true;
                };
            };
}