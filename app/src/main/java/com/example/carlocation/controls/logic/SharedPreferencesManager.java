package com.example.carlocation.controls.logic;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;

public class SharedPreferencesManager<T> {

    private SharedPreferences sharedPreferences;
    private T object;
    private Class<T> clazz;

    public SharedPreferencesManager(SharedPreferences sharedPreferences, T object, Class<T> clazz) {
        this.sharedPreferences = sharedPreferences;
        this.object = object;
        this.clazz = clazz;
    }

    public void putToSharedPreferences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(object);
        editor.putString(clazz.getSimpleName(), json);
        editor.apply();
        Log.d(clazz.getSimpleName(), "putToSharedPreferences: SAVED");
    }

    public void setObject(T object) {
        this.object = object;
        Log.d(TAG, "setObject: "+object.toString());
    }

    public T getFromSharedPreferences(){
        Gson gson = new Gson();
        String json = sharedPreferences.getString(clazz.getSimpleName(), "");
        T object = gson.fromJson(json, clazz);
        if (object != null) {
            Log.d(TAG, "getFromSharedPreferences: "+ object.getClass()+ " " + object.toString());
        }
        return object;
    }

    @SuppressLint("ApplySharedPref")
    public boolean isEmpty(){
        return !sharedPreferences.contains(clazz.getSimpleName());
    }
}
