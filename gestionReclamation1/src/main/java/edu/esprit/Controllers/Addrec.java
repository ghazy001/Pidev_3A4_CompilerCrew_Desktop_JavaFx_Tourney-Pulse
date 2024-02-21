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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

/*
public class Addrec {
}
*/
public class Addrec implements Initializable {
    @FXML
    private TextField TFObjet;
    @FXML
    private TextField TFReclamation;
    @FXML
    private TextField TFEmail;
    ServiceReclamation sp = new ServiceReclamation();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


    }





    @FXML
    void ajouterReclamationAction(ActionEvent event) {
        try {
            Date today = new Date(System.currentTimeMillis());
            int id = 123;
            this.sp.ajouter(new Reclamation(this.TFObjet.getText(), this.TFEmail.getText(), this.TFReclamation.getText(), today, id));
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