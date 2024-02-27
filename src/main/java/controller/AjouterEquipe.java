package controller;

import entities.Equipe;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.ServiceEquipe;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.sql.*;

/*
*
   Author: Ghazi saoudi
*
*/



public class AjouterEquipe implements Initializable {

    @FXML
    ImageView image;
    @FXML
    TextField nomequipe;
    @FXML
    DatePicker date;
    @FXML
    Button uplaod_pic;
    @FXML
    Button save;
    @FXML
    Button home,EquipeDisplay;
    @FXML
    Label captchaCode;
    @FXML
    TextField captchaInput;
    @FXML
    VBox myBox;
    private String captcha;

    private String imagePath; // Add this line to store the image path


    //--------------upload-pic--------------
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath(); // Save the image path
            Image selectedImage = new Image(new File(imagePath).toURI().toString());
            image.setImage(selectedImage);
        }
    }

    //-----------------generate captcha ----------
    private void generateCaptcha() {
        Random rand = new Random();
        StringBuilder captchaBuilder = new StringBuilder();
        // Generate a captcha of length 6
        for (int i = 0; i < 6; i++) {
            // For each iteration, randomly decide to append a character or a number
            if (rand.nextBoolean()) {
                // Append a random number between 0 and 9
                int randomNumber = rand.nextInt(10);
                captchaBuilder.append(randomNumber);
            } else {
                // Append a random character from 'A' to 'Z'
                char randomChar = (char) ('A' + rand.nextInt(26));
                captchaBuilder.append(randomChar);
            }
        }
        // Combine the random numbers and characters to form the captcha
        captcha = captchaBuilder.toString();
        captchaCode.setText(captcha);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //--------------Hover-------------
        FadeTransition ft = new FadeTransition(Duration.millis(300), myBox);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        myBox.setOpacity(0.0);

        // Set up mouse entered event

        myBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //System.out.println("Mouse Entered");
                // myBox.setOpacity(1.0); // Make the VBox fully visible when the mouse enters
                ft.setRate(-1); // Reverse the animation
                ft.play(); // Play the animation
            }
        });
        // Set up mouse Exited event
        myBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // System.out.println("Mouse Exited");
                //myBox.setOpacity(0.0);
                ft.setRate(3); // Play the animation in the forward direction
                ft.play(); // Make the VBox invisible (but still interactable) when the mouse exits
            }
        });










     //-------------------------image by default----------------------------

        Image defaultImage = new Image("/equipe.png");
        image.setImage(defaultImage);




          //----------------- upload function ------------------
        uplaod_pic.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chooseImage();
            }
        });



        //-----------------------captcha-call---------------------


        generateCaptcha();

        //--------------------Save-Button-------------------------
        save.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Equipe equipe = new Equipe();
                ServiceEquipe serviceEquipe = ServiceEquipe.getInstance();

                //------testing-input---------

                String nomEquipe = nomequipe.getText();
                boolean isNomEmpty = nomEquipe.trim().isEmpty();
                LocalDate dateValue = date.getValue();
                boolean isDateEmpty = (dateValue == null);
                boolean isImageEmpty = (image.getImage() == defaultImage);

                if (isImageEmpty || isDateEmpty || isNomEmpty) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("ERROR");
                    alert.setContentText("Please fill in all fields");
                    alert.show();
                    return;
                }

                //-------------testing captcha --------------
                     captchaInput.setAlignment(Pos.CENTER);
                
                if (!captchaInput.getText().equals(captcha)) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Captcha");
                    alert.setContentText("Captcha does not match");
                    alert.show();
                    return;
                }

                // set input values
                equipe.setNom(nomequipe.getText());
                LocalDate localDate = date.getValue();
                java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                equipe.setDateCreation(sqlDate); // Set the date from the DatePicker
                equipe.setImage(imagePath); // Set the image path

                try {
                    serviceEquipe.ajouter(equipe);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Good job");
                    alert.show();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //----------------switching-to-Home------------
        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/HOME.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) date.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("HOME");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        //----------------switching-to-display equipe------------
        EquipeDisplay.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayEquipe.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) date.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("HOME");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }
}
