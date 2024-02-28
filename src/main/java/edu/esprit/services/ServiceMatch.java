package edu.esprit.services;

import edu.esprit.entities.Equipe;
import edu.esprit.entities.Matchs;
import edu.esprit.entities.Tournois;
import edu.esprit.utils.DataSource;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceMatch implements InterfaceService<Matchs> {
    PreparedStatement prepare;
    Connection connection;

    @Override
    public void ajouter(Matchs matchs) {
        connection = DataSource.getInsatnce().getConnection();


        String sql = "INSERT INTO `match` ( `nom_match`, `date_match`, `duree_match`, `id_tournois` , `id_equipe1`, `id_equipe2`) VALUES (?, ?, ?, ?, ?, ?);";
        try {
            prepare = connection.prepareStatement(sql);

            prepare.setString(1, matchs.getNom_match());
            prepare.setDate(2, (Date) matchs.getDate_match());
            prepare.setString(3, matchs.getDuree_match());
            prepare.setInt(4, matchs.getTournois().getId_tournois());
            prepare.setInt(5, matchs.getEquipe().getId_equipe());
            prepare.setInt(6, matchs.getEquipe1().getId_equipe());

            prepare.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void modifier(Matchs matchs) {
            connection = DataSource.getInsatnce().getConnection();

        String sql = "UPDATE `match` SET `nom_match` = ?, `date_match` = ?, `duree_match` = ?, `id_tournois` = ?, `id_equipe1` = ?, `id_equipe2` = ? WHERE `id_match` = ? ";

        try {
            prepare = connection.prepareStatement(sql);

            prepare.setString(1, matchs.getNom_match());
            prepare.setDate(2, (Date) matchs.getDate_match());
            prepare.setString(3, matchs.getDuree_match());
            prepare.setInt(4, matchs.getTournois().getId_tournois());
            prepare.setInt(5, matchs.getEquipe().getId_equipe());
            prepare.setInt(6, matchs.getEquipe1().getId_equipe());
            prepare.setInt(7, matchs.getId_match());


            prepare.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Gestion Des Matchs");

            alert.setHeaderText("Gestion Des Matchs");
            alert.setContentText("Match Modifier!");
            alert.showAndWait();

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

                prepare.executeUpdate();

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
    public Matchs getOneById(int id)  {
        return null;
    }

    @Override
    public List<Matchs> getAll()  {
        List<Matchs> matchsList = new ArrayList<>();
        connection = DataSource.getInsatnce().getConnection();

        String sql = "SELECT m.*, t.nom_tournois, e1.nom AS nom1, e2.nom AS nom2 " + // Correction des alias pour les noms des équipes
                "FROM `match` m " +
                "JOIN `tournois` t ON m.id_tournois = t.id_tournois " +
                "JOIN `equipe` e1 ON m.id_equipe1 = e1.id " +
                "JOIN `equipe` e2 ON m.id_equipe2 = e2.id";

        try {


            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id_match");
                String nomMatch = resultSet.getString("nom_match");
                Date dateMatch = resultSet.getDate("date_match");
                String dureeMatch = resultSet.getString("duree_match");
                int idTournois = resultSet.getInt("id_tournois");
                String nomTournois = resultSet.getString("nom_tournois");
                int idEquipe1 = resultSet.getInt("id_equipe1");
                String nomEquipe1 = resultSet.getString("nom1");
                int idEquipe2 = resultSet.getInt("id_equipe2");
                String nomEquipe2 = resultSet.getString("nom2");

                Matchs m = new Matchs();
                m.setId_match(id);
                m.setNom_match(nomMatch);
                m.setDate_match(dateMatch);
                m.setDuree_match(dureeMatch);

                Tournois tour = new Tournois();
                tour.setId_tournois(idTournois);
                tour.setNom_tournoi(nomTournois);
                m.setTournois(tour);

                Equipe equ = new Equipe();
                equ.setId_equipe(idEquipe1);
                equ.setNom_equipe(nomEquipe1);
                m.setEquipe(equ);

                Equipe equ1 = new Equipe();
                equ1.setId_equipe(idEquipe2);
                equ1.setNom_equipe(nomEquipe2);
                m.setEquipe1(equ1);

                matchsList.add(m);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return matchsList;
    }




}


