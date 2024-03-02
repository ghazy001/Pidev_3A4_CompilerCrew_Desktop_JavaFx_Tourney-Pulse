package edu.esprit.controller;

import edu.esprit.utils.DataSource;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Statistique implements Initializable {

    @FXML
    private Button Bnavigatematch;

    @FXML
    private Button Bnavigatetournois;

    @FXML
    private BarChart<?, ?> CHmatch;

    @FXML
    private BarChart<?, ?> CHtournois;

    @FXML
    private Label LLnbrm;

    @FXML
    private Label LLnbrt;

    PreparedStatement prepare;
    Connection connection;
    private ResultSet result;

    public void nombreTournois(){

        String sql = "SELECT COUNT(`id_tournois`) FROM `tournois`";

        connection = DataSource.getInsatnce().getConnection();
        int countAC = 0;
        try{
            prepare = connection.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                countAC = result.getInt("COUNT(`id_tournois`)");
            }

            LLnbrt.setText(String.valueOf(countAC));

        }catch(Exception e){e.printStackTrace();}

    }

    public void nombreMatch(){

        String sql = "SELECT COUNT(`id_match`) FROM `match`";

        connection = DataSource.getInsatnce().getConnection();
        int countAC = 0;
        try{
            prepare = connection.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                countAC = result.getInt("COUNT(`id_match`)");
            }

            LLnbrm.setText(String.valueOf(countAC));

        }catch(Exception e){e.printStackTrace();}

    }

    public void tournoisChart(){

        CHtournois.getData().clear();

        String sql = "SELECT nom_tournois, SUM(nombre_match) FROM `tournois` GROUP BY nom_tournois ORDER BY TIMESTAMP(nom_tournois)";

        connection = DataSource.getInsatnce().getConnection();

        try{
            XYChart.Series chart = new XYChart.Series();

            prepare = connection.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            CHtournois.getData().add(chart);

        }catch(Exception e){e.printStackTrace();}
    }

    public void matchChart(){

        CHmatch.getData().clear();

        String sql = "SELECT date_match, COUNT(id_match) FROM `match` GROUP BY date_match ORDER BY TIMESTAMP(date_match)";

        connection = DataSource.getInsatnce().getConnection();

        try{
            XYChart.Series chart = new XYChart.Series();

            prepare = connection.prepareStatement(sql);
            result = prepare.executeQuery();

            while(result.next()){
                chart.getData().add(new XYChart.Data(result.getString(1), result.getInt(2)));
            }

            CHmatch.getData().add(chart);

        }catch(Exception e){e.printStackTrace();}
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Bnavigatematch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/AfficherMatch.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root); // Adjust width and height as needed

                    // Get the current stage
                    Stage stage = (Stage) LLnbrt.getScene().getWindow();

                    // Set the new scene to the stage
                    stage.setScene(scene);


                } catch (IOException var4) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Sorry");
                    alert.setTitle("Error");
                    alert.show();
                }

            }
        });

        Bnavigatetournois.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/AfficherTournois.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root); // Adjust width and height as needed

                    // Get the current stage
                    Stage stage = (Stage) LLnbrt.getScene().getWindow();

                    // Set the new scene to the stage
                    stage.setScene(scene);


                } catch (IOException var4) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Sorry");
                    alert.setTitle("Error");
                    alert.show();
                }

            }
        });

        nombreTournois();
        nombreMatch();
        tournoisChart();
        matchChart();
    }



}
