package com.example.demo.dao;

import com.example.demo.dao.model.Contacts;
import com.example.demo.logiclvl.FileFromServer;
import org.apache.log4j.Logger;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class DAOContacts implements DAOContactInterface<Contacts> {
    final static Logger logger = Logger.getLogger(DAOContacts.class);
    final static String PATH_TO_AVATAR = "D:\\itechart\\files\\avatars\\";

    @Override
    public List<Contacts> getFilteredData(Contacts entity, Date dataBirthday) {
        logger.info("dao contact get filtered data" + entity);
        List<Contacts> contactsList = new ArrayList<>();
        PreparedStatement statement = null;
        try (Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("SELECT * FROM contacts Where first_name like ? and second_name like ? and " +
                    "third_name like ? and birthday > ? and birthday < ? and sex like ? and marital_status like ? and citizenchip like ? and " +
                    " country like ? and city like ? and street like ? and house like ? and flat like ? and post_office_index like ?");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            statement.setString(1, "%" + entity.getFirstName() + "%");
            statement.setString(2, "%" + entity.getSecondName() + "%");
            statement.setString(3, "%" + entity.getThirdName() + "%");
            statement.setString(4, dateFormat.format(entity.getBirthday()));
            statement.setString(5, dateFormat.format(dataBirthday));
            statement.setString(6, getSex(entity) + "%");
            statement.setString(7, "%" + entity.getMaritalStatus() + "%");
            statement.setString(8, "%" + entity.getCitizenchip() + "%");
            statement.setString(9, "%" + entity.getCountry() + "%");
            statement.setString(10, "%" + entity.getCity() + "%");
            statement.setString(11, "%" + entity.getStreet() + "%");
            statement.setString(12, "%" + entity.getHouse() + "%");
            statement.setString(13, "%" + entity.getFlat() + "%");
            statement.setString(14, "%" + entity.getPostOfficeIndex() + "%");
            ResultSet resSet = statement.executeQuery();
            Contacts contacts = new Contacts();
            while (resSet.next()) {
                contacts = new Contacts();
                contacts.setId(resSet.getInt(1));
                contacts.setFirstName(resSet.getString(2));
                contacts.setSecondName(resSet.getString(3));
                contacts.setThirdName(resSet.getString(4));
                contacts.setBirthday(resSet.getDate(5));
                contacts.setSex(resSet.getString(6));
                contacts.setCitizenchip(resSet.getString(7));
                contacts.setMaritalStatus(resSet.getString(8));
                contacts.setWebSite(resSet.getString(9));
                contacts.setEmail(resSet.getString(10));
                contacts.setCurrentJob(resSet.getString(11));
                contacts.setCountry(resSet.getString(12));
                contacts.setCity(resSet.getString(13));
                contacts.setStreet(resSet.getString(14));
                contacts.setHouse(resSet.getString(15));
                contacts.setFlat(resSet.getString(16));
                contacts.setPostOfficeIndex(resSet.getString(17));
                contactsList.add(contacts);
            }
        } catch (SQLException e) {
            logger.error("dao contact  get filtered data " + entity + " \n" + e);
        } catch (ClassNotFoundException e) {
            logger.error("dao contact  get filtered data " + entity + " \n" + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                logger.error("dao contact  get filtered data " + entity + " \n" + e);
            }
        }

        return contactsList;
    }


    @Override
    public int getLastIdRecord() {
        logger.info("dao contact get last id record");
        PreparedStatement statement = null;
        int countRecord = 0;
        try (Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("SELECT id FROM contacts ORDER BY id DESC LIMIT 1");
            ResultSet resSet = statement.executeQuery();
            if (resSet.next()) {
                countRecord = resSet.getInt(1);
            }
            resSet.close();
        } catch (SQLException e) {
            logger.error("dao contact getLastIdRecord \n" + e);
        } catch (ClassNotFoundException e) {
            logger.error("dao contact getLastIdRecord \n" + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("dao contact getLastIdRecord \n" + e);
            }
        }
        return countRecord;
    }

    @Override
    public int getCountRecord() {
        logger.info("dao contact get count record ");
        PreparedStatement statement = null;
        int countRecord = 0;
        try (Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("select count(*) from contacts ");
            ResultSet resSet = statement.executeQuery();
            if (resSet.next()) {
                countRecord = resSet.getInt(1);
            }
            resSet.close();
        } catch (SQLException e) {
            logger.error("dao contact getCountRecord \n" + e);
        } catch (ClassNotFoundException e) {
            logger.error("dao contact getCountRecord \n" + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                logger.error("dao contact getCountRecord \n" + e);
            }
        }
        return countRecord;
    }

    @Override
    public Contacts getEntityById(int id) {
        logger.info("dao contact getEntityById id " + id);
        PreparedStatement statement = null;
        Contacts contacts = new Contacts();
        try (Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("select * from contacts where id = ?");
            statement.setInt(1, id);
            ResultSet resSet = statement.executeQuery();
            if (resSet.next()) {
                contacts.setId(id);
                contacts.setFirstName(resSet.getString(2));
                contacts.setSecondName(resSet.getString(3));
                contacts.setThirdName(resSet.getString(4));
                contacts.setBirthday(resSet.getDate(5));
                contacts.setSex(resSet.getString(6));
                contacts.setCitizenchip(resSet.getString(7));
                contacts.setMaritalStatus(resSet.getString(8));
                contacts.setWebSite(resSet.getString(9));
                contacts.setEmail(resSet.getString(10));
                contacts.setCurrentJob(resSet.getString(11));
                contacts.setCountry(resSet.getString(12));
                contacts.setCity(resSet.getString(13));
                contacts.setStreet(resSet.getString(14));
                contacts.setHouse(resSet.getString(15));
                contacts.setFlat(resSet.getString(16));
                contacts.setPostOfficeIndex(resSet.getString(17));
                FileFromServer fls = new FileFromServer();
                contacts.setPathToAvatar(fls.avatarToBase64(resSet.getString(18)));
            }
            resSet.close();
        } catch (SQLException e) {
            logger.error("dao contact getEntityById id " + id + "\n" + e);
        } catch (ClassNotFoundException e) {
            logger.error("dao contact getEntityById id " + id + "\n" + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                logger.error("dao contact getEntityById id " + id + "\n" + e);
            }
        }
        return contacts;
    }


    @Override
    public List<Contacts> getAll(int numberRecord, int countRecordOnPage) {
        logger.info("dao contact get all number " + numberRecord + " count " + countRecordOnPage);
        PreparedStatement statement = null;
        List<Contacts> ContactsList = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("select id, first_name, second_name, third_name,birthday,email, current_job," +
                    "country, city, street, house, flat, post_office_index from contacts limit ?,?");
            statement.setInt(1, numberRecord);
            statement.setInt(2, countRecordOnPage);
            ResultSet resSet = statement.executeQuery();
            Contacts contacts = new Contacts();
            boolean flag = false;
            while (resSet.next()) {
                flag = true;
                contacts = new Contacts();
                contacts.setId(resSet.getInt(1));
                contacts.setFirstName(resSet.getString(2));
                contacts.setSecondName(resSet.getString(3));
                contacts.setThirdName(resSet.getString(4));
                contacts.setBirthday(resSet.getDate(5));
                contacts.setEmail(resSet.getString(6));
                contacts.setCurrentJob(resSet.getString(7));
                contacts.setCountry(resSet.getString(8));
                contacts.setCity(resSet.getString(9));
                contacts.setStreet(resSet.getString(10));
                contacts.setHouse(resSet.getString(11));
                contacts.setFlat(resSet.getString(12));
                contacts.setPostOfficeIndex(resSet.getString(13));
                ContactsList.add(contacts);
            }
            if (!flag) {
                return null;
            }
            resSet.close();
        } catch (SQLException e) {
            logger.error("dao contact get all number " + numberRecord + " count " + countRecordOnPage + "\n" + e);
        } catch (ClassNotFoundException e) {
            logger.error("dao contact get all number " + numberRecord + " count " + countRecordOnPage + "\n" + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                logger.error("dao contact get all number " + numberRecord + " count " + countRecordOnPage + "\n" + e);
            }
        }
        return ContactsList;
    }

    @Override
    public void addEntity(Contacts entity) {
        logger.info("dao contact add contacts : " + entity.toString());
        PreparedStatement statement = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try (Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("insert into contacts  (first_name, second_name,third_name,birthday,sex," +
                    "citizenchip,marital_status,web_site,email,current_job,country,city,street,house,flat," +
                    "post_office_index,path_to_avatar ) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            statement = addOrUpdate(statement, entity);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("dao contact addEntity contact : " + entity.toString() + "\n" + e);
        } catch (ClassNotFoundException e) {
            logger.error("dao contact addEntity contact " + entity.toString() + "\n" + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                logger.error("dao contact addEntity contact " + entity.toString() + "\n" + e);
            }
        }
    }

    void workWithImage(PreparedStatement prStatement, Contacts entity) throws SQLException {
        if (!entity.getPathToAvatar().equals("none")) {
            FileFromServer ffs = new FileFromServer();
            String fileName = entity.getId() + ".jpg";
            String pathToFile = PATH_TO_AVATAR + entity.getId() + "\\";
            ffs.parseDecodedInfoToFile(entity.getPathToAvatar(), pathToFile, fileName);
            entity.setPathToAvatar(pathToFile + fileName);
        }
        prStatement.setString(17, entity.getPathToAvatar());
    }

    PreparedStatement addOrUpdate(PreparedStatement statement, Contacts entity) throws SQLException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        statement.setString(1, entity.getFirstName());
        statement.setString(2, entity.getSecondName());
        statement.setString(3, entity.getThirdName());
        statement.setString(4, dateFormat.format(entity.getBirthday()));
        statement.setInt(5, entity.getSex());
        statement.setString(6, entity.getCitizenchip());
        statement.setString(7, entity.getMaritalStatus());
        statement.setString(8, entity.getWebSite());
        statement.setString(9, entity.getEmail());
        statement.setString(10, entity.getCurrentJob());
        statement.setString(11, entity.getCountry());
        statement.setString(12, entity.getCity());
        statement.setString(13, entity.getStreet());
        statement.setString(14, entity.getHouse());
        statement.setString(15, entity.getFlat());
        statement.setString(16, entity.getPostOfficeIndex());
        workWithImage(statement, entity);
        return statement;
    }

    @Override
    public void updateEntity(Contacts entity) {
        PreparedStatement statement = null;
        logger.info("dao contact update contacts : " + entity.toString());
        try (Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("update contacts SET first_name = ?, "
                    + "second_name = ?,third_name = ? , birthday = ?, sex=?,citizenchip = ?,marital_status = ?,"
                    + "web_site =?,email = ?,current_job = ?, country= ?,city = ?, street= ?, house= ?, flat= ?,"
                    + " post_office_index = ? , path_to_avatar = ? where id = ?");
            statement = addOrUpdate(statement, entity);
            statement.setInt(18, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("dao contact updateEntity contact : " + entity.toString() + "\n" + e);
        } catch (ClassNotFoundException e) {
            logger.error("dao contact updateEntity contact : " + entity.toString() + "\n" + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                logger.error("dao contact updateEntity contact : " + entity.toString() + "\n" + e);
            }
        }

    }

    @Override
    public List<String> getBirthdayBoyEmail() {
        List<String> birthdayEmail = new ArrayList<>();
        logger.info("dao contact get birthday boy email  ");
        PreparedStatement statement = null;
        try (Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("Select email from contacts where  MONTH(birthday) = ? and day(birthday) = ?");
            LocalDateTime now = LocalDateTime.now();
            statement.setInt(1, now.getMonth().getValue());
            statement.setInt(2, now.getDayOfMonth());
            ResultSet resSet = statement.executeQuery();
            String email;
            while (resSet.next()) {
                email = new String();
                email = resSet.getString(1);
                birthdayEmail.add(email);
            }
        } catch (SQLException e) {
            logger.error("dao contact get birthday boy email \n" + e);
        } catch (ClassNotFoundException e) {
            logger.error("dao contact get birthday boy email \n" + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                logger.error("dao contact get birthday boy email \n" + e);
            }
        }

        return birthdayEmail;
    }

    @Override
    public void deleteEntity(int id) {
        logger.info("dao contact deleteEntity id " + id);
        PreparedStatement statement = null;
        try (Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("DELETE from contacts WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error("dao contact delete id : " + id + "\n" + e);
        } catch (ClassNotFoundException e) {
            logger.error("dao contact delete id : " + id + "\n" + e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {
                logger.error("dao contact delete id : " + id + "\n" + e);
            }
        }

    }


    private String getSex(Contacts entity) {
        String sex = "";
        if (entity.getSex() == 0) {
            sex = "";
        }
        if (entity.getSex() == 1) {
            sex = "female";
        }
        if (entity.getSex() == 2) {
            sex = "male";
        }

        return sex;
    }


}
