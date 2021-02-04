package com.example.happy2.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.happy2.DataHandling.Room.UnhappyDay;
import com.example.happy2.DataHandling.UnhappyDayViewModel;
import com.example.happy2.MainActivity;
import com.example.happy2.MyHelperMethods.MyDates;
import com.example.happy2.R;

import java.util.List;

import static com.example.happy2.MainActivity.KEY_CLOSE_FRAGMENT_AFTER_CREATE;

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
                closeIfFromNotification();
                getVisibilityOfAlert();
            }
        });
        String today = new MyDates().todayAsString();
        unhappyDayViewModel.insert(new UnhappyDay(today));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_not_happy, container, false);
        txtAlertTooManyUnhappyDays = v.findViewById(R.id.txtAlertTooManyUnhappyDays);
        getVisibilityOfAlert();
        Toast.makeText(getContext(), getString(R.string.toast_today_saved_as_unhappy), Toast.LENGTH_SHORT).show();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        int selectedItemId = ((MainActivity) getActivity()).bottomNavigationView.getSelectedItemId();
        if (selectedItemId != R.id.navigation_home) {
            ((MainActivity) getActivity()).bottomNavigationView.setSelectedItemId(R.id.navigation_home);
        }
    };


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

    private void closeIfFromNotification() {
        if (getActivity().getIntent().hasExtra(KEY_CLOSE_FRAGMENT_AFTER_CREATE)) {
            boolean openedFromAlert = getActivity().getIntent().getBooleanExtra(KEY_CLOSE_FRAGMENT_AFTER_CREATE, false);
            if (openedFromAlert && !lastXDaysUnhappy) {
                FragmentManager fm = getFragmentManager();
                if (fm.getBackStackEntryCount() > 0) {
                    Log.i("MainActivity", "popping backstack");
                    fm.popBackStack();
                } else {
                    Log.i("MainActivity", "nothing on backstack, calling super");
                    getActivity().finish();
                }
            }
        }
    }

}