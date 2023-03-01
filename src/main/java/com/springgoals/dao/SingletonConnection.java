package com.springgoals.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
public class SingletonConnection {

    private static SingletonConnection instance;
    private static Connection connection;

    //@Value("${connectionString}")
    private String connectionString="jdbc:mysql://localhost:3306/education";

    //@Value("${dbUser}")
    private String dbUser="root" ;

   // @Value("${dbPassword}")
    private String dbPassword="AngjelaWork123" ;

   // @Value("${test}")
    private String dbDriver = "com.mysql.cj.jdbc.Driver";


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