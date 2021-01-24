package com.example.happy2;

import android.content.Intent;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.happy2.Fragments.CalendarFragment;
import com.example.happy2.Fragments.HappyListFragment;
import com.example.happy2.Fragments.HomeFragment;
import com.example.happy2.Fragments.IdeaListFragment;
import com.example.happy2.Fragments.SettingsFragment;
import com.example.happy2.Notifications.NotificationActivity;
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
                    selectedFragment = new HappyListFragment();
                    break;
                case R.id.navigation_ideas:
                    selectedFragment = new IdeaListFragment();
                    break;
                case R.id.navigation_calendar:
                    selectedFragment = new CalendarFragment();
                    break;
                case R.id.navigation_settings:
                    openFragment = false;
                    selectedFragment = new SettingsFragment();
                    Intent intent = new Intent(getApplicationContext(), NotificationActivity.class);
                    startActivity(intent);
                    break;
                default:
                    return false;
            }
            if(openFragment) {
                getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer,
                        selectedFragment).commit();
            }
            return true;
        }
    };

}