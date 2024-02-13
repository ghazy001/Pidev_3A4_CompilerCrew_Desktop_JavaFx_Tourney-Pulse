package esprit.project.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class MyDB {
    final String url = "jdbc:mysql://localhost:3306/pidev_travel_me";
    final String Username = "root";
    final String Password = "";
    private Connection connection;
    private static MyDB insatnce;

    private MyDB() {
        try {
            this.connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "");
            System.out.println("Connection etablie avec Succ");
        } catch (SQLException var2) {
            System.out.println(var2.getMessage());
        }

    }

    public static MyDB getInsatnce() {
        if (insatnce == null) {
            insatnce = new MyDB();
        }

        return insatnce;
    }

    public Connection getConnection() {
        return this.connection;
    }






}
