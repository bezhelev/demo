package com.example.demo.dao.model;

import java.sql.Date;

public class AttachedFile {
    private int id;
    private String fileName;
    private Date uploadDate;
    private String comments;
    private String pathToFile;
    private int contactKey;

    public AttachedFile() {
    }

    public AttachedFile(int id, String fileName, Date uploadDate, String comments, String pathToFile, int contactKey) {
        this.id = id;
        this.fileName = fileName;
        this.uploadDate = uploadDate;
        this.comments = comments;
        this.pathToFile = pathToFile;
        this.contactKey = contactKey;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Date getUploadDate() {
        return uploadDate;
    }

    public void setUploadDate(Date uploadDate) {
        this.uploadDate = uploadDate;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getPathToFile() {
        return pathToFile;
    }

    public void setPathToFile(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public int getContactKey() {
        return contactKey;
    }

    public void setContactKey(int contactKey) {
        this.contactKey = contactKey;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        if (!AttachedFile.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        final AttachedFile other = (AttachedFile) obj;

        if(this.id!=other.getId() || this.contactKey!=other.contactKey){
            return false;
        }

        if ((this.comments == null) ? (other.comments != null) : !this.comments.equals(other.comments)) {
            return false;
        }

        if ((this.fileName == null) ? (other.fileName != null) : !this.fileName.equals(other.fileName)) {
            return false;
        }

        return true;
    }
}
