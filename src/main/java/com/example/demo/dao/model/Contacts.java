package com.example.demo.dao.model;

import java.sql.Date;

public class Contacts {

    private int id;
    private String firstName;
    private String secondName;
    private String thirdName;
    private Date birthday;
    private int sex;
    private String citizenchip;
    private String maritalStatus;
    private String webSite;
    private String email;
    private String currentJob;
    private String country;
    private String city;
    private String street;
    private String house;
    private String flat;
    private String postOfficeIndex;
    private String pathToAvatar;


    public String getPathToAvatar() {
        return pathToAvatar;
    }

    public void setPathToAvatar(String pathToAvatar) {
        this.pathToAvatar = pathToAvatar;
    }



    public Contacts(int id, String firstName, String secondName, String thirdName, Date birthday, int sex, String citizenchip, String maritalStatus, String webSite, String email, String currentJob, String country, String city, String street, String house, String flat, String postOfficeIndex, String pathToAvatar) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.birthday = birthday;
        this.sex = sex;
        this.citizenchip = citizenchip;
        this.maritalStatus = maritalStatus;
        this.webSite = webSite;
        this.email = email;
        this.currentJob = currentJob;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.postOfficeIndex = postOfficeIndex;
        this.pathToAvatar = pathToAvatar;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    public String getPostOfficeIndex() {
        return postOfficeIndex;
    }

    public void setPostOfficeIndex(String postOfficeIndex) {
        this.postOfficeIndex = postOfficeIndex;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getSecondName() {
        return secondName;
    }
    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }
    public String getThirdName() {
        return thirdName;
    }
    public void setThirdName(String thirdName) {
        this.thirdName = thirdName;
    }
    public Date getBirthday() {
        return birthday;
    }
    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
    public int getSex() {
        return sex;
    }
    public void setSex(String sex) {
        if(sex.equals("female"))
            this.sex = 2;
        else
            this.sex = 1;
    }
    public String getCitizenchip() {
        return citizenchip;
    }
    public void setCitizenchip(String citizenchip) {
        this.citizenchip = citizenchip;
    }
    public String getMaritalStatus() {
        return maritalStatus;
    }
    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }
    public String getWebSite() {
        return webSite;
    }
    public void setWebSite(String webSite) {
        this.webSite = webSite;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getCurrentJob() {
        return currentJob;
    }
    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public Contacts(){

    }

    public Contacts(int id, String firstName, String secondName, String thirdName, Date birthday, int sex, String citizenchip, String maritalStatus, String webSite, String email, String currentJob, String country, String city, String street, String house, String flat, String postOfficeIndex) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.thirdName = thirdName;
        this.birthday = birthday;
        this.sex = sex;
        this.citizenchip = citizenchip;
        this.maritalStatus = maritalStatus;
        this.webSite = webSite;
        this.email = email;
        this.currentJob = currentJob;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
        this.postOfficeIndex = postOfficeIndex;
    }


    @Override
    public String toString() {
        return getFirstName() + " " + getSecondName() + " " + getThirdName() + " " + birthday.toString()
                + " " + getCitizenchip() + " " + getCountry() + " " + getCity() + " " + getHouse() + " " + getFlat();
    }
}

