package edu.esprit.services;

import edu.esprit.entities.matchs;
import edu.esprit.utils.DataSource;
import edu.esprit.services.InterfaceService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
public class ServiceMatch implements InterfaceService<matchs> {
    PreparedStatement prepare;
    Connection connection;

    @Override
    public void ajouter(matchs matchs) {
        connection = DataSource.getInsatnce().getConnection();


        String sql = "INSERT INTO `matchs` ( `date_match`, `duree_match`) VALUES (?, ?);";
        try {
            prepare = connection.prepareStatement(sql);

            prepare.setString(1, matchs.getDate_match());
            prepare.setString(2, matchs.getDuree_match());

            prepare.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void modifier(int id, matchs matchs) {
            connection = DataSource.getInsatnce().getConnection();

            String sql = "UPDATE `matchs` SET `date_match` = ?, `duree_match` = ? WHERE `id_match` = ?";

            try {
                 prepare = connection.prepareStatement(sql);

                prepare.setString(1, matchs.getDate_match());
                prepare.setString(2, matchs.getDuree_match());
                prepare.setInt(3, id);

                prepare.executeUpdate();


                int rowsAffected = prepare.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Match with ID " + id + " modified successfully.");
                } else {
                    System.out.println("Match with ID " + id + " not found or failed to modify.");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }



    }

    @Override
    public void supprimer(int id) {
            connection = DataSource.getInsatnce().getConnection();

            String sql = "DELETE FROM `matchs` WHERE `id_match` = ? ";

            try {
                prepare = connection.prepareStatement(sql);

                prepare.setInt(1, id);

                int rowsAffected = prepare.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Match with ID " + id + " deleted successfully.");
                } else {
                    System.out.println("Match with ID " + id + " not found or failed to delete.");
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }




    @Override
    public void afficher() {
            connection = DataSource.getInsatnce().getConnection();

            String sql = "SELECT * FROM `matchs`";

            try {
                prepare = connection.prepareStatement(sql);


                ResultSet resultSet = prepare.executeQuery();

                while (resultSet.next()) {
                    System.out.println("ID: " + resultSet.getInt("id_match"));
                    System.out.println("Date Match: " + resultSet.getString("date_match"));
                    System.out.println("Duree Match: " + resultSet.getString("duree_match"));
                }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }


    }


