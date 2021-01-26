package com.example.happy2.MyHelperMethods;

import java.util.Calendar;

public class StringDate {

    public String dateToString(int day, int month, int year) {
        return new StringBuilder().
                append(day).append(".")
                .append(month+1).append(".")
                .append(year)
                .toString();
    }

    public String todayAsString() {
        Calendar calendar = Calendar.getInstance();
        return dateToString(calendar.get(Calendar.DAY_OF_MONTH),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.YEAR));
    }


}
