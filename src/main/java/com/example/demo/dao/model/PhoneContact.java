package com.example.demo.dao.model;

public class PhoneContact {


    private int id;
    private int countryCode;
    private int operatorCode;
    private String phoneNumber;
    private int phoneType;
    private String comments;
    private int contactKey;

    public PhoneContact(){

    }

    public PhoneContact(int id, int countryCode, int operatorCode, int phoneType, String phoneNumber, String comments, int contactKey) {
        this.id = id;
        this.countryCode = countryCode;
        this.operatorCode = operatorCode;
        this.phoneType = phoneType;
        this.phoneNumber = phoneNumber;
        this.comments = comments;
        this.contactKey = contactKey;
    }

    public int getContactKey() {
        return contactKey;
    }

    public void setContactKey(int contactKey) {
        this.contactKey = contactKey;
    }


    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public int getCountryCode() {
        return countryCode;
    }
    public void setCountryCode(int countryCode) {
        this.countryCode = countryCode;
    }
    public int getOperatorCode() {
        return operatorCode;
    }
    public void setOperatorCode(int operatorCode) {
        this.operatorCode = operatorCode;
    }
    public int getPhoneType() {
        return phoneType;
    }
    public void setPhoneType(String type) {
        if(type.equals("home"))
            this.phoneType=1;
        else
            this.phoneType=2;
    }
    public String getPhoneNumber() {
        return phoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return getCountryCode()+""+getContactKey()+""+getPhoneNumber()+"type " + phoneType + "comments" + getComments();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!PhoneContact.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final PhoneContact other = (PhoneContact) obj;

        if ((this.phoneNumber == null) ? (other.phoneNumber != null) : !this.phoneNumber.equals(other.phoneNumber)) {
            return false;
        }

        if ((this.comments == null) ? (other.comments != null) : !this.comments.equals(other.comments)) {
            return false;
        }

        if (this.countryCode != other.countryCode || this.operatorCode != other.operatorCode || this.phoneType!=other.phoneType) {
            return false;
        }

        return true;
    }
}
