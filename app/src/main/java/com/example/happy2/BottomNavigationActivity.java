package com.example.happy2;

import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationActivity extends AppCompatActivity {


    public BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem){
            boolean openFragment = true;
            Fragment selectedFragment;
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.navigation_happy_list:
                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                    startActivity(intent);
                    openFragment = false;
                    selectedFragment = new HappyListFragment();
                    break;
                case R.id.navigation_ideas:
                    selectedFragment = new IdeaListFragment();
                    break;
                case R.id.navigation_calendar:
                    selectedFragment = new CalendarFragment();
                    break;
                case R.id.navigation_settings:
                    selectedFragment = new SettingsFragment();
                    break;
                default:
                    selectedFragment = new HomeFragment();
                    break;
            }
            if(openFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer,
                        selectedFragment).commit();
            }
            return true;
        };
    };

}