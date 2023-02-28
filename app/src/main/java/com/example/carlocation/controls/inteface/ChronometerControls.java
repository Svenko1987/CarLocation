package com.example.carlocation.controls.inteface;

import static android.content.ContentValues.TAG;

import android.os.SystemClock;

import android.util.Log;
import android.widget.Chronometer;

import java.util.Date;


public class ChronometerControls {


    private Date date;
    private Chronometer chronometer;

    public ChronometerControls(Chronometer chronometer) {
        this.chronometer = chronometer;
    }

    public long timeDifference( long oldTime){
        long currentTime = SystemClock.elapsedRealtime();
        long timeOffset = currentTime - oldTime;
        return timeOffset;
    }
    public void startChronometerWithTime(long savedTime){
        long timeOffset=timeDifference(savedTime);
        chronometer.setBase(500);
        Log.d(TAG, "startChronometerWithTime: ");
        chronometer.start();
    }
    public void startChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }



}
