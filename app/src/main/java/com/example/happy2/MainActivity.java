package com.example.happy2;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        // from KeyboardListenerActivity
        //attachKeyboardListeners();
    }

    /*// from KeyboardListenerActivity
    @Override
    protected void onShowKeyboard(int keyboardHeight) {
        // do things when keyboard is shown
        Toast.makeText(getApplicationContext(), "Keyboard on", Toast.LENGTH_SHORT).show();
    }

    // from KeyboardListenerActivity
    @Override
    protected void onHideKeyboard() {
        // do things when keyboard is hidden
        Toast.makeText(getApplicationContext(), "Keyboard off", Toast.LENGTH_SHORT).show();
    }*/

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavMethod =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem){
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
                    selectedFragment = new SettingsFragment();
                    break;
                default:
                    selectedFragment = new HomeFragment();
                    break;
            }

//            FragmentManager fm = ((AppCompatActivity)getParent()).getSupportFragmentManager();
//            ((AppCompatActivity)getParent()).getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer,
//                    selectedFragment).commit();
            getSupportFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer,
                    selectedFragment).commit();
            return true;
        };
    };

}