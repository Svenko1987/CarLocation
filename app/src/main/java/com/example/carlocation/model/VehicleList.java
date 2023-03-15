package com.example.carlocation.model;

import static android.content.ContentValues.TAG;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;

public class VehicleList {
    private ArrayList<Vehicle> myList;

    public VehicleList(ArrayList<Vehicle> myList) {
        this.myList = myList;
    }
    public boolean create(Vehicle myObject) {
        return myList.add(myObject);
    }

    public Vehicle read(int index) {
        if (index >= 0 && index < myList.size()) {
            Gson gson = new Gson();
            String json = gson.toJson(myList.get(index));
            Vehicle vehicle = gson.fromJson(json, Vehicle.class);
            Log.d(TAG, "read: RETURNED"+ vehicle.getName());
            return vehicle;
        } else {
            return null;
        }
    }
    public Vehicle getSelected() {
        for (Object obj : myList) {
            Gson gson = new Gson();
            String json = gson.toJson(obj);
            Vehicle vehicle = gson.fromJson(json, Vehicle.class);
            if (vehicle.isSelected()) {
                Log.d(TAG, "read: RETURNED " + vehicle.getName());
                return vehicle;
            }
        }
        Log.d(TAG, "read: NO SELECTED VEHICLE FOUND");
        return null;
    }
    public boolean update(int index, Vehicle myObject) {
        if (index >= 0 && index < myList.size()) {
            myList.set(index, myObject);
            return true;
        } else {
            return false;
        }
    }
    public boolean updateSelected(Vehicle vehicleToUpdate) {
        for (Object obj : myList) {
            if (obj instanceof LinkedTreeMap) {
                Gson gson = new Gson();
                String json = gson.toJson(obj);
                Vehicle vehicle = gson.fromJson(json, Vehicle.class);
                if (vehicle.equals(vehicleToUpdate)) {
                    vehicle.setSelected(true);
                    return true;
                }
            }
        }
        return false;
    }
    public boolean delete(int index) {
        if (index >= 0 && index < myList.size()) {
            myList.remove(index);
            return true;
        } else {
            return false;
        }
    }
    public ArrayList<Vehicle> getMyList() {
        return myList;
    }

    public void setMyList(ArrayList<Vehicle> myList) {
        this.myList = myList;
    }

    @Override
    public String toString() {
        return "ParkEventsList{" +
                "myList=" + myList +
                '}';
    }
}
