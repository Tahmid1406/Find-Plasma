package com.Tlab.bloodfinder.Models;

public class UserInfo {
    private String name, phone, bloodgroup, treatment, location;

    public UserInfo() {
    }

    public UserInfo(String name, String phone, String bloodgroup, String treatment, String location) {
        this.name = name;
        this.phone = phone;
        this.bloodgroup = bloodgroup;
        this.treatment = treatment;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getBloodgroup() {
        return bloodgroup;
    }

    public void setBloodgroup(String bloodgroup) {
        this.bloodgroup = bloodgroup;
    }

    public String getTreatment() {
        return treatment;
    }

    public void setTreatment(String treatment) {
        this.treatment = treatment;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }
}
