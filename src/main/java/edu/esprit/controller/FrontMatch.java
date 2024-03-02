package edu.esprit.controller;

import edu.esprit.entities.Matchs;
import edu.esprit.services.ServiceMatch;
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
import java.util.List;
import java.util.ResourceBundle;

public class FrontMatch implements Initializable {

    @FXML
    private Button Bnavigatem;

    @FXML
    private Label LLafichem;

    @FXML
    private TextField TFrecherchem;

    @FXML
    private Button Brecherchem;

    @FXML
    private VBox Vboxx;

    ServiceMatch serviceMatch = new ServiceMatch();

    private String selectedIdm;
    private String selectedNomm;
    private String selectedDate;
    private String selectedDuree;
    private String selectedIdt;
    private String selectedIde1;
    private String selectedIde2;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Bnavigatem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/FrontTournois.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root); // Adjust width and height as needed

                    // Get the current stage
                    Stage stage = (Stage) Vboxx.getScene().getWindow();

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

        List<Matchs> matchsList = serviceMatch.getAll();
        System.out.println("Match List: " + matchsList); // Print the list

        for (Matchs matchs : matchsList) {
            System.out.println("Adding match to TitledPane: " + matchs);
            // Create layout for each reclamation
            Label nommLabel = new Label("Nom: " + matchs.getNom_match());
            Label dateLabel = new Label("Date: " + matchs.getDate_match());
            Label dureeLabel = new Label("Duree: " + matchs.getDuree_match());
            Label idtLabel = new Label("Nom Tournois: " + matchs.getTournois().getNom_tournois());
            Label ideLabel1 = new Label("Nom Equipe 1: " + (matchs.getEquipe().getNom_equipe() ));
            Label ideLabel2 = new Label("Nom Equipe 2: " + (matchs.getEquipe1().getNom_equipe()));


            GridPane gridPane = new GridPane();
            gridPane.add(nommLabel, 0, 1);
            gridPane.add(dateLabel, 0, 2);
            gridPane.add(dureeLabel, 0, 3);
            gridPane.add(idtLabel, 0, 4);
            gridPane.add(ideLabel1, 0, 5);
            gridPane.add(ideLabel2, 0, 6);


            TitledPane titledPane = new TitledPane("Match " + matchs.getId_match(), gridPane);


            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (matchs != null) {
                        // Assurez-vous que LLafichet n'est pas null pour éviter les erreurs
                        if (LLafichem != null) {
                            // Récupérez les valeurs sélectionnées
                            selectedNomm = matchs.getNom_match();
                            selectedDate = String.valueOf(matchs.getDate_match());
                            selectedDuree = matchs.getDuree_match();
                            selectedIdt = String.valueOf(matchs.getTournois().getId_tournois());
                            selectedIde1 = String.valueOf(matchs.getEquipe().getId_equipe());
                            selectedIde2 = String.valueOf(matchs.getEquipe1().getId_equipe());

                            // Mettre à jour le Label avec les valeurs sélectionnées
                            LLafichem.setText(
                                            "Selected Nom: " + selectedNomm + "\n" +
                                            "Selected Address: " + selectedDate + "\n" +
                                            "Selected Nombre Matchs: " + selectedDuree + "\n" +
                                            "Selected Date Debut: " + selectedIdt + "\n" +
                                            "Selected Date Fin: " + selectedIde1 + "\n" +
                                            "Selected Date Debut: " + selectedIde2
                            );
                        }
                    }

                }
            });


            Vboxx.getChildren().add(titledPane);
        }
        loadData();

        Brecherchem.setOnAction(event -> searchTournaments());

    }


    @FXML
    void refrechMatchAction(ActionEvent event) {
        loadData();

    }

    private void searchTournaments() {
        List<Matchs> matchsList = serviceMatch.getAll();
        System.out.println("Match List: " + matchsList);
        String searchText = TFrecherchem.getText().trim().toLowerCase(); // Récupérez le texte de recherche
        Vboxx.getChildren().clear(); // Effacez les résultats précédents

        // Parcourez la liste des tournois et recherchez ceux correspondant au critère de recherche
        for (Matchs matchs : matchsList) {
            if (matchs.getNom_match().toLowerCase().contains(searchText)) {
                // Si le nom du tournoi correspond au critère de recherche, affichez-le
                Label nommLabel = new Label("Nom: " + matchs.getNom_match());
                Label dateLabel = new Label("Date: " + matchs.getDate_match());
                Label dureeLabel = new Label("Duree: " + matchs.getDuree_match());
                Label idtLabel = new Label("Nom Tournois: " + matchs.getTournois().getNom_tournois());
                Label ideLabel1 = new Label("Nom Equipe 1: " + (matchs.getEquipe().getNom_equipe() ));
                Label ideLabel2 = new Label("Nom Equipe 2: " + (matchs.getEquipe1().getNom_equipe()));

                GridPane gridPane = new GridPane();
                gridPane.add(nommLabel, 0, 1);
                gridPane.add(dateLabel, 0, 2);
                gridPane.add(dureeLabel, 0, 3);
                gridPane.add(idtLabel, 0, 4);
                gridPane.add(ideLabel1, 0, 5);
                gridPane.add(ideLabel2, 0, 6);

                TitledPane titledPane = new TitledPane("Match " + matchs.getId_match(), gridPane);

                Vboxx.getChildren().add(titledPane);
            }
        }
    }

    private void loadData() {
        Vboxx.getChildren().clear(); // Clear existing display
        List<Matchs> matchsList = serviceMatch.getAll();
        System.out.println("Match List: " + matchsList); // Print the list

        for (Matchs matchs : matchsList) {
            System.out.println("Adding match to TitledPane: " + matchs);
            // Create layout for each reclamation
            Label nommLabel = new Label("Nom: " + matchs.getNom_match());
            Label dateLabel = new Label("Date: " + matchs.getDate_match());
            Label dureeLabel = new Label("Duree: " + matchs.getDuree_match());
            Label idtLabel = new Label("Nom Tournois: " + matchs.getTournois().getNom_tournois());
            Label ideLabel1 = new Label("Nom Equipe 1: " + (matchs.getEquipe().getNom_equipe() ));
            Label ideLabel2 = new Label("Nom Equipe 2: " + (matchs.getEquipe1().getNom_equipe()));

            GridPane gridPane = new GridPane();
            gridPane.add(nommLabel, 0, 1);
            gridPane.add(dateLabel, 0, 2);
            gridPane.add(dureeLabel, 0, 3);
            gridPane.add(idtLabel, 0, 4);
            gridPane.add(ideLabel1, 0, 5);
            gridPane.add(ideLabel2, 0, 6);


            TitledPane titledPane = new TitledPane("Match " + matchs.getId_match(), gridPane);


            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    if (matchs != null) {
                        // Assurez-vous que LLafichet n'est pas null pour éviter les erreurs
                        if (LLafichem != null) {
                            // Récupérez les valeurs sélectionnées
                            selectedNomm = matchs.getNom_match();
                            selectedDate = String.valueOf(matchs.getDate_match());
                            selectedDuree = matchs.getDuree_match();
                            selectedIdt = String.valueOf(matchs.getTournois().getId_tournois());
                            selectedIde1 = String.valueOf(matchs.getEquipe().getId_equipe());
                            selectedIde2 = String.valueOf(matchs.getEquipe1().getId_equipe());

                            // Mettre à jour le Label avec les valeurs sélectionnées
                            LLafichem.setText(
                                    "Selected Nom: " + selectedNomm + "\n" +
                                            "Selected Address: " + selectedDate + "\n" +
                                            "Selected Nombre Matchs: " + selectedDuree + "\n" +
                                            "Selected Date Debut: " + selectedIdt + "\n" +
                                            "Selected Date Fin: " + selectedIde1 + "\n" +
                                            "Selected Date Debut: " + selectedIde2
                            );
                        }
                    }

                }
            });


            Vboxx.getChildren().add(titledPane);
        }
    }



}
