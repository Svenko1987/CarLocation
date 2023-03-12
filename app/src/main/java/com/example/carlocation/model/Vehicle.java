package com.example.carlocation.model;

public class Vehicle {

    private String name;
    private String licencePlate;
    private String manufacturer;
    private  String type;
    private String firstRegistration;
    private String note;
    private String color;

    public Vehicle(String name, String licencePlate, String color) {
        this.name = name;
        this.licencePlate = licencePlate;
        this.color = color;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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
