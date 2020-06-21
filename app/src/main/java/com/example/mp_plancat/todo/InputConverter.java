package com.example.mp_plancat.todo;

import java.sql.Date;
import java.util.Calendar;

public class InputConverter {
    public static final String getCategory(int category) {
        switch (category) {
            case 1:
                return "W";
            case 2:
                return "M";
            case 3:
                return "Y";
            default:
                return "D";
        }
    }

    public static final Calendar getStartDate(int day, int week, int month, int year, int category) {
        Calendar cal = Calendar.getInstance();

        if (category == 0) {
            cal.set(year, month - 1, day);
        }
        else if (category == 1) {
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1);

            cal.set(Calendar.WEEK_OF_MONTH, week);

            cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
            int startDay = cal.get(Calendar.DAY_OF_MONTH);

                if (week == 1 && startDay >= 7) {
                    startDay = 1;
                }

            cal.set(year, month - 1, startDay);
        }
        else if (category == 2) { //monthly
            cal.set(year, month - 1, 1);
        }
        else { //yearly
            cal.set(year, 0, 1);
        }

        return cal;
    }

    public static final Calendar getEndDate(int day, int week, int month, int year, int category){
        Calendar cal = Calendar.getInstance();

        if (category == 0) { //daily
            cal.set(year, month - 1, day);
        }
        else if(category == 1){ //weekly
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1);

            cal.set(Calendar.WEEK_OF_MONTH, week);

            cal.set(Calendar.DAY_OF_WEEK, Calendar.SATURDAY);
            int endDay = cal.get(Calendar.DAY_OF_MONTH);

                if (week == cal.getMaximum(Calendar.WEEK_OF_MONTH) - 1 && endDay <= 7) {
                    endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
                }
            cal.set(year, month - 1, endDay);
        }
        else if(category == 2){ //monthly
            cal.set(Calendar.YEAR, year);
            cal.set(Calendar.MONTH, month - 1);
            int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            cal.set(year, month - 1, endDay);
        }
        else{ //yearly
            cal.set(year, 11, 1);

            int endDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);

            cal.set(year, 11, endDay);
        }
        return cal;
    }

    public static final double getCalculatedPoint(Calendar endDate, int priority){
        //Todo : 현재 포인트 계산 임시 method임; 다시 만들기!!!!!!!!!
        switch (priority){
            case 1: //중요도 상
                return 100.0;
            case 2: //중요도 중
                return 60.0;
            case 3: //중요도 하
                return 30.0;
            default:
                return 0.0;
        }
    }
}
