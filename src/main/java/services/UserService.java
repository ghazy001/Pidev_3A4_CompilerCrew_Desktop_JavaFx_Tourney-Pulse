package services;

import entities.User;
import interfaces.CRUD;
import utiles.MyConnection;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



public class UserService implements CRUD<User> {

    @Override
    public void addEntity(User user) {
        String requete = "INSERT INTO `user`(`firstname`, `lastname`, `username`, `email`, `number`, `password`, `role`, `image`) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setString(1, user.getFirstname());
            pst.setString(2, user.getLastname());
            pst.setString(3, user.getUsername());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getNumber());
            pst.setString(6, user.getPassword());
            pst.setString(7, user.getRole());

            // Set the image data
            File imageFile = user.getImageFile();
            if (imageFile != null && imageFile.exists()) {
                FileInputStream fis = new FileInputStream(imageFile);
                pst.setBinaryStream(8, fis, (int) imageFile.length());
            } else {
                pst.setBinaryStream(8, null);
            }

            pst.executeUpdate();
            System.out.println("User added!!");
        } catch (SQLException | IOException e) {
            System.out.println(e.getMessage());
        }
    }


    @Override
    public void updateEntity(User user) {
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement("UPDATE `user` SET `password` = ?, `firstname` = ?, `lastname` = ?, `email` = ?, `number` = ?, `role` = ?, `username` = ? WHERE `id` = ?");
            pst.setString(1, user.getPassword());
            pst.setString(2, user.getFirstname());
            pst.setString(3, user.getLastname());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getNumber());
            pst.setString(6, user.getRole());
            pst.setString(7, user.getUsername()); // Add the username
            pst.setInt(8, user.getId());

            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated!!");
            } else {
                System.out.println("User with id " + user.getId() + " does not exist.");
            }
        } catch (SQLException e) {
            System.out.println("Error updating user: " + e.getMessage());
        }
    }


    @Override
    public void deleteEntity(User user) {
        String requete = "DELETE FROM `user` WHERE `id` = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(requete);
            pst.setInt(1, user.getId());
            int rowsDeleted = pst.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("User deleted!!");
            } else {
                System.out.println("User with id " + user.getId() + " does not exist.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<User> getAllData() {
        List<User> data = new ArrayList<>();
        String requete = "SELECT * FROM user";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            ResultSet rs = st.executeQuery(requete);
            while (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setNumber(rs.getString("number"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                data.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return data;
    }
    public boolean saveOTP(String email, String otp) {
        // Check if email already exists
        if (getUserByEmail(email) != null) {
            // Email already exists, update OTP
            String updateQuery = "UPDATE user SET OTP = ? WHERE email = ?";
            try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(updateQuery)) {
                pst.setString(1, otp);
                pst.setString(2, email);
                int rowsUpdated = pst.executeUpdate();
                return rowsUpdated > 0;
            } catch (SQLException e) {
                System.out.println("Error updating OTP: " + e.getMessage());
                return false;
            }
        } else {
            // Email does not exist, insert new OTP record
            String insertQuery = "INSERT INTO user (email, OTP) VALUES (?, ?)";
            try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(insertQuery)) {
                pst.setString(1, email);
                pst.setString(2, otp);
                int rowsInserted = pst.executeUpdate();
                return rowsInserted > 0;
            } catch (SQLException e) {
                System.out.println("Error saving OTP: " + e.getMessage());
                return false;
            }
        }
    }

    public boolean updatePassword(String email, String newPassword) {
        String query = "UPDATE user SET password = ? WHERE email = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, newPassword);
            pst.setString(2, email);
            int rowsUpdated = pst.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            System.out.println("Error updating password: " + e.getMessage());
            return false;
        }
    }

    private User getUserByEmail(String email) {
        String query = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query)) {
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setId(rs.getInt("id"));
                user.setFirstname(rs.getString("firstname"));
                user.setLastname(rs.getString("lastname"));
                user.setUsername(rs.getString("username"));
                user.setEmail(rs.getString("email"));
                user.setNumber(rs.getString("number"));
                user.setPassword(rs.getString("password"));
                user.setRole(rs.getString("role"));
                return user;
            }
        } catch (SQLException e) {
            System.out.println("Error fetching user by email: " + e.getMessage());
        }
        return null;
    }
}
