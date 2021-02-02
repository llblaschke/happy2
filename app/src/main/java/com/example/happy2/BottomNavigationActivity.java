package com.example.happy2;

import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy2.Fragments.CalendarFragment;
import com.example.happy2.Fragments.HappyListFragment;
import com.example.happy2.Fragments.HomeFragment;
import com.example.happy2.Fragments.IdeaListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BottomNavigationActivity extends AppCompatActivity {


    public BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem){
            switch (menuItem.getItemId()) {
                case R.id.navigation_home:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.mainFragmentContainer, new HomeFragment())
                            .commit();
                    break;
                case R.id.navigation_happy_list:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.mainFragmentContainer, new HappyListFragment())
                            .commit();
                    break;
                case R.id.navigation_ideas:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.mainFragmentContainer, new IdeaListFragment())
                            .commit();
                    break;
                case R.id.navigation_calendar:
                    getSupportFragmentManager()
                            .beginTransaction()
                            .replace(R.id.mainFragmentContainer, new CalendarFragment())
                            .commit();
                    break;
                case R.id.navigation_settings:
                    startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
                    break;
                default:
                    return false;
            }
            return true;
        }
    };

}