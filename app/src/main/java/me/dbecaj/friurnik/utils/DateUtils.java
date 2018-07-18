package me.dbecaj.friurnik.utils;

import java.util.Calendar;

import me.dbecaj.friurnik.FRIUrnikApp;
import me.dbecaj.friurnik.R;

public class DateUtils {

    public static String getDayDateTitle() {
        String dayString = "";
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        switch (dayOfWeek) {
            case Calendar.MONDAY:
                dayString = FRIUrnikApp.getInstance().getString(R.string.monday);
                break;
            case Calendar.TUESDAY:
                dayString = FRIUrnikApp.getInstance().getString(R.string.tuesday);
                break;
            case Calendar.WEDNESDAY:
                dayString = FRIUrnikApp.getInstance().getString(R.string.wednesday);
                break;
            case Calendar.THURSDAY:
                dayString = FRIUrnikApp.getInstance().getString(R.string.thursday);
                break;
            case Calendar.FRIDAY:
                dayString = FRIUrnikApp.getInstance().getString(R.string.friday);
                break;
            case Calendar.SATURDAY:
                dayString = FRIUrnikApp.getInstance().getString(R.string.saturday);
                break;
            case Calendar.SUNDAY:
                dayString = FRIUrnikApp.getInstance().getString(R.string.sunday);
                break;
        }

        // Starts with 0
        int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);

        return String.format("%s (%d.%d)", dayString, day, month);
    }
}
