package com.example.demo.dao;

import com.example.demo.dao.model.Contacts;

import java.sql.Date;
import java.util.List;

public interface DAOContactInterface<T>  {
    List<Contacts> getFilteredData(Contacts entity, Date dataBirthday);
    int getLastIdRecord();
    int getCountRecord();
    T getEntityById(int id);
    List<T> getAll(int numberRecord, int countRecordOnPage);
    void addEntity(T entity);
    void updateEntity(T entity);
    void deleteEntity(int id);
    List<String> getBirthdayBoyEmail();
}
