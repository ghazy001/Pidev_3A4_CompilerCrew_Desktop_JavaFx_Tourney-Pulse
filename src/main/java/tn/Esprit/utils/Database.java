package tn.Esprit.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    private static Database instance;
    private Connection connection;
    private final String URL = "jdbc:mysql://127.0.0.1/esprit";
    private final String USERNAME = "root";
    private final String PASSWORD = "";

    private Database() {
        try {
            connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
            System.out.println("__ Connected __ ");
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            System.out.println("__ Not Connected __");
        }
    }

    public static Database getInstance(){
        if (instance == null)
            return instance = new Database();
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }
}
