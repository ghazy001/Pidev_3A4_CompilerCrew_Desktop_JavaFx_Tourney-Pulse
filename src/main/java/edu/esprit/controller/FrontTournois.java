package edu.esprit.controller;

import edu.esprit.entities.Tournois;
import edu.esprit.services.ServiceTournois;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class FrontTournois implements Initializable {

    @FXML
    private Button Bnavigatet;

    @FXML
    private Label LLafichet;

    @FXML
    private TextField TFrecherchet;

    @FXML
    private Button Brecherchet;

    @FXML
    private VBox Vbox;
    ServiceTournois serviceTournois = new ServiceTournois();

    private String selectedIdt;
    private String selectedNomt;
    private String selectedAddress;
    private String selectedNombrem;
    private String selectedDated;
    private String selectedDatef;

    public void initialize(URL url, ResourceBundle resourceBundle) {
        Bnavigatet.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/FrontMatch.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root); // Adjust width and height as needed

                    // Get the current stage
                    Stage stage = (Stage) Vbox.getScene().getWindow();

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

        List<Tournois> tournoisList = serviceTournois.getAll();
        System.out.println("Tournois List: " + tournoisList);
        for (Tournois tournois : tournoisList) {
            System.out.println("Adding tournois to TitledPane: " + tournois);
            // Create layout for each reclamation
            Label nomtLabel = new Label("Nom: " + tournois.getNom_tournois());
            Label addressLabel = new Label("Address: " + tournois.getStade());
            Label nombremLabel = new Label("Nombre Match: " + tournois.getNombre_match());
            Label datedLabel = new Label("Date Debut: " + tournois.getDate_debut());
            Label datefLabel = new Label("Date Fin: " + tournois.getDate_fin());

            GridPane gridPane = new GridPane();
            gridPane.add(nomtLabel, 0, 1);
            gridPane.add(addressLabel, 0, 2);
            gridPane.add(nombremLabel, 0, 3);
            gridPane.add(datedLabel, 0, 4);
            gridPane.add(datefLabel, 0, 5);

            TitledPane titledPane = new TitledPane("Tournois " + tournois.getId_tournois(), gridPane);


            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (tournois != null) {
                        // Assurez-vous que LLafichet n'est pas null pour éviter les erreurs
                        if (LLafichet != null) {
                            // Récupérez les valeurs sélectionnées
                            selectedNomt = tournois.getNom_tournois();
                            selectedAddress = tournois.getStade();
                            selectedNombrem = String.valueOf(tournois.getNombre_match());
                            selectedDated = String.valueOf(tournois.getDate_debut());
                            selectedDatef = String.valueOf(tournois.getDate_fin());

                            // Mettre à jour le Label avec les valeurs sélectionnées
                            LLafichet.setText(
                                            "Selected Nom: " + selectedNomt + "\n" +
                                            "Selected Address: " + selectedAddress + "\n" +
                                            "Selected Nombre Matchs: " + selectedNombrem + "\n" +
                                            "Selected Date Debut: " + selectedDated + "\n" +
                                            "Selected Date Fin: " + selectedDatef
                            );
                        }
                    }




                }
            });


            Vbox.getChildren().add(titledPane);
        }
        loadData();

        Brecherchet.setOnAction(event -> searchTournaments());

    }

    @FXML
    void refrechTournoisAction(ActionEvent event) {
        loadData();

    }

    private void searchTournaments() {
        List<Tournois> tournoisList = serviceTournois.getAll();
        System.out.println("Tournois List: " + tournoisList);
        String searchText = TFrecherchet.getText().trim().toLowerCase(); // Récupérez le texte de recherche
        Vbox.getChildren().clear(); // Effacez les résultats précédents

        // Parcourez la liste des tournois et recherchez ceux correspondant au critère de recherche
        for (Tournois tournois : tournoisList) {
            if (tournois.getNom_tournois().toLowerCase().contains(searchText) || tournois.getStade().toLowerCase().contains(searchText)) {
                // Si le nom du tournoi correspond au critère de recherche, affichez-le
                Label nomtLabel = new Label("Nom: " + tournois.getNom_tournois());
                Label addressLabel = new Label("Address: " + tournois.getStade());
                Label nombremLabel = new Label("Nombre Match: " + tournois.getNombre_match());
                Label datedLabel = new Label("Date Debut: " + tournois.getDate_debut());
                Label datefLabel = new Label("Date Fin: " + tournois.getDate_fin());

                GridPane gridPane = new GridPane();
                gridPane.add(nomtLabel, 0, 1);
                gridPane.add(addressLabel, 0, 2);
                gridPane.add(nombremLabel, 0, 3);
                gridPane.add(datedLabel, 0, 4);
                gridPane.add(datefLabel, 0, 5);

                TitledPane titledPane = new TitledPane("Tournois " + tournois.getId_tournois(), gridPane);


                Vbox.getChildren().add(titledPane);
            }
        }
    }

    private void loadData() {
        Vbox.getChildren().clear(); // Clear existing display
        List<Tournois> tournoisList = serviceTournois.getAll();
        System.out.println("Tournois List: " + tournoisList); // Print the list

        for (Tournois tournois : tournoisList) {
            System.out.println("Adding tournois to TitledPane: " + tournois);
            // Create layout for each reclamation
            Label nomtLabel = new Label("Nom: " + tournois.getNom_tournois());
            Label addressLabel = new Label("Address: " + tournois.getStade());
            Label nombremLabel = new Label("Nombre Match: " + tournois.getNombre_match());
            Label datedLabel = new Label("Date Debut: " + tournois.getDate_debut());
            Label datefLabel = new Label("Date Fin: " + tournois.getDate_fin());

            GridPane gridPane = new GridPane();
            gridPane.add(nomtLabel, 0, 1);
            gridPane.add(addressLabel, 0, 2);
            gridPane.add(nombremLabel, 0, 3);
            gridPane.add(datedLabel, 0, 4);
            gridPane.add(datefLabel, 0, 5);

            TitledPane titledPane = new TitledPane("Tournois " + tournois.getId_tournois(), gridPane);


            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (tournois != null) {
                        // Assurez-vous que LLafichet n'est pas null pour éviter les erreurs
                        if (LLafichet != null) {
                            // Récupérez les valeurs sélectionnées
                            String selectedNomt = tournois.getNom_tournois();
                            String selectedAddress = tournois.getStade();
                            String selectedNombrem = String.valueOf(tournois.getNombre_match());
                            String selectedDated = String.valueOf(tournois.getDate_debut());
                            String selectedDatef = String.valueOf(tournois.getDate_fin());

                            // Mettre à jour le Label avec les valeurs sélectionnées
                            LLafichet.setText(
                                            "Selected Nom: " + selectedNomt + "\n" +
                                            "Selected Address: " + selectedAddress + "\n" +
                                            "Selected Nombre Matchs: " + selectedNombrem + "\n" +
                                            "Selected Date Debut: " + selectedDated + "\n" +
                                            "Selected Date Fin: " + selectedDatef
                            );
                        }
                    }

                }
            });


            Vbox.getChildren().add(titledPane);
        }


    }
}
