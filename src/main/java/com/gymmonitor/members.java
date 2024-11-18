package com.gymmonitor;

public class members {
    String id;
    String name;
    String address;
    String gender;
    String phonenum;
    String expiration;

    public members(String expiration,String id, String name, String address, String gender, String phonenum) {
        this.name = name;
        this.address = address;
        this.gender = gender;
        this.phonenum = phonenum;
        this.id = id;
        this.expiration = expiration;
    }

    public String getExpiration() {
        return expiration;
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

    public void setId(String  id){
        this.id = id;
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
    public void setExpiration(String expiration){
        this.expiration = expiration;
    }
}
