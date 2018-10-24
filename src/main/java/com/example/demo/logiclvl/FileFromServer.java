package com.example.demo.logiclvl;

import com.example.demo.dao.DAOAttachedFile;
import com.example.demo.dao.DAOContacts;
import com.example.demo.dao.DAOInterface;
import com.example.demo.dao.model.AttachedFile;
import com.example.demo.dao.model.FileData;
import org.apache.log4j.Logger;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class FileFromServer {

    final static Logger logger = Logger.getLogger(FileFromServer.class);
    private static final String GLOBAL_PATH_TO_SAVE = "D:\\itechart\\files";
    private List<AttachedFile> attachedFileList= new ArrayList<>();
    private List<FileData> fileDataList= new ArrayList<>();
    private List<AttachedFile> attachedFileListToAdd = new ArrayList<>();
    private List<AttachedFile> attachedFileListToUpdate = new ArrayList<>();
    private DAOInterface daoInterface = new DAOAttachedFile();

    public void handleAttachedList(){

        if(attachedFileList.size()<1) return;
        logger.info("File from server method handle attached list");
        List<AttachedFile> listFromDB = daoInterface.getElementsByForeignKey(attachedFileList.get(0).getContactKey());
        for(int i=0;i<attachedFileList.size();i++){
            if(listFromDB.size()>i){
                if(!getAttachedFileList().get(i).equals(listFromDB.get(i))){
                    attachedFileListToUpdate.add(getAttachedFileList().get(i));
                }
            }
            else{
                attachedFileListToAdd.add(getAttachedFileList().get(i));
            }

        }
        updateAttachedFiles();
        addAttachedFiles();
    }

    private void addAttachedFiles(){
        logger.info("File from server method add attached file");
        for(AttachedFile attached:attachedFileListToAdd){
            String filePath = GLOBAL_PATH_TO_SAVE+"\\" +attached.getContactKey() +"\\"+attached.getId()+"\\" ;
            String fileName = attached.getFileName()+"."+ attached.getPathToFile();
            parseDecodedInfoToFile(getFileDataById(attached.getId()),filePath,fileName);
            attached.setPathToFile(filePath+fileName);
            daoInterface.addEntity(attached);
        }
    }

    private String getFileDataById(int id){
        for(FileData fdata:fileDataList){
            if(fdata.getId()==id) return fdata.getEncodedFile();
        }
        return null;
    }

    private void updateAttachedFiles(){
        logger.info("File from server method update attached");
        for(AttachedFile attachedFile:attachedFileListToUpdate){
            daoInterface.updateEntity(attachedFile);
        }
    }


    public void parseDecodedInfoToFile(String inBase64,String filePath,String fileName){
        inBase64 = inBase64.replaceAll("\"","");
        FileOutputStream fos = null;
        try {
            File fl = new File(filePath);
            fl.mkdirs();
            fos = new FileOutputStream(filePath+fileName);
            String partSeparator = ",";
            String encodedFile = inBase64.split(partSeparator)[1];
            fos.write(Base64.getDecoder().decode(encodedFile));
            fos.close();
        } catch (FileNotFoundException e) {
            logger.info("File from server method parseDecodedInfoToFile "+filePath + fileName+"\n" + e);
        } catch (IOException e) {
            logger.info("File from server method parseDecodedInfoToFile \n" + e);
        }
        catch(SecurityException se){
            logger.info("File from server method parseDecodedInfoToFile \n" + se);
        }
    }

    public String avatarToBase64(String filePath){
        String imageInBase64="";
        try {
            if(filePath == null || filePath.equals("none"))
                filePath = "D:\\itechart\\files\\avatars\\profile.jpg";
            File fl = new File(filePath);
            byte[] bt = Files.readAllBytes(fl.toPath());
            imageInBase64= Base64.getEncoder().encodeToString(bt);
            imageInBase64 = "data:image/jpg;base64," + imageInBase64;
        } catch (FileNotFoundException e) {
            logger.info("File from server method avatarToBase64 "+filePath + "\n" + e);
        } catch (IOException e) {
            logger.info("File from server method avatarToBase64 "+filePath + "\n" + e);
        }
        catch(SecurityException se){
            logger.info("File from server method avatarToBase64 "+filePath + "\n" + se);
        }
        return imageInBase64;
    }


    public FileFromServer() {
    }

    public FileFromServer(List<AttachedFile> attachedFileList, List<FileData> fileDataList) {
        this.attachedFileList = attachedFileList;
        this.fileDataList = fileDataList;
    }

    public List<AttachedFile> getAttachedFileList() {

        return attachedFileList;
    }

    public void setAttachedFileList(List<AttachedFile> attachedFileList) {
        this.attachedFileList = attachedFileList;
    }

    public List<FileData> getFileDataList() {
        return fileDataList;
    }

    public void setFileDataList(List<FileData> fileDataList) {
        this.fileDataList = fileDataList;
    }
}
