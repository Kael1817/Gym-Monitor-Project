package com.gymmonitor;

public class coaches {
    String id;
    String name;
    String address;
    String gender;
    String phonenum;
    String status;

    public coaches(String id,String name, String address, String gender, String phonenum, String status) {
        this.id = id;
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.phonenum = phonenum;
        this.status = status;
    }

    public String getId(){
        return id;
    }
    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getGender() {
        return gender;
    }

    public String getPhonenum() {
        return phonenum;
    }
    public  String getStatus(){
        return status;
    }

    public String setId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public void setPhonenum(String phonenum) {
        this.phonenum = phonenum;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
