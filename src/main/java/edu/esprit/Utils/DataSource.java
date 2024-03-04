package edu.esprit.Utils;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    private final String URL = "jdbc:mysql://localhost:3306/esprit3";
    private final String USER = "root";
    private final String PWD = "";

    private Connection cnx;

    private static DataSource instance;

    private DataSource(){
        try {
            cnx = DriverManager.getConnection(URL,USER,PWD);
            System.out.println("Connected successfully !");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static DataSource getInstance(){
        if(instance == null)
            instance = new DataSource();
        return instance;
    }

    public Connection getCnx(){
        return this.cnx;
    }
}
