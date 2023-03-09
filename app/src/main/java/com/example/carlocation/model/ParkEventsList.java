package com.example.carlocation.model;

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
            return myList.get(index);
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
}
