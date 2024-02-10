package controller;

import entities.AvisJoueur;
import esprit.project.tools.MyDB;
import service.InterfaceServices;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceAvisJoueur implements InterfaceServices<AvisJoueur> {


    PreparedStatement prepare;
    Connection connection;
    @Override
    public void ajouter(AvisJoueur avisJoueur) {


        try {
            connection = MyDB.getInsatnce().getConnection();
            String sql = "INSERT INTO AvisJoueur (commaintre, note, idJoueur, nom_joueur) VALUES (?, ?, (SELECT id_joueur FROM joueur WHERE nom = ?), ?)";

            prepare = connection.prepareStatement(sql);
            prepare.setString(1, avisJoueur.getCommentaire());
            prepare.setFloat(2, avisJoueur.getNote());
            prepare.setString(3, avisJoueur.getNom_joueur());
            prepare.setString(4, avisJoueur.getNom_joueur());

            prepare.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void modifier(int id, AvisJoueur avisJoueur) {
        try {
            connection = MyDB.getInsatnce().getConnection();
            String sql = "UPDATE AvisJoueur SET commaintre = ?, note = ?, nom_joueur = ? WHERE idAvis = ?";

            prepare = connection.prepareStatement(sql);
            prepare.setString(1, avisJoueur.getCommentaire());
            prepare.setFloat(2, avisJoueur.getNote());
            prepare.setString(3, avisJoueur.getNom_joueur());
            prepare.setInt(4, id);

            prepare.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void supprimer(int id) {
        try {
            connection = MyDB.getInsatnce().getConnection();
            String sql = "DELETE FROM AvisJoueur WHERE idAvis = ?;";

            prepare = connection.prepareStatement(sql);
            prepare.setInt(1, id);

            int rowsAffected = prepare.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("AvisJoueur with ID " + id + " deleted successfully.");
            } else {
                System.out.println("AvisJoueur with ID " + id + " not found or failed to delete.");
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void afficher() {


            try {
                connection = MyDB.getInsatnce().getConnection();
                String sql = "SELECT * FROM AvisJoueur;";

                prepare = connection.prepareStatement(sql);
                ResultSet resultSet = prepare.executeQuery();

                while (resultSet.next()) {
                    // Retrieve and print AvisJoueur information
                    System.out.println("ID Avis: " + resultSet.getInt("idAvis"));
                    System.out.println("Commentaire: " + resultSet.getString("commaintre"));
                    System.out.println("Note: " + resultSet.getFloat("note"));
                    System.out.println("ID Joueur: " + resultSet.getInt("idJoueur"));
                    System.out.println("Nom Joueur: " + resultSet.getString("nom_joueur"));
                    System.out.println("-------------------------");
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }

    }
}
