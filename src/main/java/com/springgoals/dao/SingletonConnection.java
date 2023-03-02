package com.springgoals.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static com.springgoals.constants.DBConstants.*;

public class SingletonConnection {

    private static SingletonConnection instance;
    private static Connection connection;

    private SingletonConnection() throws SQLException {
        try {
            Class.forName(dbDriver);
            this.connection = DriverManager.getConnection(connectionString, dbUser, dbPassword);
        } catch (ClassNotFoundException ex) {
            System.out.println("Something is wrong with the DB connection String : " + ex.getMessage());
        }
    }

    public static Connection getConnection() {
        return connection;
    }

    public static SingletonConnection getInstance() throws SQLException {
        if (instance == null) {
            instance = new SingletonConnection();
        } else if (instance.getConnection().isClosed()) {
            instance = new SingletonConnection();
        }
        return instance;
    }


}
