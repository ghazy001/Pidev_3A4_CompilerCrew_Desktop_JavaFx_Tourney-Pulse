package controller;

import esprit.project.tools.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.chart.StackedBarChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

/*
*
   Author: @Ghazi saoudi
*
*/


public class StaticAvisController implements Initializable {

    @FXML
     private AnchorPane Mypane;
    @FXML
     Pane pane;
    @FXML
    StackedBarChart bar;
    @FXML
    Label NbrUser,NbrUser2,NbrUser1;


    // --------------- nbr total user ------------------
    private int fetchData() {
        int numberOfUsers = 0;

        String sql = "SELECT COUNT(*) FROM USER";
        try (PreparedStatement preparedStatement = MyDB.getInsatnce().getConnection().prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                numberOfUsers = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return numberOfUsers;
    }

    // -------------------- nbr total joueurs -----------------------------------

    private int fetchDataJoueur() {
        int numberOfUsers = 0;

        String sql = "SELECT COUNT(*) FROM USER WHERE id_equipe IS NOT NULL ";
        try (PreparedStatement preparedStatement = MyDB.getInsatnce().getConnection().prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                numberOfUsers = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return numberOfUsers;
    }

    // -------------------nbr tota equipe------------
    private int fetchDataEquipe() {
        int numberOfUsers = 0;

        String sql = "SELECT COUNT(*) FROM equipe";
        try (PreparedStatement preparedStatement = MyDB.getInsatnce().getConnection().prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            if (resultSet.next()) {
                numberOfUsers = resultSet.getInt(1);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return numberOfUsers;
    }




    //---------------------------------------
    private ObservableList<StackedBarChart.Series<String, Number>> fetchDataForBarChart() {
        ObservableList<StackedBarChart.Series<String, Number>> barChartData = FXCollections.observableArrayList();

        String sql = "SELECT avis.idjoueur, COUNT(*), user.name " +
                "FROM avisjoueur avis " +
                "JOIN user ON avis.idjoueur = user.id " +
                "GROUP BY avis.idjoueur, user.name";
        try (PreparedStatement preparedStatement = MyDB.getInsatnce().getConnection().prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int idjoueur = resultSet.getInt("idjoueur");
                String playerName = resultSet.getString("name");
                long count = resultSet.getLong(2); // Use index 2 for the count column

                StackedBarChart.Series<String, Number> series = new StackedBarChart.Series<>();
                series.setName(playerName + " (idjoueur " + idjoueur + ")");
                series.getData().add(new StackedBarChart.Data<>(playerName, count));

                barChartData.add(series);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return barChartData;
    }

//pie chart function
    private ObservableList<PieChart.Data> fetchDataFromDatabase() {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();

        String sql = "SELECT avis.idjoueur, COUNT(*), user.name " +
                "FROM avisjoueur avis " +
                "JOIN user ON avis.idjoueur = user.id " +
                "GROUP BY avis.idjoueur, user.name";
        try (PreparedStatement preparedStatement = MyDB.getInsatnce().getConnection().prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int idjoueur = resultSet.getInt("idjoueur");
                String playerName = resultSet.getString("name");
                long count = resultSet.getLong(2); // Use index 2 for the count column

                PieChart.Data data = new PieChart.Data(playerName + " (idjoueur " + idjoueur + ")", count);
                pieChartData.add(data);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }

        return pieChartData;
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        ObservableList<PieChart.Data> pieChartData = fetchDataFromDatabase();

        PieChart pieChart = new PieChart(pieChartData);
        //pieChart.setTitle("Statics");

        pieChart.setMinSize(100,100);
        pieChart.setMaxSize(300, 300);


        pane.getChildren().add(pieChart);

        //--------------- return to home -------------------------------
        for (PieChart.Data data : pieChartData) {
            data.getNode().setOnMouseClicked(event -> {
                navigateToAvisTable();
            });
        }


        ObservableList<StackedBarChart.Series<String, Number>> barChartData = fetchDataForBarChart();

        bar.getData().addAll(barChartData);
        //bar.setTitle();


        NbrUser.setText(String.valueOf(fetchData()));
        NbrUser2.setText(String.valueOf(fetchDataJoueur()));
        NbrUser1.setText(String.valueOf(fetchDataEquipe()));







    }
    private void navigateToAvisTable() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowAvisPLayer.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = new Stage(); // Assuming a new stage for avis table
            stage.setScene(new Scene(root));
            stage.setTitle("Avis Table");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
