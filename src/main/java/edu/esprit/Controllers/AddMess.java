package edu.esprit.Controllers;

import edu.esprit.Services.ServiceMessagerie;
import edu.esprit.Services.ServiceReclamation;
import edu.esprit.entities.Messagerie;
import edu.esprit.entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.sql.Date;
import java.sql.PreparedStatement;

public class AddMess {
    @FXML
    private TextArea messageTextArea;
    @FXML
    private VBox messagePane;
    ServiceMessagerie sp = new ServiceMessagerie();

    @FXML
    public void displayMessage() {
        String message = messageTextArea.getText();
        Label messageLabel = new Label(message);
        messagePane.getChildren().add(messageLabel);
    }
    /*void ajouterMessageAction(ActionEvent event) {
        try {

            Date date = new Date(System.currentTimeMillis());
            this.sp.ajouter(new Reclamation(this.TFObjet.getText(), email, this.TFReclamation.getText(), today, id));

            this.sp.ajouter(new Messagerie(this.messageTextArea.getText()));
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("Ajoutee avec succes");
            alert.show();

                String sql = "INSERT INTO messagerie (id_rec, contenue, temps) VALUES (?, ?, ?)";
                PreparedStatement statement = conn.prepareStatement(sql);

                statement.setString(2, messageTextArea.getText());
                statement.setTimestamp(3, new java.sql.Timestamp(date.getTime()));
                statement.executeUpdate();
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setContentText("Message ajouté avec succès.");
            alert.show();
        } catch (SQLException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur SQL");
            alert.setContentText("Erreur lors de l'ajout du message : " + e.getMessage());
            alert.showAndWait();
        }
    }*/
}
