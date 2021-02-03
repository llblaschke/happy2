package com.example.happy2.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.happy2.DataHandling.Room.UnhappyDay;
import com.example.happy2.DataHandling.UnhappyDayViewModel;
import com.example.happy2.MyHelperMethods.MyDates;
import com.example.happy2.R;

import java.util.List;

import static com.example.happy2.MainActivity.KEY_OPEN_NOT_HAPPY;

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
        unhappyDayViewModel = ViewModelProviders.of(this).get(UnhappyDayViewModel.class);
        unhappyDayViewModel.getAllUnhappyDaysDates().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> dates) {
                getLastXDaysUnhappy(dates);
                getNrOfUnhappyDays(dates);
            }
        });
        String today = new MyDates().todayAsString();
        unhappyDayViewModel.insert(new UnhappyDay(today));

        if (getActivity().getIntent().hasExtra(KEY_OPEN_NOT_HAPPY)) {
            boolean openedFromAlert = getActivity().getIntent().getBooleanExtra(KEY_OPEN_NOT_HAPPY, false);
            if (openedFromAlert && !lastXDaysUnhappy) {
                getActivity().finish();
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_not_happy, container, false);
        txtAlertTooManyUnhappyDays = v.findViewById(R.id.txtAlertTooManyUnhappyDays);
        getVisibilityOfAlert();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    private void getLastXDaysUnhappy(List<String> unhappyDates) {
        List<String> lastXDays = new MyDates().getLastXDays();
        lastXDaysUnhappy = unhappyDates.containsAll(lastXDays);
    };

    private void getNrOfUnhappyDays(List<String> dates) {
        nrOfUnhappyDays = new MyDates().getNrOfUnhappyDaysInARow(dates);
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