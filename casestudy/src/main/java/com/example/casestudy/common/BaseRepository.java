package com.example.casestudy.common;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

// Kết nối DB
public class BaseRepository {
    private Connection connection;
    private static final String URL = "jdbc:mysql://localhost:3306/grocerystore";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "bln2004";

    public BaseRepository() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("Successful connection!!!");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }
}
