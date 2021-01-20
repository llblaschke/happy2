package com.example.happy2;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class HomeFragment extends Fragment {

    public HomeFragment(){
        // empty constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        v.findViewById(R.id.btnHappy).setOnClickListener(btnClickHappy);
        v.findViewById(R.id.btnNotHappy).setOnClickListener(btnClickNotHappy);
        v.findViewById(R.id.btnAddIdea).setOnClickListener(btnAddIdea);

        return v;
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs,
                          @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
    }


    @Override
    public void onResume() {
        super.onResume();
        int selectedItemId = ((MainActivity) getActivity()).bottomNavigationView.getSelectedItemId();
        if (selectedItemId != R.id.navigation_home) {
            ((MainActivity) getActivity()).bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
    }





    private View.OnClickListener btnClickNotHappy = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getParentFragmentManager().beginTransaction().replace(R.id.mainFragmentContainer,
                    new NotHappyFragment()).commit();
        }
    };


    private View.OnClickListener btnClickHappy = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddActivity.class);
            startActivity(intent);
        }
    };


    private View.OnClickListener btnAddIdea = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddActivity.class);
            intent.putExtra("loadIdeaFragment", true);
            startActivity(intent);
        }
    };
}