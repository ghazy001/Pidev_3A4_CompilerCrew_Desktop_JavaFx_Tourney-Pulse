package edu.esprit.utils;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataSource {
    final String url = "jdbc:mysql://localhost:3306/pidev_travel_me";
    final String Username = "root";
    final String Password = "";
    private Connection connection;
    private static DataSource insatnce;

    private DataSource() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/database", "root", "");
            System.out.println("Connection etablie avec Succee");
        } catch (SQLException var2) {
            System.out.println(var2.getMessage());
        }

    }

    public static DataSource getInsatnce() {
        if (insatnce == null) {
            insatnce = new DataSource();
        }

        return insatnce;
    }

    public Connection getConnection() {

        return this.connection;
    }






}
