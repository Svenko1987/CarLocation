package com.example.carlocation.controls.logic;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import com.example.carlocation.model.ParkEvent;
import com.example.carlocation.model.Vehicle;
import com.google.gson.Gson;

public class SharedPreferencesManagerVehicle {
    SharedPreferences sharedPreferences;
    Vehicle vehicle;

    public SharedPreferencesManagerVehicle(SharedPreferences sharedPreferences, Vehicle vehicle) {
        this.sharedPreferences = sharedPreferences;
        this.vehicle = vehicle;
    }

    public void putToSharedPreferences(){
        SharedPreferences.Editor editor=sharedPreferences.edit();

        Gson gson = new Gson();
        String json=gson.toJson(vehicle);
        editor.putString("Vehicle",json);
        editor.commit();
        Log.d(TAG, "putToSharedPreferences: SAVED"+ vehicle.getName());

    }

    public void setLocation(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public Vehicle getFromSharedPreferences(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString("Vehicle","");
        vehicle =gson.fromJson(json, Vehicle.class);
        Log.d(TAG, "getFromSharedPreferences: "+vehicle.getName());
        return vehicle;

    }
    @SuppressLint("SuspiciousIndentation")
    public boolean IsEmpty(){
        if(!sharedPreferences.contains("Vehicle")){
            return true;
        }else
            return false;

    }
}
