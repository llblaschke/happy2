package com.example.happy2.Fragments;

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
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.happy2.AddActivity;
import com.example.happy2.DataHandling.Room.UnhappyDay;
import com.example.happy2.DataHandling.UnhappyDayViewModel;
import com.example.happy2.MainActivity;
import com.example.happy2.MyHelperMethods.DayCount;
import com.example.happy2.MyHelperMethods.StringDate;
import com.example.happy2.R;

import java.util.List;
import java.util.Objects;


public class HomeFragment extends Fragment {

    private UnhappyDayViewModel unhappyDayViewModel;
    private List<String> allUnhappyDays;
    public boolean lastXDaysUnhappy;

    public HomeFragment(){
        // empty constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unhappyDayViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(UnhappyDayViewModel.class);
        lastXDaysUnhappy = false;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        v.findViewById(R.id.btnHappy).setOnClickListener(btnClickHappy);
        v.findViewById(R.id.btnNotHappy).setOnClickListener(btnClickNotHappy);
        v.findViewById(R.id.btnAddIdea).setOnClickListener(btnAddIdea);


        unhappyDayViewModel.getAllUnhappyDaysDates().observe(Objects.requireNonNull(getActivity()), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> dates) {
                allUnhappyDays = dates;
            }
        });

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
            // add today to unhappy days if not already there
            String today = new StringDate().todayAsString();
            if (!allUnhappyDays.contains(today)) {
                unhappyDayViewModel.insert(new UnhappyDay(today));
            }
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
            intent.putExtra(AddActivity.KEY_LOAD_IDEA_FRAGMENT, true);
            startActivity(intent);
        }
    };

    private boolean getLastXDaysUnhappy(List<String> unhappyDates) {
        List<String> lastXDays = new DayCount().getLastXDays();
        return unhappyDates.containsAll(lastXDays);
    };
}