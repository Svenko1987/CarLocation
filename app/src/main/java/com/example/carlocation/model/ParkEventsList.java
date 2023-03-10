package com.example.carlocation.model;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.gson.Gson;

import java.util.ArrayList;

public class ParkEventsList {

    private ArrayList<ParkEvent> myList;

    public ParkEventsList(ArrayList<ParkEvent> myList) {
        this.myList = myList;
    }

    public boolean create(ParkEvent myObject) {
        return myList.add(myObject);
    }

    public ParkEvent read(int index) {
        if (index >= 0 && index < myList.size()) {
            Gson gson = new Gson();
            String json = gson.toJson(myList.get(index));
            ParkEvent parkEvent = gson.fromJson(json, ParkEvent.class);
            Log.d(TAG, "read: RETURNED"+ parkEvent.getDate());
            return parkEvent;
        } else {
            return null;
        }
    }

    public boolean update(int index, ParkEvent myObject) {
        if (index >= 0 && index < myList.size()) {
            myList.set(index, myObject);
            return true;
        } else {
            return false;
        }
    }

    public boolean delete(int index) {
        if (index >= 0 && index < myList.size()) {
            myList.remove(index);
            return true;
        } else {
            return false;
        }
    }

    public ArrayList<ParkEvent> getMyList() {
        return myList;
    }

    public void setMyList(ArrayList<ParkEvent> myList) {
        this.myList = myList;
    }

    @Override
    public String toString() {
        return "ParkEventsList{" +
                "myList=" + myList +
                '}';
    }

}
