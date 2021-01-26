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
    private static final String TAG = "AddActivity";

    public static final String KEY_LOAD_IDEA_FRAGMENT = "com.example.happy2.LOAD_IDEA_FRAGMENT";
    public static final String KEY_EDIT_IDEA_HAPPY = "com.example.happy2.EDIT_IDEA_HAPPY";

    public static final String KEY_WHAT = "com.example.happy2.WHAT";
    public static final String KEY_WITH = "com.example.happy2.WITH";
    public static final String KEY_WHERE = "com.example.happy2.WHERE";
    public static final String KEY_ADINFO = "com.example.happy2.ADINFO";
    public static final String KEY_WHEN = "com.example.happy2.WHEN";
    public static final String KEY_ID = "com.example.happy2.ID";


    private boolean loadIdeaFragment;
    private boolean editIdeaHappy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);

        loadIdeaFragment = getLoadIdeaFragment();
        editIdeaHappy = getEditIdeaHappy();
        getSupportActionBar().setTitle(createTitle());


        if (loadIdeaFragment) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.addFragmentContainer,
                    new AddIdeaFragment(), "AddIdeaFragment")
                    .commit();
        } else {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.addFragmentContainer,
                    new AddHappyThingFragment(), "updatedFragmentAddHappy")
                    .commit();
        }

    }

    // get boolean if to load AddIdeaFragment, default = false
    private boolean getLoadIdeaFragment() {
        try {
            return getIntent().getExtras().getBoolean(KEY_LOAD_IDEA_FRAGMENT);
        }catch (NullPointerException e) {
            Log.d(TAG, KEY_LOAD_IDEA_FRAGMENT+" gives NullPointerException");
        }
        return false;
    }

    // get boolean if to edit an existing idea/happy thing or add a new one, default = false
    private boolean getEditIdeaHappy() {
        try {
            return getIntent().getExtras().getBoolean(KEY_EDIT_IDEA_HAPPY);
        }catch (NullPointerException e) {
            Log.d(TAG, KEY_EDIT_IDEA_HAPPY+" gives NullPointerException");
        }
        return false;
    }

    // create title
    public String createTitle() {
        String title = "";
        if (editIdeaHappy) {
            title += getString(R.string.edit) + " ";
        } else {
            title += getString(R.string.add) + " ";
        }
        if (loadIdeaFragment) {
            title += getString(R.string.idea);
        } else {
            title += getString(R.string.happy_thing);
        }
        return title;
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