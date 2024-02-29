package services;

import entities.User;
import utiles.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginService {

    public User getUserByUsername(String username) {
        String query = "SELECT * FROM user WHERE username=?";
        User user = null;

        try {
            PreparedStatement statement = MyConnection.getInstance().getCnx().prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String firstname = resultSet.getString("firstname");
                String lastname = resultSet.getString("lastname");
                String email = resultSet.getString("email");
                String number = resultSet.getString("number");
                String password = resultSet.getString("password");
                String role = resultSet.getString("role");

                user = new User(id, firstname, lastname, username, email, number, password, role);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user by username: " + e.getMessage());
        }

        return user;
    }
}
