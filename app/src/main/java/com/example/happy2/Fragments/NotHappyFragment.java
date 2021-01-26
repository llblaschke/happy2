package com.example.happy2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.happy2.DataHandling.UnhappyDayViewModel;
import com.example.happy2.MyHelperMethods.DayCount;
import com.example.happy2.R;

import java.util.List;
import java.util.Objects;

public class NotHappyFragment extends Fragment {

    private UnhappyDayViewModel unhappyDayViewModel;
    private int nrOfUnhappyDays;
    private boolean lastXDaysUnhappy;
    private TextView txtAlertTooManyUnhappyDays;

    public NotHappyFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unhappyDayViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(UnhappyDayViewModel.class);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_not_happy, container, false);
        txtAlertTooManyUnhappyDays = v.findViewById(R.id.txtAlertTooManyUnhappyDays);
        unhappyDayViewModel.getAllUnhappyDaysDates().observe(getActivity(), new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> dates) {
                getLastXDaysUnhappy(dates);
                getNrOfUnhappyDays(dates);
            }
        });
        getVisibilityOfAlert();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        getVisibilityOfAlert();
    }



    private void getLastXDaysUnhappy(List<String> unhappyDates) {
        List<String> lastXDays = new DayCount().getLastXDays();
        lastXDaysUnhappy = unhappyDates.containsAll(lastXDays);
    };

    private void getNrOfUnhappyDays(List<String> dates) {
        nrOfUnhappyDays = new DayCount().getNrOfUnhappyDaysInARow(dates);
    }

    private void getVisibilityOfAlert(){
        if (lastXDaysUnhappy) {
            txtAlertTooManyUnhappyDays.setVisibility(View.VISIBLE);
            txtAlertTooManyUnhappyDays.setText(getAlertText());
        } else {
            txtAlertTooManyUnhappyDays.setVisibility(View.INVISIBLE);
        }
    }

    private String getAlertText() {
        String part1;
        String part2;
        if (nrOfUnhappyDays > 1) {
            part1 = getString(R.string.alert_too_many_unhappy_days_part1);
            part2 = getString(R.string.alert_too_many_unhappy_days_part2);
        } else {
            part1 = getString(R.string.alert_too_many_unhappy_days_part1_singular);
            part2 = getString(R.string.alert_too_many_unhappy_days_part2_singular);
        }
        String alertText = part1 + " " + nrOfUnhappyDays + " " + part2;
        return alertText;
    }

}