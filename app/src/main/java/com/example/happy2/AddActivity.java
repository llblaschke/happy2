package com.example.happy2;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class AddActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_white);

        String intentFragment = getIntent().getExtras().getString("fragmentToLoad");

        switch (intentFragment){
            case "Idea":
                getSupportFragmentManager().beginTransaction().replace(R.id.addFragmentContainer,
                        new AddIdeaFragment(), "AddIdeaFragment").commit();
                break;
            default:
                getSupportFragmentManager().beginTransaction().replace(R.id.addFragmentContainer,
                        new AddHappyThingFragment(), "updatedFragmentAddHappy").commit();
                break;
        }

    }

}