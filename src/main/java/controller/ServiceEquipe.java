package controller;

import entities.equipe;
import esprit.project.tools.MyDB;
import service.InterfaceServices;

import java.sql.*;
import java.util.Scanner;

public class ServiceEquipe implements InterfaceServices<equipe> {

    PreparedStatement prepare;
    Connection connection;
    Connection cnx;

    @Override
    public void ajouter(equipe equipe) {
        try {
            connection = MyDB.getInsatnce().getConnection();

            String selectJoueurSql = "SELECT id_joueur, nom FROM joueur WHERE id_joueur = ?";

            try (PreparedStatement selectJoueurStatement = connection.prepareStatement(selectJoueurSql)) {
                selectJoueurStatement.setInt(1, equipe.getId_joueur());

                try (ResultSet joueurResultSet = selectJoueurStatement.executeQuery()) {
                    if (joueurResultSet.next()) {
                        int idJoueur = joueurResultSet.getInt("id_joueur");
                        String nomJoueur = joueurResultSet.getString("nom");

                        String insertEquipeSql = "INSERT INTO equipe (nom_equipe, id_joueur, nom_joueur) VALUES (?, ?, ?)";

                        try (PreparedStatement insertEquipeStatement = connection.prepareStatement(insertEquipeSql)) {
                            insertEquipeStatement.setString(1, equipe.getNom());
                            insertEquipeStatement.setInt(2, idJoueur);
                            insertEquipeStatement.setString(3, nomJoueur);

                            insertEquipeStatement.executeUpdate();

                            System.out.println("Équipe ajoutée avec succès.");
                        }
                    } else {
                        System.out.println("Aucun joueur trouvé avec l'ID " + equipe.getId_joueur());
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void modifier(int id, equipe equipe) {
        try {
            connection = MyDB.getInsatnce().getConnection();

            String selectJoueurSql = "SELECT id_joueur, nom FROM joueur WHERE id_joueur = ?";

            try (PreparedStatement selectJoueurStatement = connection.prepareStatement(selectJoueurSql)) {
                selectJoueurStatement.setInt(1, equipe.getId_joueur());

                try (ResultSet joueurResultSet = selectJoueurStatement.executeQuery()) {
                    if (joueurResultSet.next()) {
                        int idJoueur = joueurResultSet.getInt("id_joueur");
                        String nomJoueur = joueurResultSet.getString("nom");

                        String updateEquipeSql = "UPDATE equipe SET nom_equipe = ?, id_joueur = ?, nom_joueur = ? WHERE id_equipe = ?";

                        try (PreparedStatement updateEquipeStatement = connection.prepareStatement(updateEquipeSql)) {
                            updateEquipeStatement.setString(1, equipe.getNom());
                            updateEquipeStatement.setInt(2, idJoueur);
                            updateEquipeStatement.setString(3, nomJoueur);
                            updateEquipeStatement.setInt(4, id);

                            updateEquipeStatement.executeUpdate();

                            System.out.println("Équipe modifiée avec succès.");
                        }
                    } else {
                        System.out.println("Aucun joueur trouvé avec l'ID " + equipe.getId_joueur());
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


/*
    @Override
    public void ajouter(equipe equipe) {
        try (Connection connection = MyDB.getInsatnce().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id_joueur, nom FROM joueur;")) {

            System.out.println("Liste des joueurs existants :");

            while (resultSet.next()) {
                int idJoueur = resultSet.getInt("id_joueur");
                String nomJoueur = resultSet.getString("nom");

                System.out.println("ID: " + idJoueur + ", Nom: " + nomJoueur);
            }
             int nbr =0;
            while (nbr <4 ) {
            // Demander à l'utilisateur de saisir l'ID du joueur
            Scanner scanner = new Scanner(System.in);
            System.out.print("Saisissez l'ID du joueur sélectionné : ");
            int idJoueurSelectionne = scanner.nextInt();


                String sql = "INSERT INTO equipe (nom_equipe, id_joueur, nom_joueur) VALUES (?, ?, (SELECT nom FROM joueur WHERE id_joueur = ?));";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                    preparedStatement.setString(1, equipe.getNom());
                    preparedStatement.setInt(2, idJoueurSelectionne);
                    preparedStatement.setInt(3, idJoueurSelectionne);

                    preparedStatement.executeUpdate();
                    nbr ++;

                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

*/

    /*

    @Override
    public void modifier(int id, equipe equipe) {
        try (Connection connection = MyDB.getInsatnce().getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery("SELECT id_joueur, nom FROM joueur;")) {

            System.out.println("Liste des joueurs existants :");

            while (resultSet.next()) {
                int idJoueur = resultSet.getInt("id_joueur");
                String nomJoueur = resultSet.getString("nom");

                System.out.println("ID: " + idJoueur + ", Nom: " + nomJoueur);
            }


                // Demander à l'utilisateur de saisir l'ID du joueur
                Scanner scanner = new Scanner(System.in);
                System.out.print("Saisissez l'ID du joueur sélectionné : ");
                int idJoueurSelectionne = scanner.nextInt();

                // Demander à l'utilisateur de saisir le nouveau nom de l'équipe
                System.out.print("Saisissez le nouveau nom de l'équipe : ");
                String nouveauNomEquipe = scanner.next();

                String sql = "UPDATE equipe SET nom_equipe = ?, id_joueur = ?, nom_joueur = (SELECT nom FROM joueur WHERE id_joueur = ?) WHERE id_equipe = ?;";

                try (PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

                    preparedStatement.setString(1, nouveauNomEquipe);
                    preparedStatement.setInt(2, idJoueurSelectionne);
                    preparedStatement.setInt(3, idJoueurSelectionne);
                    preparedStatement.setInt(4, id);

                    preparedStatement.executeUpdate();


                } catch (SQLException ex) {
                    ex.printStackTrace();
                }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
*/

    @Override
    public void supprimer(int id) {
        try {
            connection = MyDB.getInsatnce().getConnection();

            String deleteEquipeSql = "DELETE FROM equipe WHERE id_equipe = ?";

            try (PreparedStatement deleteEquipeStatement = connection.prepareStatement(deleteEquipeSql)) {
                deleteEquipeStatement.setInt(1, id);

                int rowsAffected = deleteEquipeStatement.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Équipe supprimée avec succès.");
                } else {
                    System.out.println("Aucune équipe trouvée avec l'ID " + id);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void afficher() {
        try {
            connection = MyDB.getInsatnce().getConnection();

            String selectEquipesSql = "SELECT id_equipe, nom_equipe, id_joueur, nom_joueur FROM equipe";

            try (PreparedStatement selectEquipesStatement = connection.prepareStatement(selectEquipesSql);
                 ResultSet equipeResultSet = selectEquipesStatement.executeQuery()) {

                while (equipeResultSet.next()) {
                    int idEquipe = equipeResultSet.getInt("id_equipe");
                    String nomEquipe = equipeResultSet.getString("nom_equipe");
                    int idJoueur = equipeResultSet.getInt("id_joueur");
                    String nomJoueur = equipeResultSet.getString("nom_joueur");

                    System.out.println("ID équipe: " + idEquipe +
                            ", Nom équipe: " + nomEquipe +
                            ", Nom joueur: " + nomJoueur);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }


    /*

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

 */
}