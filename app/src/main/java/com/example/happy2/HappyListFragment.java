package com.example.happy2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class HappyListFragment<list> extends Fragment {

    public static final String TAG = "HappyListFragment";

    private myList happyList = new myList("Happy");
    Object[][] list = happyList.getList();
    int length = happyList.length();

    public HappyListFragment() {
        // Required empty public constructor
    }

    public static HappyListFragment newInstance() {
        return new HappyListFragment();
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = getLayoutInflater().inflate(R.layout.fragment_happy_list, container, false);
        FloatingActionButton btnAdd = v.findViewById(R.id.btnAddInList);
        btnAdd.setOnClickListener(btnAddHappyThing);
        return v;
    }

    private View.OnClickListener btnAddHappyThing = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddHappyActivity.class);
            startActivity(intent);
        }
    };

}