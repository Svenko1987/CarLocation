package com.example.carlocation.inteface;

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
        Log.d(TAG, "timeDifference: " +oldTime );
        long currentTime = SystemClock.elapsedRealtime();
        Log.d(TAG, "timeDifference: "+ currentTime);
        long timeOffset = currentTime - oldTime;
        return timeOffset;
    }
    public void startChronometerWithTime(long savedTime){
        long timeOffset=timeDifference(savedTime);
        chronometer.setBase(SystemClock.elapsedRealtime() - timeOffset);
        Log.d(TAG, "startChronometerWithTime: " + timeOffset);
        chronometer.start();
    }
    public void startChronometer(){
        chronometer.setBase(SystemClock.elapsedRealtime());
        chronometer.start();
    }



}
