package com.example.carlocation.controls;

import android.content.SharedPreferences;

import com.example.carlocation.model.Location;
import com.google.gson.Gson;

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

    }
    public Location getFromSharedPreferences(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("CarLocation","");
        location=gson.fromJson(json,Location.class);
        return location;
    }
}
