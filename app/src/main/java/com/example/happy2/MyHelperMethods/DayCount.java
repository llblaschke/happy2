package com.example.happy2.MyHelperMethods;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DayCount {
    private int nrOfUnhappyDaysOK;
    private List<String> lastXDays;
    private Calendar calendar = Calendar.getInstance();
    int today_as_int;

    public DayCount() {
        nrOfUnhappyDaysOK = 1;
        today_as_int = calendar.get(Calendar.DATE);
    }

    public List<String> getLastXDays() {
        lastXDays = new ArrayList<>();
        for(int i = 0; i< nrOfUnhappyDaysOK; i++) {
            lastXDays.add(dateToString(today_as_int - i));
        }
        return lastXDays;
    }

    public int getNrOfUnhappyDaysInARow(List<String> dates) {
        boolean inARow = true;
        int nrOfUnhappyDays = 0;
        while(inARow) {
            if(! dates.contains(dateToString(today_as_int - nrOfUnhappyDays))) return nrOfUnhappyDays;
            nrOfUnhappyDays += 1;
        }
        return nrOfUnhappyDays;
    }

    private String dateToString(int date) {
        calendar.set(Calendar.DATE, date);
        return new StringDate().dateToString(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR));

    }


}
