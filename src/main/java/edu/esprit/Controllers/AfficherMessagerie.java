package edu.esprit.Controllers;


import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AfficherMessagerie {
    // Déclarations des champs
    @FXML
    private TextField TFMess;

    @FXML
    private VBox Vbox;

    @FXML
    private void envoyerMessage() {
        String message = TFMess.getText();
        if (!message.isEmpty()) {
            Label labelMessage = new Label(message);
            Vbox.getChildren().add(labelMessage);
            TFMess.clear(); // Efface le champ texte après l'envoi du message
        }
    }

    // Autres méthodes et champs...
}


