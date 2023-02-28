package com.example.carlocation.controls.inteface;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.carlocation.model.ParkEvent;
import com.google.gson.Gson;

/*
Saving and getting data from sharedPreferences
 */
public class SharedPreferencesManager {

    SharedPreferences sharedPreferences;
    ParkEvent parkEvent;

    public SharedPreferencesManager(SharedPreferences sharedPreferences, ParkEvent parkEvent) {

        this.sharedPreferences = sharedPreferences;
        this.parkEvent = parkEvent;
    }

    public void putToSharedPreferences(){
        SharedPreferences.Editor editor=sharedPreferences.edit();

        Gson gson = new Gson();
        String json=gson.toJson(parkEvent);
        editor.putString("CarLocation",json);
        editor.commit();
        Log.d(TAG, "putToSharedPreferences: SAVED"+ parkEvent.getLatitude()+" "+ parkEvent.getLongitude());

    }

    public void setLocation(ParkEvent parkEvent) {
        this.parkEvent = parkEvent;
    }

    public ParkEvent getFromSharedPreferences(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("CarLocation","");
        parkEvent =gson.fromJson(json, ParkEvent.class);
        Log.d(TAG, "getFromSharedPreferences: "+ parkEvent.getLatitude()+" "+ parkEvent.getLongitude()+" from :"+ parkEvent.getDate()+" Time "+parkEvent.getTime());
        return parkEvent;

    }
    @SuppressLint("SuspiciousIndentation")
    public boolean IsEmpty(){
        if(!sharedPreferences.contains("CarLocation")){
            return true;
        }else
        return false;

    }
}
