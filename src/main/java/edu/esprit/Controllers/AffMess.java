package edu.esprit.Controllers;

import edu.esprit.Services.ServiceMessagerie;
import edu.esprit.entities.Messagerie;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.List;

public class AffMess implements Initializable {
    @FXML
    VBox Vbox;
    @FXML
    TextField id_mess,id_rec,contenue,temps;
    @FXML
    Button delete,update,navigate,message;
    ServiceMessagerie serviceMessagerie = new ServiceMessagerie();

    private String selectedIdmess;
    private String selectedIdrec;
    private String selectedcontenue;
    private String selectedtemps;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<Messagerie> messagerieList = serviceMessagerie.getAll();
        System.out.println("Messagerie List: " + messagerieList); // Print the list

        for (Messagerie messagerie : messagerieList) {
            System.out.println("Adding message to TitledPane: " + messagerie);
            // Create layout for each reclamation
            Label idrecLabel = new Label("ID: " + messagerie.getId_rec());
            Label messageIdLabel = new Label("UserID: " + messagerie.getId_mess());
            Label contenueLabel = new Label("Rec: " + messagerie.getContenue());
            Label tempsLabel = new Label("Date Rec: " + messagerie.getTemps());

            GridPane gridPane = new GridPane();
            gridPane.add(messageIdLabel, 0, 0);

            gridPane.add(idrecLabel, 0, 1);
            gridPane.add(contenueLabel, 0, 2);
            gridPane.add(tempsLabel, 0, 3);

            TitledPane titledPane = new TitledPane("Messagerie  " + messagerie.getId_mess(), gridPane);


            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedIdmess = "" + messagerie.getId_mess();

                    selectedIdrec = "" + messagerie.getId_rec();
                    selectedcontenue = messagerie.getContenue();
                    selectedtemps = String.valueOf(messagerie.getTemps());

                    // Perform any action with the selected values
                    System.out.println("Selected ID: " + selectedIdrec);
                    System.out.println("Selected ID rec: " + selectedIdmess);
                    System.out.println("Selected contenue: " + selectedcontenue);

                    System.out.println("Selected Date message: " + selectedtemps);
                    id_mess.setText(selectedIdmess);

                    id_rec.setText(selectedIdrec);
                    temps.setText(selectedtemps);
                    contenue.setText(selectedcontenue);

                }
            });


            Vbox.getChildren().add(titledPane);
        }

        loadMessagerieData();

        // ----------------delete code --------------------------
        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(selectedIdmess != null) {
                    serviceMessagerie.supprimer(Integer.parseInt(selectedIdmess));
                    loadMessagerieData();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("supprimee avec succes");
                    alert.show();
                }
            }
        });


//--------refresh


    }
    private void loadMessagerieData() {
        Vbox.getChildren().clear(); // Clear existing display

        List<Messagerie>messagerieList = serviceMessagerie.getAll();
        System.out.println("Messagerie List: " + messagerieList); // Print the list

        for (Messagerie messagerie : messagerieList) {
            System.out.println("Adding message to TitledPane: " + messagerie);
            // Create layout for each messagerie
            Label idrecLabel = new Label("ID Rec: " + messagerie.getId_rec());
            Label messageIdLabel = new Label("ID Message: " + messagerie.getId_mess());
            Label contenueLabel = new Label("Contenue: " + messagerie.getContenue());
            Label tempsLabel = new Label("Date: " + messagerie.getTemps());

            GridPane gridPane = new GridPane();
            gridPane.add(messageIdLabel, 0, 0);
            gridPane.add(idrecLabel, 0, 1);
            gridPane.add(contenueLabel, 0, 2);
            gridPane.add(tempsLabel, 0, 3);

            TitledPane titledPane = new TitledPane("Messagerie " + messagerie.getId_mess(), gridPane);

            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedIdmess = String.valueOf(messagerie.getId_mess());
                    selectedIdrec = String.valueOf(messagerie.getId_rec());
                    selectedcontenue = messagerie.getContenue();
                    selectedtemps = String.valueOf(messagerie.getTemps());

                    // Perform any action with the selected values
                    System.out.println("Selected ID Message: " + selectedIdmess);
                    System.out.println("Selected ID Rec: " + selectedIdrec);
                    System.out.println("Selected Contenue: " + selectedcontenue);
                    System.out.println("Selected Date: " + selectedtemps);

                    id_mess.setText(selectedIdmess);
                    id_rec.setText(selectedIdrec);
                    contenue.setText(selectedcontenue);
                    temps.setText(selectedtemps);
                }
            });

            Vbox.getChildren().add(titledPane);
        }
    }
}
