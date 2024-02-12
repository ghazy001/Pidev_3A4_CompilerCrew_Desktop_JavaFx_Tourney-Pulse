package services;
import entities.user;
import interfaces.CRUD;
import utiles.MyConnection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserService implements CRUD <user>{
    @Override
    public void addEntity(user user) {
        String requete= "INSERT INTO `user`(`id`, `password`) VALUES ('"+user.getId()+"','"+user.getPassword()+"')";
        try {
            Statement st = MyConnection.getInstance().getCnx().createStatement();
            st.executeUpdate(requete);
            System.out.println("User added!!");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void updateEntity(user user) {
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement("UPDATE `user` SET `password` = ? WHERE `id` = ?");
            pst.setString(1, user.getPassword());
            pst.setInt(2, user.getId());
            int rowsUpdated = pst.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("User updated!!");
            } else {
                System.out.println("User with id " + user.getId() + " does not exist.");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void deleteEntity(user user) {
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
    public List<user> getAllData() {

            List<user> data = new ArrayList<>();
            String requete = "SELECT * FROM user";
            try {
                Statement st = MyConnection.getInstance().getCnx().createStatement();
                ResultSet rs =st.executeQuery(requete);
                while(rs.next()){
                    user p = new user();
                    p.setId(rs.getInt(1));
                    data.add(p);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

            return data;
        }

    }





