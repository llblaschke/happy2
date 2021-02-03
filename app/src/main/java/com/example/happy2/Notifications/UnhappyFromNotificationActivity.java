package com.example.happy2.Notifications;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.happy2.DataHandling.Room.UnhappyDay;
import com.example.happy2.DataHandling.UnhappyDayViewModel;
import com.example.happy2.MyHelperMethods.MyDates;
import com.example.happy2.R;

import java.util.List;

public class UnhappyFromNotificationActivity extends AppCompatActivity {
    private UnhappyDayViewModel unhappyDayViewModel;
    private boolean lastXDaysUnhappy;
    private int nrOfUnhappyDays;
    private List<String> allUnhappyDays;

    public UnhappyFromNotificationActivity() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        unhappyDayViewModel = ViewModelProviders.of(this).get(UnhappyDayViewModel.class);
        unhappyDayViewModel.getAllUnhappyDaysDates().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> dates) {
                allUnhappyDays = dates;
                getLastXDaysUnhappy(dates);
                getNrOfUnhappyDays(dates);
            }
        });

        String today = new MyDates().todayAsString();
        if (!allUnhappyDays.contains(today)) {
            unhappyDayViewModel.insert(new UnhappyDay(today));
        }

    }



    private void getNrOfUnhappyDays(List<String> dates) {
        nrOfUnhappyDays = new MyDates().getNrOfUnhappyDaysInARow(dates);
    }


    private void getLastXDaysUnhappy(List<String> unhappyDates) {
        List<String> lastXDays = new MyDates().getLastXDays();
        lastXDaysUnhappy = unhappyDates.containsAll(lastXDays);
    };


    private void makeAlertTooUnhappy(){
        if (lastXDaysUnhappy) {
            // make alert
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
