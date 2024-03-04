package edu.esprit.Controllers;

import edu.esprit.Services.ServiceReclamation;
import edu.esprit.entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
public class Addrec {
}
*/
public class Addrec implements Initializable {
    @FXML
    private TextField TFObjet;
    @FXML
    private TextArea TFReclamation;
    @FXML
    private TextField TFEmail;
    ServiceReclamation sp = new ServiceReclamation();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }



    // Expression régulière pour vérifier le format d'une adresse email
    private static final String EMAIL_REGEX =
            "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    // Pattern correspondant à l'expression régulière
    private static final Pattern pattern = Pattern.compile(EMAIL_REGEX);

    @FXML
    void ajouterReclamationAction(ActionEvent event) {
        try {
            Date today = new Date(System.currentTimeMillis());
            int id = 4;

            // Vérification si tous les champs sont remplis
            if (TFObjet.getText().isEmpty() || TFEmail.getText().isEmpty() || TFReclamation.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Champs vides");
                alert.setContentText("Veuillez remplir tous les champs.");
                alert.showAndWait();
                return; // Sortir de la méthode si un champ est vide
            }


            // Vérification du format de l'adresse email saisie
            String email = TFEmail.getText().trim();
            Matcher matcher = pattern.matcher(email);
            if (!matcher.matches()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Format d'email incorrect");
                alert.setContentText("Veuillez saisir une adresse email valide.");
                alert.showAndWait();
                return; // Sortir de la méthode si le format est incorrect
            }

            this.sp.ajouter(new Reclamation(email,this.TFObjet.getText(), this.TFReclamation.getText(), today, id));


            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Ajoutee avec succes");
            alert.show();
        } catch (Exception var5) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQL Exception");
            alert.setContentText(var5.getMessage());
            alert.showAndWait();
        }

    }
    public void message(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontMess.fxml"));
            Parent root = loader.load();
            //MessagesController messagesController = loader.getController();
            //messagesController.setUser_id(4);
            TFEmail.getScene().setRoot(root);


        } catch (IOException var4) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    public void navigatetoAfficherReclamationAction(ActionEvent actionEvent) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/DisplayReclamation.fxml"));

            // Create a Scene with custom dimensions
            Scene scene = new Scene(root, 800, 600); // Adjust width and height as needed

            // Get the current stage
            Stage stage = (Stage) TFObjet.getScene().getWindow();

            // Set the new scene to the stage
            stage.setScene(scene);


        } catch (IOException var4) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }
}