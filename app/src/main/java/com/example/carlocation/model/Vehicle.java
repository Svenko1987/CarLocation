package com.example.carlocation.model;

import android.text.TextUtils;

public class Vehicle {

    private String name;
    private String licencePlate;
    private String manufacturer;
    private String firstRegistration;
    private String note;
    private String color;
    private String image;

    public Vehicle(String name, String licencePlate, String manufacturer,  String firstRegistration, String note, String color,String image) {
        this.name = name;
        this.licencePlate = licencePlate;
        this.manufacturer = manufacturer;
        this.firstRegistration = firstRegistration;
        this.note = note;
        this.color = color;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLicencePlate() {
        return licencePlate;
    }

    public void setLicencePlate(String licencePlate) {
        this.licencePlate = licencePlate;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }



    public String getFirstRegistration() {
        return firstRegistration;
    }

    public void setFirstRegistration(String firstRegistration) {
        this.firstRegistration = firstRegistration;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

}
