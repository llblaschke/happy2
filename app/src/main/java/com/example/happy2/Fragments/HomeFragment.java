package com.example.happy2.Fragments;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.happy2.AddActivity;
import com.example.happy2.MainActivity;
import com.example.happy2.R;


public class HomeFragment extends Fragment {

    public HomeFragment(){
        // empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE ||
                newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            try {
                FragmentTransaction ft = getFragmentManager().beginTransaction();
                if (Build.VERSION.SDK_INT >= 26) {
                    ft.setReorderingAllowed(false);
                }
                ft.detach(this).attach(this).commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    private View.OnClickListener btnClickNotHappy = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            getParentFragmentManager()
                    .beginTransaction()
                    .replace(R.id.mainFragmentContainer,
                            new NotHappyFragment())
                    .commit();
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
            intent.putExtra(AddActivity.KEY_LOAD_IDEA_FRAGMENT, true);
            startActivity(intent);
        }
    };

}