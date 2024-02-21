package edu.esprit.Controllers;

import edu.esprit.entities.Messagerie;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;
import java.util.Date;

public class AffMess implements Initializable {

    @FXML
    private TextArea messageArea;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        refreshMessages(); // Rafraîchir les messages lors du chargement de la vue
    }

    @FXML
    private void refreshMessages() {
        List<Messagerie> messages = getMessagesFromService(); // Appeler votre méthode de service pour récupérer les messages
        displayMessages(messages);
    }

    private void displayMessages(List<Messagerie> messages) {
        messageArea.clear(); // Effacer le contenu précédent
        for (Messagerie message : messages) {
            messageArea.appendText("Message ID: " + message.getId_mess() + "\n");
            messageArea.appendText("Contenu: " + message.getContenue() + "\n");
            messageArea.appendText("Temps: " + message.getTemps() + "\n\n");
        }
    }

    // Méthode de service fictive pour démonstration
    private List<Messagerie> getMessagesFromService() {
        // Implémentez cette méthode selon vos besoins pour récupérer les messages
        // Ceci est juste un exemple factice
        return List.of(
                new Messagerie(1, 1, "Contenu du message 1", (java.sql.Date) new Date(System.currentTimeMillis())),
                new Messagerie(2, 1, "Contenu du message 2", (java.sql.Date) new Date(System.currentTimeMillis()))
        );
    }
}
