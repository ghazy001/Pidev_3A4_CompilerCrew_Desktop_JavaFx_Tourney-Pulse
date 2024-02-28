package controller;

import com.twilio.Twilio;
import com.twilio.base.ResourceSet;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
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
import java.net.URI;
import java.net.URL;
import java.sql.SQLException;
import java.util.Random;
import java.util.ResourceBundle;
import java.time.LocalDate;
import java.sql.*;
import com.twilio.Twilio;
import com.twilio.converter.Promoter;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import java.net.URI;
import java.math.BigDecimal;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;


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
    Button home, EquipeDisplay;
    @FXML
    Label captchaCode;
    @FXML
    TextField captchaInput;
    @FXML
    VBox myBox;
    private String captcha;

    private String imagePath; // Add this line to store the image path

    public static final String ACCOUNT_SID = "AC4d1d41f7d616d4a60ac222a861f9a695";
    public static final String AUTH_TOKEN = "ssss";



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


        Verifer();

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
                    // sms
                    sendTwilioSMS();
                    //
                    serviceEquipe.ajouter(equipe);
                    //sing
                    //sing();
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

        //----------------switching-to-display2card equipe------------


    }

    // ------------- sms confirmation by twil

    private void sendTwilioSMS() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        Message message = Message.creator(
                        new com.twilio.type.PhoneNumber("+21626864405"),
                        new com.twilio.type.PhoneNumber("+17816337025"),
                        "Your team has been added")
                .setMediaUrl(
                        Promoter.listOfOne(URI.create("http://placekitten.com/300/200")))
                .create();

        System.out.println(message.getSid()+"status :" +message.getStatus());

    }

    public void sing() {
        // sound effect
        try {
            File file = new File("src/main/resources/media/sui.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);
            Clip clip = AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
            Thread.sleep(clip.getMicrosecondLength() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ------------only Letters-------------------

    void Verifer() {
        nomequipe.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z]*")) {
                // If the new value is not a string (contains non-alphabetic characters), set it back to the old value
                nomequipe.setText(oldValue);
            }
        });
    }






}
