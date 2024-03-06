package tn.Esprit.services;

import tn.Esprit.iservices.IService;
import tn.Esprit.models.MarketPlace;
import tn.Esprit.models.User;
import tn.Esprit.utils.Database;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.ArrayList;


public class ServiceMarketPlace implements IService<MarketPlace> {

    private Connection connection;

    public ServiceMarketPlace() {
        connection = Database.getInstance().getConnection();
    }

    @Override
    public void add(MarketPlace marketPlace) {
        String query = "INSERT INTO marketplace (price,quantity,prodName, prodDescription, DateProd, image) VALUES (?, ?, ?, ?,?,?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, marketPlace.getPrice());
            preparedStatement.setInt(2, marketPlace.getQuantity());
            preparedStatement.setString(3, marketPlace.getProdName());
            preparedStatement.setString(4, marketPlace.getProdDescription());
            preparedStatement.setTimestamp(5, marketPlace.getDateProd());

            // Set the image
            if (marketPlace.getImage() != null) {
                preparedStatement.setBinaryStream(6, new ByteArrayInputStream(marketPlace.getImage()));
            } else {
                preparedStatement.setNull(6, Types.BLOB);
            }

            preparedStatement.executeUpdate();

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
        }
    }

    @Override
    public ArrayList<MarketPlace> getAll() {
        String req = "SELECT * FROM marketplace";
        ArrayList<MarketPlace> marketPlaces = new ArrayList<>();
        try {
            Statement st = connection.createStatement();
            ResultSet resultSet = st.executeQuery(req);

            while (resultSet.next()) {
                marketPlaces.add(new MarketPlace(resultSet.getInt(1),
                        resultSet.getFloat(2),
                        resultSet.getInt(3),
                        resultSet.getString(4),
                        resultSet.getString(5),
                        resultSet.getTimestamp(6),
                        resultSet.getBytes(7)));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return marketPlaces;
    }

    @Override
    public void update(MarketPlace marketPlace) {
        String query = "UPDATE marketplace SET Price=?, Quantity=?, ProdName=?, ProdDescription=?, DateProd=?, image=? WHERE idProd=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setFloat(1, marketPlace.getPrice());
            preparedStatement.setInt(2, marketPlace.getQuantity());
            preparedStatement.setString(3, marketPlace.getProdName());
            preparedStatement.setString(4, marketPlace.getProdDescription());
            preparedStatement.setTimestamp(5, marketPlace.getDateProd());

            // Set the image
            if (marketPlace.getImage() != null) {
                preparedStatement.setBinaryStream(6, new ByteArrayInputStream(marketPlace.getImage()));
            } else {
                preparedStatement.setNull(6, Types.BLOB);
            }

            preparedStatement.setInt(7, marketPlace.getIdProd());

            int rows = preparedStatement.executeUpdate();
            if (rows > 0) {
                System.out.println("updated");
            } else {
                System.out.println("not updated");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
  /*  public MarketPlace getById(int id) {
        String query = "SELECT * FROM marketplace WHERE idProd=?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                return new MarketPlace(resultSet.getInt(1),
                        resultSet.getString(2),
                        resultSet.getString(3),
                        resultSet.getTimestamp(4));

            } else {
                // marketPlace not found, handle this case
                System.out.println("MarketPlace with ID " + id + " not found.");
                return null; // Or throw an exception to indicate the marketPlace was not found
            }

        } catch (SQLException exception) {
            System.out.println(exception.getMessage());
            return null;
        }
    }*/

    @Override
    public boolean delete(MarketPlace marketplace) {
        String query = "DELETE FROM marketPlace WHERE idProd=?";
        int rows = 0;
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, marketplace.getIdProd());

            rows = preparedStatement.executeUpdate();
            if (rows > 0) return true;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return false;
    }

    @Override
    public User readById(int idDM) {
        return null;
    }
}
