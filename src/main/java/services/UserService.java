package services;

import entities.User;
import interfaces.EService;
import utiles.MyConnection;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class UserService implements EService<User> {

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
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement("UPDATE `user` SET `firstname` = ?, `lastname` = ?, `email` = ?, `number` = ?, `role` = ?, `username` = ? WHERE `id` = ?");
            //pst.setString(1, user.getPassword());
            pst.setString(1, user.getFirstname());
            pst.setString(2, user.getLastname());
            pst.setString(3, user.getEmail());
            pst.setString(4, user.getNumber());
            pst.setString(5, user.getRole());
            pst.setString(6, user.getUsername());
            pst.setInt(7, user.getId());

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

    public boolean updatePassword(String newPassword,String email) {
        String query = "UPDATE user SET password = ? where email= ?";
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

    public boolean doesEmailExist(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            conn = MyConnection.getInstance().getCnx();
            String sql = "SELECT COUNT(*) FROM user WHERE email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                exists = count > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return exists;
    }

    public void updateOTP(String email, String otp) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = MyConnection.getInstance().getCnx();
            String sql = "UPDATE user SET OTP = ? WHERE email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, otp);
            stmt.setString(2, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public String getOTP(String email) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        String otp = null;

        try {
            conn = MyConnection.getInstance().getCnx();
            String sql = "SELECT otp FROM user WHERE email = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, email);
            rs = stmt.executeQuery();
            if (rs.next()) {
                otp = rs.getString("otp");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return otp;
    }
    public Map<String, Integer> getRoleCounts() {
        Map<String, Integer> roleCounts = new HashMap<>();
        String query = "SELECT role, COUNT(*) AS count FROM user GROUP BY role";
        try (Connection conn = MyConnection.getInstance().getCnx();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                String role = rs.getString("role");
                int count = rs.getInt("count");
                roleCounts.put(role, count);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return roleCounts;
    }

}
