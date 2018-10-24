package com.example.demo.dao.model;

public class FileData {
    private int id;
    private String encodedFile;

    public FileData() {
    }

    public FileData(int id, String encodedFile) {
        this.id = id;
        this.encodedFile = encodedFile;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEncodedFile() {
        return encodedFile;
    }

    public void setEncodedFile(String encodedFile) {
        this.encodedFile = encodedFile;
    }
}
