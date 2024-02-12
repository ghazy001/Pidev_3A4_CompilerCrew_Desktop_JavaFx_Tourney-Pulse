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


            String selectJoueurSql = "SELECT id_joueur, nom FROM joueur WHERE id_joueur = ?";

            try (PreparedStatement selectJoueurStatement = connection.prepareStatement(selectJoueurSql)) {
                selectJoueurStatement.setInt(1, avisJoueur.getIdJoueur());

                try (ResultSet joueurResultSet = selectJoueurStatement.executeQuery()) {
                    if (joueurResultSet.next()) {
                        int idJoueur = joueurResultSet.getInt("id_joueur");
                        String nomJoueur = joueurResultSet.getString("nom");


                        String insertAvisSql = "INSERT INTO AvisJoueur (idJoueur, nom_joueur, note, commaintre) VALUES (?, ?, ?, ?)";

                        try (PreparedStatement insertAvisStatement = connection.prepareStatement(insertAvisSql)) {
                            insertAvisStatement.setInt(1, idJoueur);
                            insertAvisStatement.setString(2, nomJoueur);
                            insertAvisStatement.setFloat(3, avisJoueur.getNote());
                            insertAvisStatement.setString(4, avisJoueur.getCommentaire());

                            insertAvisStatement.executeUpdate();

                            System.out.println("Avis ajouté avec succès.");
                        }
                    } else {
                        System.out.println("Aucun joueur trouvé avec le nom " + avisJoueur.getNom_joueur());
                    }
                }
            }
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
