package com.example.eshopie.model;

public class AddressModel {

    private String name;
    private String address;
    private String pincode;
    private Boolean selected;

    public AddressModel(String name, String address, String pincode, Boolean selected) {
        this.name = name;
        this.address = address;
        this.pincode = pincode;
        this.selected = selected;
    }

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
