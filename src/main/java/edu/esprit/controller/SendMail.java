package edu.esprit.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;
import java.util.ResourceBundle;

public class SendMail implements Initializable {

    @FXML
    private Button Bnavigatetournois;

    @FXML
    private Button Bsendmail;

    @FXML
    private Button Bnavigatematch;

    @FXML
    private TextArea TAbodyemail;

    @FXML
    private TextField TFobject;

    @FXML
    private TextField TFsendemail;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Bnavigatetournois.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/AfficherTournois.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root); // Adjust width and height as needed

                    // Get the current stage
                    Stage stage = (Stage) TFsendemail.getScene().getWindow();

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

        Bnavigatematch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/AfficherMatch.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root); // Adjust width and height as needed

                    // Get the current stage
                    Stage stage = (Stage) TFsendemail.getScene().getWindow();

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

        Bsendmail.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String recipientEmail = TFsendemail.getText();
                String subject = TFobject.getText();
                String body = TAbodyemail.getText();

                if (recipientEmail.isEmpty() || subject.isEmpty() || body.isEmpty()) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information manquante!");
                    alert.setHeaderText("Gestion Des Tournois");
                    alert.setContentText("Veuillez compléter tous les champs!!");
                    alert.showAndWait();
                    return;
                }

                MailSender.sendEmail(recipientEmail, subject, body);

                TFsendemail.setText("");
                TFobject.setText("");
                TAbodyemail.setText("");

            }
        });


    }

    public class MailSender {
        public static void sendEmail(String recipientEmail, String subject, String body) {
            // Paramètres de l'hôte SMTP
            String host = "smtp-mail.outlook.com";
            String username = "MohamedRayan.Fathallah@esprit.tn";
            String password = "53938140MRf";

            // Propriétés pour la session de courrier
            Properties props = new Properties();
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.starttls.enable", "true");
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.port", "587");

            // Création d'une session de courrier
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });


            try {
                // Création du message
                Message message = new MimeMessage(session);
                message.setFrom(new InternetAddress(username));
                message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recipientEmail));
                message.setSubject(subject);
                message.setText(body);

                // Envoi du message
                Transport.send(message);

                System.out.println("Email sent successfully!");
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Email");
                alert.setHeaderText("Gestion Des Tournois");
                alert.setContentText("Email sent successfully!");
                alert.showAndWait();

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }

    }

}
