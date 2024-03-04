package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class BackOffice implements Initializable {
     @FXML
    Button btnEquipe;

    @FXML
   Button btnAvis;


    @FXML
    Label date,time;

    private boolean stop = false;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayDate();
        updateTime();

        btnAvis.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowAvisPLayer.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) btnAvis.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("GestionAvis");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        btnEquipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayEquipe.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) btnAvis.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("GestionEquipe");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

    }
    //--------------------------------------timeCurrent--------------------------------------
    private  void updateTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if (!stop) {
                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                            String timeNow = sdf.format(new Date());
                            time.setText(timeNow);
                        }
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    //----------------------------dateCuurent------------------------------------
    private void displayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("'Aujourd''hui' EEEE, MMMM dd, yyyy");
        String dateNow = sdf.format(new Date());
        System.out.println(dateNow);
        date.setText(dateNow);
        date.setWrapText(true);
    }

    //---------------------------------------------------------------------------

}
