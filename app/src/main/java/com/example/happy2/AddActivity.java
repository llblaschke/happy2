package com.example.happy2;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.happy2.Fragments.AddHappyThingFragment;
import com.example.happy2.Fragments.AddIdeaFragment;

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

    public void onPause() {
        super.onPause();
        // Close SoftKeyBoard from:
        // https://stackoverflow.com/questions/1109022/how-do-you-close-hide-the-android-soft-keyboard-using-java#1109108
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        // Find the currently focused view, so we can grab the correct window token from it.
        View view = getCurrentFocus();
        // If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(this);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public void closeActivity(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        this.finish();
    }

}