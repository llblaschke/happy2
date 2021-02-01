package com.example.happy2;

import android.os.Bundle;

import androidx.lifecycle.ViewModelProviders;

import com.example.happy2.DataHandling.HappyViewModel;
import com.example.happy2.DataHandling.IdeaViewModel;
import com.example.happy2.DataHandling.UnhappyDayViewModel;
import com.example.happy2.Fragments.HappyListFragment;
import com.example.happy2.Fragments.IdeaListFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BottomNavigationActivity {
    public static final String KEY_OPEN_HAPPY_LIST = "com.example.happy2.KEY_OPEN_HAPPY_LIST";
    public static final String KEY_OPEN_IDEA_LIST = "com.example.happy2.KEY_OPEN_IDEA_LIST";

    public BottomNavigationView bottomNavigationView;
    private IdeaViewModel ideaViewModel;
    private HappyViewModel happyViewModel;
    private UnhappyDayViewModel unhappyDayViewModel;

    private boolean openHappyList;
    private boolean openIdeaList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.nav_view_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        ideaViewModel = ViewModelProviders.of(this).get(IdeaViewModel.class);
        happyViewModel = ViewModelProviders.of(this).get(HappyViewModel.class);
        unhappyDayViewModel = ViewModelProviders.of(this).get(UnhappyDayViewModel.class);

        getOpenHappyFragment();
        getOpenIdeaFragment();

        if (openHappyList) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFragmentContainer,
                            new HappyListFragment(), "OpenHappyListFragment")
                    .commit();
        } else if (openIdeaList) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFragmentContainer,
                            new IdeaListFragment(), "OpenIdeaListFragment")
                    .commit();
        }
    }


    // get boolean if to load HappyListFragment, default = false
    private void getOpenHappyFragment() {
        if (getIntent().hasExtra(KEY_OPEN_HAPPY_LIST)) {
            openHappyList = getIntent().getBooleanExtra(KEY_OPEN_HAPPY_LIST, false);
        } else {openHappyList = false;}
    }


    // get boolean if to load IdeaListFragment, default = false
    private void getOpenIdeaFragment() {
        if (getIntent().hasExtra(KEY_OPEN_IDEA_LIST)) {
            openIdeaList = getIntent().getBooleanExtra(KEY_OPEN_IDEA_LIST, false);
        } else {openIdeaList = false;}
    }

}