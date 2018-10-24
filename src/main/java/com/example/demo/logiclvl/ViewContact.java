    package com.example.demo.logiclvl;

import com.example.demo.dao.model.Contacts;
import org.apache.log4j.Logger;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ViewContact {
    final static Logger logger = Logger.getLogger(TransformationPhoneContact.class);
    private int id;
    private String fullName;
    private Date birthday;
    private String currentJob;
    private String email;
    private String fullAddress;


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ViewContact remakeEntity(Contacts contacts){
        ViewContact viewContact = new ViewContact();
        viewContact.id = contacts.getId();
        viewContact.fullName =  contacts.getSecondName() + " " + contacts.getFirstName() + " "+ contacts.getThirdName() ;
        viewContact.birthday = contacts.getBirthday();
        viewContact.email = contacts.getEmail();
        viewContact.currentJob = contacts.getCurrentJob();
        viewContact.fullAddress = contacts.getCountry()+ ", " +contacts.getCity()+ ", " +contacts.getStreet()+ ", " +contacts.getHouse()+ ", " +contacts.getFlat()+ ", " +contacts.getPostOfficeIndex();

        return viewContact;
    }

    public List<ViewContact> remakeEntityList(List<Contacts> contactsList){
        List<ViewContact> viewContactList = new ArrayList<>();
        ViewContact viewContact;
        if(contactsList==null) return null;
        for(Contacts contacts : contactsList){
            viewContact = remakeEntity(contacts);
            viewContactList.add(viewContact);
        }
        return viewContactList;
    }

    public ViewContact() {
    }

    public ViewContact(String fullName, Date birthday, String currentJob, String fullAddress) {
        this.fullName = fullName;
        this.birthday = birthday;
        this.currentJob = currentJob;
        this.fullAddress = fullAddress;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCurrentJob() {
        return currentJob;
    }

    public void setCurrentJob(String currentJob) {
        this.currentJob = currentJob;
    }

    public String getFullAddress() {
        return fullAddress;
    }

    public void setFullAddress(String fullAddress) {
        this.fullAddress = fullAddress;
    }
}
