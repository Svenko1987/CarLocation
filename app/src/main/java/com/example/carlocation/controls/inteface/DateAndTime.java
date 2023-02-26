package com.example.carlocation.controls.inteface;

import android.os.SystemClock;

import android.widget.Chronometer;

import java.util.Date;


public class DateAndTime {


    private Date date;
    private Chronometer chronometer;

    public DateAndTime(Chronometer chronometer) {
        this.chronometer = chronometer;
    }

    public long timeDifference(Date currentDate, Date oldDate){
        return currentDate.getTime() - oldDate.getTime();
    }
    public void startChronometerWithTime(Date currentDate, Date oldDate ){
        chronometer.setBase(SystemClock.elapsedRealtime()- timeDifference(currentDate,oldDate));
        chronometer.start();
    }
    public void startChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }



}
