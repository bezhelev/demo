package com.example.demo.dao;

import com.example.demo.dao.model.Address;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DAOAddress  implements DAOInterface<Address> {

    @Override
    public Address getEntityById(int id) {
        PreparedStatement statement = null;
        Address address = new Address();
        try (Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("select * from address where id = ?");
            statement.setInt(1, id);
            ResultSet resSet = statement.executeQuery();
            if (resSet.next()) {
                address.setId(resSet.getInt(1));
                address.setCountry (resSet.getString(2));
                address.setCity (resSet.getString(3));
                address.setStreet(resSet.getString(4));
                address.setHouse(resSet.getString(5));
                address.setFlat(resSet.getString(6));
                address.setPostOfficeIndex(resSet.getString(7));
            }
            resSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e ){
            e.printStackTrace();
        }
        finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {

            }
        }
        return address;
    }

    @Override
    public List<Address> getAll(int numberRecord,int countRecordOnPage) {
        List<Address> addressList = new ArrayList<>();
        PreparedStatement statement = null;
        Address address = new Address();
        try ( Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("select * from address limit ?,?");
            ResultSet resSet = statement.executeQuery();
            while (resSet.next()) {

                address.setId(resSet.getInt(1));
                address.setCountry (resSet.getString(2));
                address.setCity (resSet.getString(3));
                address.setStreet(resSet.getString(4));
                address.setHouse(resSet.getString(5));
                address.setFlat(resSet.getString(6));
                address.setPostOfficeIndex(resSet.getString(7));
                addressList.add(address);
            }
            resSet.close();
        } catch (SQLException e) {

        }  catch (ClassNotFoundException e ){
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {

            }
        }
        return addressList;
    }

    @Override
    public void addEntity(Address entity) {
        PreparedStatement statement = null;

        try ( Connection connection = DSConnestion.getConnection()) {
            statement = connection.prepareStatement("insert into address (country,city,street,house,flat," +
                    "post_office_index values (?,?,?,?,?,?)" );
            statement.setString(1, entity.getCountry());
            statement.setString(2, entity.getCity());
            statement.setString(3, entity.getStreet());
            statement.setString(4, entity.getHouse());
            statement.setString(5, entity.getFlat());
            statement.setString(6, entity.getPostOfficeIndex());
            statement.executeUpdate();
        } catch (SQLException e) {

        } catch (ClassNotFoundException e ){
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {

            }
        }
    }

    @Override
    public void updateEntity(Address entity) {
        PreparedStatement statement = null;
        try ( Connection connection = DSConnestion.getConnection()) {
            statement = connection
                    .prepareStatement("UPDATE address SET country= ?,"
                            + " city = ?, street= ?, house= ?, flat= ?,"
                            + " post_office_index = ? WHERE id = ?");
            statement.setString(1, entity.getCountry());
            statement.setString(2, entity.getCity());
            statement.setString(3, entity.getStreet());
            statement.setString(4, entity.getHouse());
            statement.setString(5, entity.getFlat());
            statement.setString(6, entity.getPostOfficeIndex());
            statement.setInt(7, entity.getId());
            statement.executeUpdate();
        } catch (SQLException e) {

        } catch (ClassNotFoundException e ){
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {

            }
        }
    }

    @Override
    public void deleteEntity(int id,int contactKey) {
        PreparedStatement statement = null;
        try ( Connection connection = DSConnestion.getConnection()) {

            statement = connection.prepareStatement("DELETE from address WHERE id = ?");
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {

        } catch (ClassNotFoundException e ){
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }

            } catch (SQLException e) {

            }
        }
    }

    @Override
    public List<Address> getElementsByForeignKey(int id) {
        return null;
    }

}
