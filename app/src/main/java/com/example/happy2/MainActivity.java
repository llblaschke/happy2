package com.example.happy2;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProviders;

import com.example.happy2.DataHandling.HappyViewModel;
import com.example.happy2.DataHandling.IdeaViewModel;
import com.example.happy2.DataHandling.UnhappyDayViewModel;
import com.example.happy2.Fragments.HappyListFragment;
import com.example.happy2.Fragments.IdeaListFragment;
import com.example.happy2.Fragments.NotHappyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends BottomNavigationActivity {
    public static final String KEY_OPEN_HAPPY_LIST = "com.example.happy2.KEY_OPEN_HAPPY_LIST";
    public static final String KEY_OPEN_IDEA_LIST = "com.example.happy2.KEY_OPEN_IDEA_LIST";
    public static final String KEY_OPEN_NOT_HAPPY = "com.example.happy2.KEY_OPEN_NOT_HAPPY";
    public static final String KEY_CLOSE_FRAGMENT_AFTER_CREATE = "com.example.happy2.KEY_CLOSE_FRAGMENT_AFTER_CREATE";

    public BottomNavigationView bottomNavigationView;
    private IdeaViewModel ideaViewModel;
    private HappyViewModel happyViewModel;
    private UnhappyDayViewModel unhappyDayViewModel;

    private boolean openHappyList;
    private boolean openIdeaList;
    private boolean openNotHappy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toolbar.setNavigationOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View view) {
                    if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
                        getSupportFragmentManager().popBackStack();
                    } else {
                        onBackPressed();
                    }
                }
            });
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setDisplayShowHomeEnabled(true);

        bottomNavigationView = findViewById(R.id.nav_view_main);
        bottomNavigationView.setOnNavigationItemSelectedListener(bottomNavMethod);

        ideaViewModel = ViewModelProviders.of(this).get(IdeaViewModel.class);
        happyViewModel = ViewModelProviders.of(this).get(HappyViewModel.class);
        unhappyDayViewModel = ViewModelProviders.of(this).get(UnhappyDayViewModel.class);

        getOpenHappyFragment();
        getOpenIdeaFragment();
        getOpenNotHappyFragment();

        if (openHappyList) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFragmentContainer,
                            new HappyListFragment(),
                            "OpenHappyListFragment")
                    .addToBackStack(null)
                    .commit();
        } else if (openIdeaList) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFragmentContainer,
                            new IdeaListFragment(),
                            "OpenIdeaListFragment")
                    .addToBackStack(null)
                    .commit();
        } else if (openNotHappy) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFragmentContainer,
                            new NotHappyFragment(),
                            "OpenNotHappyFragment")
                    .commit();
        }
    }

    @Override
    public boolean onNavigateUp() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
        } else {
            super.onNavigateUp();
        }
        return false;
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0 ){
            getSupportFragmentManager().popBackStack();
        } else {
            super.onBackPressed();
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

    // get boolean if to load HappyListFragment, default = false
    private void getOpenNotHappyFragment() {
        if (getIntent().hasExtra(KEY_OPEN_NOT_HAPPY)) {
            openNotHappy = getIntent().getBooleanExtra(KEY_OPEN_NOT_HAPPY, false);
        } else {openNotHappy = false;}
    }

}