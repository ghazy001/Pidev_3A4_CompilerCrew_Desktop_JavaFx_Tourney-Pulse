package tn.Esprit.services;

import tn.Esprit.iservices.IService;
import tn.Esprit.models.Avis;
import tn.Esprit.models.User;
import tn.Esprit.utils.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ServiceAvis implements IService<Avis> {

    private Connection connection;

    public ServiceAvis() {
        connection = Database.getInstance().getConnection();
    }

    @Override
    public void add(Avis avis) {
        String query = "INSERT INTO avis (idAvis,idProd, dateAvis, note) VALUES (?,?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, avis.getIdAvis());
            preparedStatement.setInt(2, avis.getIdProd());
            preparedStatement.setTimestamp(3, avis.getdateAvis());
            preparedStatement.setInt(4, avis.getNote());
            preparedStatement.executeUpdate();
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public ArrayList<Avis> getAll() {
        String req = "SELECT * FROM avis";
        ArrayList<Avis> avis = new ArrayList<>();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(req);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                avis.add(new Avis(
                        resultSet.getInt("idAvis"),
                        resultSet.getInt("idProd"),
                        resultSet.getTimestamp("dateAvis"),
                        resultSet.getInt("note")
                ));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return avis;
    }

    @Override
    public void update(Avis avis) {
        String query = "UPDATE avis SET dateAvis=?, note=? WHERE idAvis=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setTimestamp(1, avis.getdateAvis());
            preparedStatement.setInt(2, avis.getNote());
            preparedStatement.setInt(3, avis.getIdAvis());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean delete(Avis avis) {
        String query = "DELETE FROM avis WHERE idAvis=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, avis.getIdAvis());
            int rows = preparedStatement.executeUpdate();
            return rows > 0;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            return false;
        }
    }

    @Override
    public User readById(int idDM) {
        return null;
    }

    public Avis getById(int id) {
        String query = "SELECT * FROM avis WHERE idAvis=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return new Avis(
                        resultSet.getInt("idAvis"),
                        resultSet.getInt("idProd"),
                        resultSet.getTimestamp("dateAvis"),
                        resultSet.getInt("note")
                );
            }
        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }
}
