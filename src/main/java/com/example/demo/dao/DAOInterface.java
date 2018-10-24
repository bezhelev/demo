package com.example.demo.dao;

import java.util.List;

public interface DAOInterface<T> {

    T getEntityById(int id);
    List<T> getAll(int numberRecord,int countRecordOnPage);
    void addEntity(T entity);
    void updateEntity(T entity);
    void deleteEntity(int id,int contactKey);
    List<T> getElementsByForeignKey(int id);

}