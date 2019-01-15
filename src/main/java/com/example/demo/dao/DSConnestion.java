package com.example.demo.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DSConnestion {

    private static DSConnestion _instance = null;
    private static String url;
    private static String user;
    private static String pass;
    private int initConnCnt = 1;

    private DSConnestion() throws ClassNotFoundException {

        Class.forName("com.mysql.jdbc.Driver");
        DSConnestion.url = "jdbc:mysql://localhost:3306/bezhelev?autoReconnect=true&useSSL=false";
        DSConnestion.user = "root";
        DSConnestion.pass = "local";

    }

    private static Connection connection() throws SQLException {
        Connection conn = DriverManager.getConnection(url, user, pass);
        return conn;
    }


    public static synchronized Connection getConnection() throws SQLException, ClassNotFoundException {

        if (_instance == null) {
            _instance = new DSConnestion();
        }

        return _instance.connection();
    }

}
