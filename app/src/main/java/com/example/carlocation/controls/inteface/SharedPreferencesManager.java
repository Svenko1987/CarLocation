package com.example.carlocation.controls.inteface;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.carlocation.model.Location;
import com.google.gson.Gson;

/*
Saving and getting data from sharedPreferences
 */
public class SharedPreferencesManager {

    SharedPreferences sharedPreferences;
    Location location;

    public SharedPreferencesManager(SharedPreferences sharedPreferences, Location location) {

        this.sharedPreferences = sharedPreferences;
        this.location = location;
    }

    public void putToSharedPreferences(){
        SharedPreferences.Editor editor=sharedPreferences.edit();

        Gson gson = new Gson();
        String json=gson.toJson(location);
        editor.putString("CarLocation",json);
        editor.commit();
        Log.d(TAG, "putToSharedPreferences: SAVED"+location.getLatitude()+" "+ location.getLongitude());

    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public Location getFromSharedPreferences(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("CarLocation","");
        location=gson.fromJson(json,Location.class);
        Log.d(TAG, "getFromSharedPreferences: "+location.getLatitude()+" "+ location.getLongitude()+" from :"+location.getDate());
        return location;

    }
    @SuppressLint("SuspiciousIndentation")
    public boolean IsEmpty(){
        if(!sharedPreferences.contains("CarLocation")){
            return true;
        }else
        return false;

    }
}
