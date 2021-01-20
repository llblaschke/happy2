package com.example.happy2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {
    private final String TAG = "AddActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);

        boolean loadIdeaFragment = false;
        try {
            loadIdeaFragment = getIntent().getExtras().getBoolean("loadIdeaFragment");
        }catch (NullPointerException e) {
            Log.d(TAG, "loadIdeaFragment gives NullPointerException");
        }

        if(loadIdeaFragment) {
            getSupportActionBar().setTitle(R.string.title_ideas);
            getSupportFragmentManager().beginTransaction().replace(R.id.addFragmentContainer,
                    new AddIdeaFragment(), "AddIdeaFragment").commit();
        }else{
                getSupportFragmentManager().beginTransaction().replace(R.id.addFragmentContainer,
                        new AddHappyThingFragment(), "updatedFragmentAddHappy").commit();
        }

    }

    public void closeActivity(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        this.finish();
    }

}