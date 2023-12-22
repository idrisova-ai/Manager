package com.example.demo;

import java.sql.*;

public class database {
    public static final String USER_NAME = "root";
    public static final String PASSWORD = "1409";
    public static final String URL = "jdbc:mysql://localhost:3306/manager";
    public static Statement statement;
    public static Connection connection;
    public static Connection connectDB() {
        try {
            //Class.forName("com.mysql.jdbc.Drive");
            connection = DriverManager.getConnection(URL, USER_NAME, PASSWORD) ;
            return connection;
        } catch (Exception e) {e.printStackTrace();}
        return null;
    }
}
