package com.example.demo.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;


import com.example.demo.dao.model.PhoneContact;
import org.apache.log4j.Logger;

public class DAOPhoneContact implements DAOInterface<PhoneContact> {
    final static Logger logger = Logger.getLogger(DAOPhoneContact.class);

    @Override
    public PhoneContact getEntityById(int id) {
        logger.info("dao phoneContact getEntityById id " + id );
        PreparedStatement statement = null;
        PhoneContact phoneContact = new PhoneContact();
        try ( Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("select * from phone_contact where id = ?");
            statement.setInt(1, id);
            ResultSet resSet = statement.executeQuery();
            if (resSet.next()) {
                phoneContact.setId(resSet.getInt(1));
                phoneContact.setCountryCode(resSet.getInt(2));
                phoneContact.setOperatorCode(resSet.getInt(3));
                phoneContact.setPhoneNumber(resSet.getString(4));
                phoneContact.setPhoneType( resSet.getString(5));
                phoneContact.setComments(resSet.getString(6));
                phoneContact.setContactKey(resSet.getInt(7));

            }
            resSet.close();
        } catch (SQLException e) {
            logger.error("dao phoneContact getEntityById id " +id+"\n" +e);
        }  catch (ClassNotFoundException e ){
            logger.error("dao phoneContact getEntityById id " +id+"\n" +e);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                logger.error("dao phoneContact getEntityById id " +id+"\n" +e);
            }
        }
        return phoneContact;
    }

    @Override
    public List<PhoneContact> getAll(int numberRecord,int countRecordOnPage) {
        logger.info("dao phoneContact get all number " + numberRecord + " count " + countRecordOnPage);
        List<PhoneContact> phoneContactList = new ArrayList<>();
        Statement statement = null;
        PhoneContact phoneContact = new PhoneContact();
        try ( Connection connection = DSConnestion.getConnection()) {
            statement = connection.createStatement();
            ResultSet resSet = statement.executeQuery("select * from phone_contact");
            while (resSet.next()) {
                phoneContact.setId(resSet.getInt(1));
                phoneContact.setCountryCode(resSet.getInt(2));
                phoneContact.setOperatorCode(resSet.getInt(3));
                phoneContact.setPhoneNumber(resSet.getString(4));
                phoneContact.setPhoneType( resSet.getString(5));
                phoneContact.setComments(resSet.getString(6));
                phoneContact.setContactKey(resSet.getInt(7));
                phoneContactList.add(phoneContact);
            }
            resSet.close();
        } catch (SQLException e) {
            logger.error("dao phoneContact get all number " +numberRecord +" count "+countRecordOnPage +"\n" +e);
        }  catch (ClassNotFoundException e ){
            logger.error("dao phoneContact get all number " +numberRecord +" count "+countRecordOnPage +"\n" +e);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                logger.error("dao phoneContact get all number " +numberRecord +" count "+countRecordOnPage +"\n" +e);
            }
        }
        return phoneContactList;
    }

    @Override
    public void addEntity(PhoneContact entity) {
        logger.info("dao phoneContact add contacts : " + entity.toString());
        PreparedStatement statement = null;
        try ( Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("insert into phone_contact (country_coude, operator_code, phone_number, phone_type, "
                    + "comments , contact_key) values (?,?,?,?,?,?)" );
            addOrUpdate(statement,entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("dao phoneContact addEntity contact : " +entity.toString()+"\n" +e);
        } catch (ClassNotFoundException e ){
            logger.error("dao phoneContact addEntity contact : " +entity.toString()+"\n" +e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                logger.error("dao phoneContact addEntity contact : " +entity.toString()+"\n" +e);
            }
        }
    }

    void addOrUpdate( PreparedStatement statement,PhoneContact entity )throws SQLException{
        statement.setInt(1, entity.getCountryCode());
        statement.setInt(2, entity.getOperatorCode());
        statement.setString(3, entity.getPhoneNumber());
        statement.setInt(4, entity.getPhoneType());
        statement.setString(5, entity.getComments());
        statement.setInt(6, entity.getContactKey());
    }

    @Override
    public void updateEntity(PhoneContact entity) {
        PreparedStatement statement = null;
        logger.info("dao phoneContact update contacts : " + entity.toString());
        try ( Connection connection = DSConnestion.getConnection()) {
            statement = connection
                    .prepareStatement("UPDATE phone_contact SET country_coude= ?,"
                            + " operator_code = ?, phone_number = ?, phone_type = ?, comments = ?,"
                            + " contact_key = ? WHERE id = ?");
            addOrUpdate(statement,entity);
            statement.setInt(7, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("dao phoneContact updateEntity contact : " +entity.toString()+"\n" +e);
        }  catch (ClassNotFoundException e ){
            logger.error("dao phoneContact updateEntity contact : " +entity.toString()+"\n" +e);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                logger.error("dao phoneContact updateEntity contact : " +entity.toString()+"\n" +e);
            }
        }
    }

    @Override
    public void deleteEntity(int id,int contactKey) {
        logger.info("dao phoneContact deleteEntity id " + id );
        PreparedStatement statement = null;
        try ( Connection connection = DSConnestion.getConnection()) {

            statement = connection.prepareStatement("DELETE from phone_contact WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("dao phoneContact delete id : " +id+"\n" +e);
        }  catch (ClassNotFoundException e ){
            logger.error("dao phoneContact delete id : " +id+"\n" +e);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("dao phoneContact delete id : " +id+"\n" +e);
            }
        }
    }

    @Override
    public List<PhoneContact> getElementsByForeignKey (int id) {
        logger.info("dao phoneContact getElementsByForeignKey id " + id );
        List<PhoneContact> listPhoneContact = new ArrayList();
        PreparedStatement statement = null;
        try ( Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("select * from phone_contact where contact_key = ?");
            statement.setInt(1, id);
            ResultSet resSet = statement.executeQuery();
            PhoneContact phoneContact = new PhoneContact();
            while (resSet.next()) {
                phoneContact = new PhoneContact();
                phoneContact.setId(resSet.getInt(1));
                phoneContact.setCountryCode(resSet.getInt(2));
                phoneContact.setOperatorCode(resSet.getInt(3));
                phoneContact.setPhoneNumber(resSet.getString(4));
                phoneContact.setPhoneType( resSet.getString(5));
                phoneContact.setComments(resSet.getString(6));
                phoneContact.setContactKey(resSet.getInt(7));
                listPhoneContact.add(phoneContact);
            }
            resSet.close();
        } catch (SQLException e) {
            logger.error("dao phoneContact getElementsByForeignKey id " +id+"\n" +e);
        }  catch (ClassNotFoundException e ){
            logger.error("dao phoneContact getElementsByForeignKey id " +id+"\n" +e);
        }finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                logger.error("dao phoneContact getElementsByForeignKey id " +id+"\n" +e);
            }
        }
        return listPhoneContact;
    }


}
