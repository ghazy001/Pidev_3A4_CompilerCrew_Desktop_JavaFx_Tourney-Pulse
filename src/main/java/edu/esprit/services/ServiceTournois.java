package edu.esprit.services;

import edu.esprit.entities.tournois;
import edu.esprit.utils.DataSource;
import edu.esprit.services.InterfaceService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ServiceTournois implements InterfaceService<tournois> {

    PreparedStatement prepare;
    Connection connection;
    @Override
    public void ajouter(tournois tournois) {
        connection = DataSource.getInsatnce().getConnection();


        String sql = "INSERT INTO `tournois` ( `nom_tournois`, `address_tournois`, `nombre_match`, `date_debut`, `date_fin`) VALUES (?, ?, ?, ?, ?);";
        try {
            prepare = connection.prepareStatement(sql);
            prepare.setString(1, tournois.getNom_tournois());
            prepare.setString(2, tournois.getAddress_tournois());
            prepare.setInt(3, tournois.getNombre_match());
            prepare.setString(4, tournois.getDate_debut());
            prepare.setString(5, tournois.getDate_fin());

            prepare.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void modifier(int id, tournois tournois) {
        connection = DataSource.getInsatnce().getConnection();

        String sql = "UPDATE `tournois` SET nom_tournois = ?, address_tournois = ?, nombre_match = ?, date_debut = ?, date_fin = ? WHERE id_tournois = ?";

        try {
             prepare = connection.prepareStatement(sql);

            prepare.setString(1, tournois.getNom_tournois());
            prepare.setString(2, tournois.getAddress_tournois());
            prepare.setInt(3, tournois.getNombre_match());
            prepare.setString(4, tournois.getDate_debut());
            prepare.setString(5, tournois.getDate_fin());
            prepare.setInt(6, id);

            prepare.executeUpdate();


            int rowsAffected = prepare.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Tournois with ID " + id + " modified successfully.");
            } else {
                System.out.println("Tournois with ID " + id + " not found or failed to modify.");
            }

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

            int rowsAffected = prepare.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Tournois with ID " + id + " deleted successfully.");
            } else {
                System.out.println("Tournois with ID " + id + " not found or failed to delete.");
            }

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
                System.out.println("Date Debut: " + resultSet.getString("date_debut"));
                System.out.println("Date Fin: " + resultSet.getString("date_fin"));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    }

