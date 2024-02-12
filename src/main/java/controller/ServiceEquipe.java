package controller;

import entities.equipe;
import esprit.project.tools.MyDB;
import service.InterfaceServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceEquipe implements InterfaceServices<equipe> {

    PreparedStatement prepare;
    Connection connection;




    @Override
    public void ajouter(equipe e) {
        connection = MyDB.getInsatnce().getConnection();


        String sql = "INSERT INTO `equipe` ( `nom_equipe`, `nom_joueur`, `id_joueur`) VALUES (?, ?, (SELECT id_joueur FROM joueur WHERE nom = ?));";
        try {
            prepare = connection.prepareStatement(sql);

            prepare.setString(1, e.getNom());
            prepare.setString(2, e.getNom_joueur());
            prepare.setString(3, e.getNom_joueur());

            prepare.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }






    @Override
    public void modifier(int id,equipe updatedEquipe) {

        connection = MyDB.getInsatnce().getConnection();
        String sql = "UPDATE `equipe` SET `nom_equipe` = ?, `nom_joueur1` = ?, `id_joueur` = (SELECT id_joueur FROM joueur WHERE nom = ?) WHERE `id_equipe` = ?;";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, updatedEquipe.getNom());
            preparedStatement.setString(2, updatedEquipe.getNom_joueur());
            preparedStatement.setString(3, updatedEquipe.getNom_joueur());
            preparedStatement.setInt(4, id);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Equipe with ID " + id + " modified successfully.");
            } else {
                System.out.println("Equipe with ID " + id + " not found or failed to modify.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void supprimer(int id) {
        connection = MyDB.getInsatnce().getConnection();
        String sql = "DELETE FROM `equipe` WHERE `id_equipe` = ?;";

        try {
            prepare = connection.prepareStatement(sql);
            prepare.setInt(1, id);

            int rowsAffected = prepare.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Equipe with ID " + id + " deleted successfully.");
            } else {
                System.out.println("Equipe with ID " + id + " not found or failed to delete.");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void afficher() {


        connection = MyDB.getInsatnce().getConnection();
        String sql = "SELECT * FROM equipe;";
        try {
            prepare = connection.prepareStatement(sql);
            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()) {
                // Retrieve and print equipe information
                System.out.println("ID: " + resultSet.getInt("id_equipe"));
                System.out.println("Nom Equipe: " + resultSet.getString("nom_equipe"));
                System.out.println("Nom Joueur1: " + resultSet.getString("nom_joueur"));
                System.out.println("ID Joueur: " + resultSet.getInt("id_joueur"));
                System.out.println("-------------------------");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();

        }
    }
}