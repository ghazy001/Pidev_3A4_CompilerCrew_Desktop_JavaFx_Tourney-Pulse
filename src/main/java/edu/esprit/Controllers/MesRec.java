package edu.esprit.Controllers;

import edu.esprit.Services.ServiceReclamation;
import edu.esprit.entities.Reclamation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class MesRec implements Initializable {


    private String selectedId;
    private String selectedUserId;
    private String selectedEmail;
    private String selectedObject;
    private String selectedRec;
    private String selectedDateRec;
    @FXML
    private VBox vboxRec;

    @FXML
    private Button message,navigate;

    private int userId;
    // L'ID de l'utilisateur pour lequel vous souhaitez afficher les réclamations
    private ServiceReclamation serviceReclamation = new ServiceReclamation();

    public void setUserId(int userId) {
        this.userId = userId;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        navigate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    // Charger la vue Addrec.fxml
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Addrec.fxml"));
                    Parent root = loader.load();

                    // Créer une scène avec des dimensions personnalisées
                    Scene scene = new Scene(root, 800, 600); // Ajustez la largeur et la hauteur selon vos besoins

                    // Récupérer la scène actuelle
                    Stage stage = (Stage) navigate.getScene().getWindow();

                    // Définir la nouvelle scène sur la stage
                    stage.setScene(scene);
                } catch (IOException e) {
                    e.printStackTrace(); // Gérer l'exception si le chargement de la vue échoue
                }
            }
        });

       /* message.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/FrontMess.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root, 800, 600); // Adjust width and height as needed

                    // Get the current stage
                    Stage stage = (Stage) message.getScene().getWindow();

                    // Set the new scene to the stage
                    stage.setScene(scene);


                } catch (IOException var4) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Sorry");
                    alert.setTitle("Error");
                    alert.show();
                }

            }
        });*/

        loadUserReclamations();
    }

    @FXML
    void handleUpdate(ActionEvent event) {
        try{
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FrontMess.fxml"));
            Parent root = loader.load();
            MessageC messageC = loader.getController(); // Retrieve the controller instance
            messageC.setRec_id(Integer.parseInt(selectedId));
            System.out.println(Integer.parseInt(selectedId) + "" + Integer.parseInt(selectedUserId));
            messageC.setUser_id(Integer.parseInt(selectedUserId));
            message.getScene().setRoot(root);
        } catch(IOException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Can not load to the messages page" + e.getMessage());
            alert.show();
        }

    }

    private void loadUserReclamations() {
        List<Reclamation> userReclamations = serviceReclamation.getReclamationsByUserId(4);
        for (Reclamation reclamation : userReclamations) {
            // Créer des labels pour afficher les détails de la réclamation
            Label idLabel = new Label("ID: " + reclamation.getId_rec());
            Label emailLabel = new Label("Email: " + reclamation.getEmail());
            Label objectLabel = new Label("Object: " + reclamation.getObject());
            Label recLabel = new Label("Rec: " + reclamation.getRec());
            Label dateLabel = new Label("Date Rec: " + reclamation.getDate_rec());

            // Créer un GridPane pour disposer les labels de manière organisée
            GridPane gridPane = new GridPane();
            gridPane.setHgap(10);
            gridPane.setVgap(5);

            // Ajouter les labels au GridPane avec leurs positions
            gridPane.add(idLabel, 0, 0);
            gridPane.add(emailLabel, 0, 1);
            gridPane.add(objectLabel, 0, 2);
            gridPane.add(recLabel, 0, 3);
            gridPane.add(dateLabel, 0, 4);

            // Créer un TitledPane pour chaque réclamation avec le GridPane
            TitledPane titledPane = new TitledPane("Reclamation de " + reclamation.getEmail(), gridPane);
            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    // Single-click behavior
                    selectedId = "" + reclamation.getId_rec();
                    selectedUserId = "" + reclamation.getId();
                    selectedEmail = reclamation.getEmail();
                    selectedObject = reclamation.getObject();
                    selectedRec = reclamation.getRec();
                    selectedDateRec = String.valueOf(reclamation.getDate_rec());

                    // Perform any action with the selected values
                    System.out.println("Selected ID: " + selectedId);
                    System.out.println("Selected UserID: " + selectedUserId);
                    System.out.println("Selected Email: " + selectedEmail);
                    System.out.println("Selected Object: " + selectedObject);
                    System.out.println("Selected Rec: " + selectedRec);
                    System.out.println("Selected Date Rec: " + selectedDateRec);



                }
            });


            // Ajouter le TitledPane au VBox principal
            vboxRec.getChildren().add(titledPane);
        }
    }
}
