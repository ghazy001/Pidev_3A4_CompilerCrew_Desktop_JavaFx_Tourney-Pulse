package edu.esprit.services;

import edu.esprit.entities.Tournois;
import edu.esprit.utils.DataSource;
import javafx.scene.control.Alert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceTournois implements InterfaceService<Tournois> {

    PreparedStatement prepare;
    Connection connection;
    @Override
    public void ajouter(Tournois tournois) {
        connection = DataSource.getInsatnce().getConnection();


        String sql = "INSERT INTO `tournois` ( `nom_tournois`, `address_tournois`, `nombre_match`, `date_debut`, `date_fin`) VALUES (?, ?, ?, ?, ?);";
        try {
            prepare = connection.prepareStatement(sql);
            prepare.setString(1, tournois.getNom_tournois());
            prepare.setString(2, tournois.getAddress_tournois());
            prepare.setInt(3, tournois.getNombre_match());
            prepare.setDate(4, (Date) tournois.getDate_debut());
            prepare.setDate(5, (Date) tournois.getDate_fin());

            prepare.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void modifier(Tournois tournois) {
        connection = DataSource.getInsatnce().getConnection();

        String sql = "UPDATE `tournois` SET nom_tournois = ?, address_tournois = ?, nombre_match = ?, date_debut = ?, date_fin = ? ";

        try {
             prepare = connection.prepareStatement(sql);

            prepare.setString(1, tournois.getNom_tournois());
            prepare.setString(2, tournois.getAddress_tournois());
            prepare.setInt(3, tournois.getNombre_match());
            prepare.setDate(4, (Date) tournois.getDate_debut());
            prepare.setDate(5, (Date) tournois.getDate_fin());

            prepare.executeUpdate();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Gestion Des Tournois");

            alert.setHeaderText("Gestion Des Tournois");
            alert.setContentText("Tournois Modifier!");
            alert.showAndWait();


        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void supprimer(int id) {
        connection = DataSource.getInsatnce().getConnection();

        String sql = "DELETE FROM `tournois` WHERE `id_tournois` = ? ";

        try {
            prepare = connection.prepareStatement(sql);

            prepare.setInt(1, id);

            prepare.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Gestion Des Tournois");

            alert.setHeaderText("Gestion Des Tournois");
            alert.setContentText("Tournois supprimer!");
            alert.showAndWait();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    @Override
    public void afficher() {
        connection = DataSource.getInsatnce().getConnection();

        String sql = "SELECT * FROM `tournois`";

        try {
            prepare = connection.prepareStatement(sql);


            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id_tournois"));
                System.out.println("Nom Tournois : " + resultSet.getString("nom_tournois"));
                System.out.println("Address Tournois: " + resultSet.getString("address_tournois"));
                System.out.println("Nombre Match: " + resultSet.getInt("nombre_match"));
                System.out.println("Date Debut: " + resultSet.getDate("date_debut"));
                System.out.println("Date Fin: " + resultSet.getDate("date_fin"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Tournois getOneById(int id)  {
        return null;
    }

    @Override
    public List<Tournois> getAll(){
        List<Tournois> tournoisList = new ArrayList<>();
        connection = DataSource.getInsatnce().getConnection();

        String sql = "SELECT * FROM `tournois`";

        try {
            Statement prepare = connection.createStatement();


            ResultSet resultSet = prepare.executeQuery(sql);

            while (resultSet.next()) {
                int id = resultSet.getInt("id_tournois");
                String nomTournois = resultSet.getString("nom_tournois");
                String addressTournois = resultSet.getString("address_tournois");
                int nombreMatch =resultSet.getInt("nombre_match");
                Date dateDebut = resultSet.getDate("date_debut");
                Date dateFin = resultSet.getDate("date_fin");
                Tournois t = new Tournois();
                t.setId_tournois(id);
                t.setNom_tournoi(nomTournois);
                t.setAddress_tournois(addressTournois);
                t.setNombre_match(nombreMatch);
                t.setDate_debut(dateDebut);
                t.setDate_fin(dateFin);
                tournoisList.add(t);
            }

        } catch (SQLException ex) {
            System.out.print(ex.getMessage());
        }
        return tournoisList;
    }


}

