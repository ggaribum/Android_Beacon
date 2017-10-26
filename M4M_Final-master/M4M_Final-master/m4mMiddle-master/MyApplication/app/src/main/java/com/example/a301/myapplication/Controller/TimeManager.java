package com.example.a301.myapplication.Controller;

import java.util.Calendar;

/**
 * Created by kwakgee on 2017. 10. 24..
 */

public class TimeManager {

    public String getCurrentDate(){

        Calendar cal = Calendar.getInstance();

        String currentDate = String.valueOf(cal.get(Calendar.YEAR)) + "년 "
                + String.valueOf(cal.get(Calendar.MONTH) + 1) + "월 "
                + String.valueOf(cal.get(Calendar.DATE)) + "일";

        return currentDate;
    }

}
