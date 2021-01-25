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

    public static final String KEY_LOAD_IDEA_FRAGMENT = "loadIdeaFragment";
    public static final String KEY_EDIT_IDEA_HAPPY = "editIdeaHappy";

    public static final String KEY_WHAT = "what";
    public static final String KEY_WITH = "with";
    public static final String KEY_WHERE = "where";
    public static final String KEY_ADINFO = "adInfo";
    public static final String KEY_WHEN = "when";
    public static final String KEY_ID = "id";


    private boolean loadIdeaFragment;
    private boolean editIdeaHappy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);

        loadIdeaFragment = false;
        try {
            loadIdeaFragment = getIntent().getExtras().getBoolean(KEY_LOAD_IDEA_FRAGMENT);
        }catch (NullPointerException e) {
            Log.d(TAG, KEY_LOAD_IDEA_FRAGMENT+" gives NullPointerException");
        }

        editIdeaHappy = false;
        try {
            editIdeaHappy = getIntent().getExtras().getBoolean(KEY_EDIT_IDEA_HAPPY);
        }catch (NullPointerException e) {
            Log.d(TAG, KEY_EDIT_IDEA_HAPPY+" gives NullPointerException");
        }

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
        getSupportActionBar().setTitle(title);

        if(loadIdeaFragment) {
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