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
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;

import java.util.List;
import java.util.ResourceBundle;

public class DisplayReclamation implements Initializable {
    @FXML
    VBox Vbox;
    @FXML
    TextField id,userid,email,date,object,reclamationI;
    @FXML
    Button delete,update,navigate,message;

    ServiceReclamation serviceReclamation = new ServiceReclamation();

    private String selectedId;
    private String selectedUserId;
    private String selectedEmail;
    private String selectedObject;
    private String selectedRec;
    private String selectedDateRec;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        message.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/Messagerie.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root, 800, 600); // Adjust width and height as needed

                    // Get the current stage
                    Stage stage = (Stage) delete.getScene().getWindow();

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



        navigate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/Addrec.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root, 800, 600); // Adjust width and height as needed

                    // Get the current stage
                    Stage stage = (Stage) delete.getScene().getWindow();

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


        System.out.println("zzzzzzzzz");
        List<Reclamation> reclamationList = serviceReclamation.getAll();
        System.out.println("Reclamation List: " + reclamationList); // Print the list

        for (Reclamation reclamation : reclamationList) {
            System.out.println("Adding reclamation to TitledPane: " + reclamation);
            // Create layout for each reclamation
            Label idLabel = new Label("ID: " + reclamation.getId_rec());
            Label userIdLabel = new Label("UserID: " + reclamation.getId());
            Label emailLabel = new Label("Email: " + reclamation.getEmail());
            Label objectLabel = new Label("Object: " + reclamation.getObject());
            Label recLabel = new Label("Rec: " + reclamation.getRec());
            Label dateLabel = new Label("Date Rec: " + reclamation.getDate_rec());

            GridPane gridPane = new GridPane();
            gridPane.add(idLabel, 0, 0);
            gridPane.add(userIdLabel, 0, 1);
            gridPane.add(emailLabel, 0, 2);
            gridPane.add(objectLabel, 0, 3);
            gridPane.add(recLabel, 0, 4);
            gridPane.add(dateLabel, 0, 5);

            TitledPane titledPane = new TitledPane("Reclamation de " + reclamation.getEmail(), gridPane);


            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
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

                    id.setText(selectedId);
                    userid.setText(selectedUserId);
                    email.setText(selectedEmail);
                    date.setText(selectedDateRec);
                    reclamationI.setText(selectedRec);
                    object.setText(selectedObject);

                }
            });


            Vbox.getChildren().add(titledPane);
        }

        loadReclamationData();


         // ----------------delete code --------------------------
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(selectedId != null) {
                    serviceReclamation.supprimer(Integer.parseInt(selectedId));
                    loadReclamationData();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("supprimee avec succes");
                    alert.show();
                }
            }
        });
        //-------------update----------------------


        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Reclamation reclamation = new Reclamation(Integer.parseInt(id.getText()),Integer.parseInt(userid.getText()),email.getText(),object.getText(),reclamationI.getText(),new Date(System.currentTimeMillis()));

                if(selectedId != null) {
                    serviceReclamation.update(reclamation);
                    loadReclamationData();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("modifiee avec succes");
                    alert.show();
                }
            }
        });










    }




 //-------------refresh fucnction ---------------------------------------
    private void loadReclamationData() {
        Vbox.getChildren().clear(); // Clear existing display

        List<Reclamation> reclamationList = serviceReclamation.getAll();

        for (Reclamation reclamation : reclamationList) {
            // Create layout for each reclamation
           // Label idLabel = new Label("ID: " + reclamation.getId_rec());
            Label userIdLabel = new Label("User: " + reclamation.getId());
            Label emailLabel = new Label("Email: " + reclamation.getEmail());
            Label objectLabel = new Label("Object: " + reclamation.getObject());
            Label recLabel = new Label("Rec: " + reclamation.getRec());
            Label dateLabel = new Label("Date Rec: " + reclamation.getDate_rec());

            GridPane gridPane = new GridPane();
           // gridPane.add(idLabel, 0, 0);
            gridPane.add(userIdLabel, 0, 1);
            gridPane.add(emailLabel, 0, 2);
            gridPane.add(objectLabel, 0, 3);
            gridPane.add(recLabel, 0, 4);
            gridPane.add(dateLabel, 0, 5);

            TitledPane titledPane = new TitledPane("Reclamation de " + reclamation.getEmail(), gridPane);

            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
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

                    id.setText(selectedId);
                    userid.setText(selectedUserId);
                    email.setText(selectedEmail);
                    date.setText(selectedDateRec);
                    reclamationI.setText(selectedRec);
                    object.setText(selectedObject);
                }
            });

            Vbox.getChildren().add(titledPane);
        }
    }
}
