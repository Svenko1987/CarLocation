package com.example.carlocation.model;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/*
Object Location is used for storing common information regarding current and odl positions
 */
public class ParkEvent {
    private double latitude, longitude;
    private String street, houseNumber, city, postal, photoURL;

    private String note;
    private Address address;
    private String date;
    private long time;
    Car car;

    public ParkEvent(double latitude, double longitude, Context context) {
        this.latitude = latitude;
        this.longitude = longitude;
        try {
            this.address = (Address) setAddress(latitude, longitude, context);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.city = address.getLocality();
        this.houseNumber = address.getFeatureName();
        this.street = address.getThoroughfare();
        setDateAndTime();


    }

    public Address setAddress(double latitude, double longitude, Context context) throws IOException {
        Address address = null;
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 2);
        address = addresses.get(0);
        return address;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {

        this.street = street;
    }

    public String getHouseNumber() {
        return houseNumber;
    }

    public void setHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostal() {
        return postal;
    }

    public void setPostal(String postal) {
        this.postal = postal;
    }

    public String getPhotoURL() {
        return photoURL;
    }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getDate() {
        return date;
    }

    public void setDateAndTime() {
        DateAndTime dateAndTime = new DateAndTime();
        this.date = dateAndTime.getDate();
        this.time = dateAndTime.getTime();

    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {

        this.time = time;
    }
}
