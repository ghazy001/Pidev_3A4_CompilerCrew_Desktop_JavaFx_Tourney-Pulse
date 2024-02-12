package edu.esprit.services;

import edu.esprit.entities.matchs;
import edu.esprit.utils.DataSource;
import edu.esprit.services.InterfaceService;
import edu.esprit.entities.tournois;

import java.sql.*;

public class ServiceMatch implements InterfaceService<matchs> {
    PreparedStatement prepare;
    Connection connection;

    @Override
    public void ajouter(matchs matchs) {
        connection = DataSource.getInsatnce().getConnection();


        String sql = "INSERT INTO `matchs` ( `nom_match`, `date_match`, `duree_match`, `id_tournois`) VALUES (?, ?, ?, ?);";
        try {
            prepare = connection.prepareStatement(sql);

            prepare.setString(1, matchs.getNom_match());
            prepare.setString(2, matchs.getDate_match());
            prepare.setString(3, matchs.getDuree_match());
            prepare.setInt(4, matchs.getId_tournois().getId_tournois());

            prepare.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void modifier(int id, matchs matchs) {
            connection = DataSource.getInsatnce().getConnection();

        String sql = "UPDATE `matchs` SET `nom_match` = ?, `date_match` = ?, `duree_match` = ?, `id_tournois` = ? WHERE `id_match` = ?";

        try {
            prepare = connection.prepareStatement(sql);

            prepare.setString(1, matchs.getNom_match());
            prepare.setString(2, matchs.getDate_match());
            prepare.setString(3, matchs.getDuree_match());
            prepare.setInt(4, matchs.getId_tournois().getId_tournois());
            prepare.setInt(5, id);

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

        String sql = "SELECT m.*, t.nom_tournois FROM `matchs` m JOIN `tournois` t ON m.id_tournois = t.id_tournois";

        try {
            prepare = connection.prepareStatement(sql);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id_match"));
                System.out.println("Nom Match: " + resultSet.getString("nom_match"));
                System.out.println("Date Match: " + resultSet.getString("date_match"));
                System.out.println("Duree Match: " + resultSet.getString("duree_match"));
                System.out.println("Nom Tournois: " + resultSet.getString("nom_tournois"));
            }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }


    }


