package com.example.demo.dao;

import com.example.demo.dao.model.AttachedFile;
import com.example.demo.dao.model.AttachedFile;
import com.example.demo.dao.model.PhoneContact;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DAOAttachedFile implements DAOInterface<AttachedFile> {
    final static Logger logger = Logger.getLogger(DAOPhoneContact.class);

    @Override
    public List<AttachedFile> getElementsByForeignKey(int id){
        logger.info("dao attached get all files ");
        List<AttachedFile> attachedFileList = new ArrayList<>();
        AttachedFile attachedFile = new AttachedFile();
        PreparedStatement statement = null;
        try ( Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("select * from attached_file where contact_key = ?");
            statement.setInt(1, id);
            ResultSet resSet = statement.executeQuery();
            while (resSet.next()) {
                attachedFile = new AttachedFile();
                attachedFile.setId(resSet.getInt(1));
                attachedFile.setFileName(resSet.getString(2));
                attachedFile.setUploadDate(resSet.getDate(3));
                attachedFile.setComments(resSet.getString(4));
                attachedFile.setPathToFile(resSet.getString(5));
                attachedFile.setContactKey(resSet.getInt(6));
                attachedFileList.add(attachedFile);
            }
            resSet.close();
        } catch (SQLException e) {
            logger.error("dao attached get all files \n" +e);
        } catch (ClassNotFoundException e ){
            logger.error("dao attached get all files \n" +e);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("dao attached get all files \n" +e);
            }
        }
        return attachedFileList;
    }

    @Override
    public AttachedFile getEntityById(int id) {
        logger.info("dao attached getEntityById id " + id );
        PreparedStatement statement = null;
        AttachedFile attachedFile =  new AttachedFile();
        try ( Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("select * from attached_file where id = ?");
            statement.setInt(1, id);
            ResultSet resSet = statement.executeQuery();
            if (resSet.next()) {
                attachedFile.setId(resSet.getInt(1));
                attachedFile.setFileName(resSet.getString(2));
                attachedFile.setUploadDate(resSet.getDate(3));
                attachedFile.setComments(resSet.getString(4));
                attachedFile.setPathToFile(resSet.getString(5));
                attachedFile.setContactKey(resSet.getInt(6));
            }
            resSet.close();
        } catch (SQLException e) {
            logger.error("dao attached getEntityById id " +id+"\n" +e);
        } catch (ClassNotFoundException e ){
            logger.error("dao attached getEntityById id " +id+"\n" +e);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                logger.error("dao attached getEntityById id " +id+"\n" +e);
            }
        }
        return attachedFile;
    }

    @Override
    public List<AttachedFile> getAll(int numberRecord,int countRecordOnPage) {
        logger.info("dao attached get all number " + numberRecord + " count " + countRecordOnPage);
        List<AttachedFile> attachedFileList = new ArrayList<>();
        Statement statement = null;
        AttachedFile attachedFile = new AttachedFile();
        try ( Connection connection = DSConnestion.getConnection()) {
            statement = connection.createStatement();
            ResultSet resSet = statement.executeQuery("select * from attached_file");
            while (resSet.next()) {
                attachedFile.setId(resSet.getInt(1));
                attachedFile.setFileName(resSet.getString(2));
                attachedFile.setUploadDate(resSet.getDate(3));
                attachedFile.setComments(resSet.getString(4));
                attachedFile.setPathToFile(resSet.getString(5));
                attachedFile.setContactKey(resSet.getInt(6));
                attachedFileList.add(attachedFile);
            }
            resSet.close();
        } catch (SQLException e) {
            logger.error("dao attached get all number " +numberRecord +" count "+countRecordOnPage +"\n" +e);
        } catch (ClassNotFoundException e ){
            logger.error("dao attached get all number " +numberRecord +" count "+countRecordOnPage +"\n" +e);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("dao attached get all number " +numberRecord +" count "+countRecordOnPage +"\n" +e);
            }
        }
        return attachedFileList;
    }

    @Override
    public void addEntity(AttachedFile entity) {
        logger.info("dao attached add contacts : " + entity.toString());
        PreparedStatement statement = null;
        try ( Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("insert into attached_file (file_name,upload_date,comments," +
                    "path_to_file, contact_key) values (?,?,?,?,?)" );
            addOrUpdate(statement,entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("dao attached addEntity contact : " +entity.toString()+"\n" +e);
        } catch (ClassNotFoundException e ){
            logger.error("dao attached addEntity contact : " +entity.toString()+"\n" +e);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("dao attached addEntity contact : " +entity.toString()+"\n" +e);
            }
        }
    }

    void addOrUpdate( PreparedStatement statement,AttachedFile entity )throws SQLException{
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        statement.setString(1, entity.getFileName());
        statement.setString(2, dateFormat.format(entity.getUploadDate()));
        statement.setString(3, entity.getComments());
        statement.setString(4, entity.getPathToFile());
        statement.setInt(5, entity.getContactKey());
    }

    @Override
    public void updateEntity(AttachedFile entity) {
        logger.info("dao attached update contacts : " + entity.toString());
        PreparedStatement statement = null;

        try ( Connection connection = DSConnestion.getConnection()) {
            statement = connection
                    .prepareStatement("UPDATE attached_file SET file_name= ?,"
                            + " upload_date = ?, comments= ?, path_to_file= ?, contact_key= ? WHERE id = ?");
            addOrUpdate(statement,entity);
            statement.setInt(6, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("dao attached updateEntity contact : " +entity.toString()+"\n" +e);
        } catch (ClassNotFoundException e ){
            logger.error("dao attached updateEntity contact : " +entity.toString()+"\n" +e);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("dao attached updateEntity contact : " +entity.toString()+"\n" +e);
            }
        }
    }

    @Override
    public void deleteEntity(int id,int contactKey) {
        logger.info("dao attached deleteEntity id " + id );
        PreparedStatement statement = null;
        try ( Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("DELETE from attached_file WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("dao attached delete id : " +id+"\n" +e);
        } catch (ClassNotFoundException e ){
            logger.error("dao attached delete id : " +id+"\n" +e);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("dao attached delete id : " +id+"\n" +e);
            }
        }
    }

}
