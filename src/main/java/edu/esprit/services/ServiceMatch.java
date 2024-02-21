package edu.esprit.services;

import edu.esprit.entities.Matchs;
import edu.esprit.entities.Tournois;
import edu.esprit.utils.DataSource;
import edu.esprit.services.ServiceTournois;
import javafx.scene.control.Alert;


import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class ServiceMatch implements InterfaceService<Matchs> {
    PreparedStatement prepare;
    Connection connection;

    @Override
    public void ajouter(Matchs matchs) {
        connection = DataSource.getInsatnce().getConnection();


        String sql = "INSERT INTO `match` ( `nom_match`, `date_match`, `duree_match`, `id_tournois`) VALUES (?, ?, ?, ?);";
        try {
            prepare = connection.prepareStatement(sql);

            prepare.setString(1, matchs.getNom_match());
            prepare.setDate(2, (Date) matchs.getDate_match());
            prepare.setString(3, matchs.getDuree_match());
            prepare.setInt(4, matchs.getTournois().getId_tournois());

            prepare.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void modifier(int id, Matchs matchs) {
            connection = DataSource.getInsatnce().getConnection();

        String sql = "UPDATE `match` SET `nom_match` = ?, `date_match` = ?, `duree_match` = ?, `id_tournois` = ? WHERE `id_match` = ?";

        try {
            prepare = connection.prepareStatement(sql);

            prepare.setString(1, matchs.getNom_match());
            prepare.setDate(2, (Date) matchs.getDate_match());
            prepare.setString(3, matchs.getDuree_match());
            prepare.setInt(4, matchs.getTournois().getId_tournois());
            prepare.setInt(5, id);

            prepare.executeUpdate();


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Gestion Des Matchs");

            alert.setHeaderText("Gestion Des Matchs");
            alert.setContentText("Match Modifier!");

            } catch (SQLException ex) {
                ex.printStackTrace();
            }



    }

    @Override
    public void supprimer(int id) {
            connection = DataSource.getInsatnce().getConnection();

            String sql = "DELETE FROM `match` WHERE `id_match` = ? ";

            try {
                prepare = connection.prepareStatement(sql);

                prepare.setInt(1, id);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Gestion Des Matchs");

                alert.setHeaderText("Gestion Des Matchs");
                alert.setContentText("Matchs supprimer!");
                alert.showAndWait();

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }




    @Override
    public void afficher() {
            connection = DataSource.getInsatnce().getConnection();

        String sql = "SELECT m.*, t.nom_tournois FROM `match` m JOIN `tournois` t ON m.id_tournois = t.id_tournois";

        try {
            prepare = connection.prepareStatement(sql);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id_match"));
                System.out.println("Nom Match: " + resultSet.getString("nom_match"));
                System.out.println("Date Match: " + resultSet.getDate("date_match"));
                System.out.println("Duree Match: " + resultSet.getString("duree_match"));
                System.out.println("Nom Tournois: " + resultSet.getString("nom_tournois"));
            }

            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

    @Override
    public Matchs getOneById(int id) throws SQLException {
        return null;
    }

    @Override
    public Set<Matchs> getAll() throws SQLException {
        Set<Matchs> match = new HashSet<>();
        connection = DataSource.getInsatnce().getConnection();

        String sql = "SELECT m.*, t.nom_tournois FROM `match` m JOIN `tournois` t ON m.tournois = t.id_tournois";

        try {
            prepare = connection.prepareStatement(sql);

            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_match");
                String nomMatch = resultSet.getString("nom_match");
                Date dateMatch = resultSet.getDate("date_match");
                String dureeMatch = resultSet.getString("duree_match");
                String nomTournois = resultSet.getString("nom_tournois");
                Matchs m = new Matchs();
                m.setId_match(id);
                m.setNom_match(nomMatch);
                m.setDate_match(dateMatch);
                m.setDuree_match(dureeMatch);
                Tournois tour = new Tournois();
                tour.setNom_tournoi(nomTournois);
                m.setTournois(tour);
                match.add(m);
            }

        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
        return null;
    }




}


