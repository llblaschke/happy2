package com.example.happy2;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;


import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;


public class HomeFragment extends Fragment {

    public HomeFragment(){
        // empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        ((Button)v.findViewById(R.id.btnHappy)).setOnClickListener(btnClickHappyNotHappy);
        ((Button)v.findViewById(R.id.btnNotHappy)).setOnClickListener(btnClickHappyNotHappy);

        return v;
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs,
                          @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
    }




    private View.OnClickListener btnClickHappyNotHappy = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Fragment fragment;
            switch (v.getId()){
                case R.id.btnHappy:
                    fragment = new AddHappyThingFragment();
                    break;
                case R.id.btnNotHappy:
                    fragment = new NotHappyFragment();
                    break;
                default:
                    fragment = new HomeFragment();
                    break;
            }
            FragmentManager fm = getParentFragmentManager();
            fm.beginTransaction().replace(R.id.mainFragmentContainer, fragment).commit();
        }
    };
}