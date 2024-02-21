package edu.esprit.controller;

import edu.esprit.entities.Matchs;
import edu.esprit.entities.Tournois;
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
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherMatch implements Initializable {

        @FXML
        private Button Bmodm;

        @FXML
        private Button Bnavigatem;

        @FXML
        private Button Bsuppm;

        @FXML
        private DatePicker TFdatem;

        @FXML
        private TextField TFdureem;

        @FXML
        private TextField TFnomm;

        @FXML
        private TextField TFidt;

        @FXML
        private VBox Vboxx;

        ServiceMatch serviceMatch = new ServiceMatch();

        private String selectedIdm;
        private String selectedNomm;
        private String selectedDate;
        private String selectedDuree;
        private String selectedIdt;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Bnavigatem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/AfficherTournois.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root, 800, 600); // Adjust width and height as needed

                    // Get the current stage
                    Stage stage = (Stage) TFnomm.getScene().getWindow();

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
            Label idmLabel = new Label("ID: " + matchs.getId_match());
            Label nommLabel = new Label("Nom: " + matchs.getNom_match());
            Label dateLabel = new Label("Date: " + matchs.getDate_match());
            Label dureeLabel = new Label("Duree: " + matchs.getDuree_match());
            Label idtLabel = new Label("Nom Tournois: " + matchs.getId_tournois());

            GridPane gridPane = new GridPane();
            gridPane.add(idmLabel, 0, 0);
            gridPane.add(nommLabel, 0, 1);
            gridPane.add(dateLabel, 0, 2);
            gridPane.add(dureeLabel, 0, 3);
            gridPane.add(idtLabel, 0, 4);

            TitledPane titledPane = new TitledPane("Tournois " + matchs.getId_tournois(), gridPane);


            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedIdm = "" + matchs.getId_match();
                    selectedNomm = matchs.getNom_match();
                    selectedDate = String.valueOf(matchs.getDate_match());
                    selectedDuree = matchs.getDuree_match();
                    selectedIdt = String.valueOf(matchs.getId_tournois());

                    // Perform any action with the selected values
                    System.out.println("Selected ID: " + selectedIdm);
                    System.out.println("Selected Nom: " + selectedNomm);
                    System.out.println("Selected Date: " + selectedDate);
                    System.out.println("Selected Duree: " + selectedDuree);
                    System.out.println("Selected ID Tournois: " + selectedIdt);

                    TFnomm.setText(selectedNomm);
                    LocalDate dateMatch = Date.valueOf(selectedDate).toLocalDate();
                    TFdatem.setValue(dateMatch);
                    TFdureem.setText(selectedDuree);
                    TFidt.setText(selectedIdt);

                }
            });


            Vboxx.getChildren().add(titledPane);
        }
        loadData1();

        Bsuppm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (selectedIdm != null) {
                    serviceMatch.supprimer(Integer.parseInt(selectedIdm));
                    loadData1();
                }
            }

        });

        Bmodm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                if (selectedIdm != null) {
                    String idTourText = TFidt.getText();
                    int idTour;
                    String dateMText = String.valueOf(TFdatem.getValue());
                    java.util.Date dateM;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        idTour = Integer.parseInt(idTourText);
                        dateM = sdf.parse(dateMText);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    Tournois tournois = new Tournois();
                    tournois.setId_tournois(idTour);
                    Date sqlDateM = new Date(dateM.getTime());
                    serviceMatch.modifier(new Matchs(TFnomm.getText(), sqlDateM, TFdureem.getText(), tournois));
                    loadData1();
                }
            }


        });

    }

    @FXML
    void ajouterMatchsAction(ActionEvent event) {
        String nomTourText = TFidt.getText();
        int idTour;
        String dateMText = String.valueOf(TFdatem.getValue());
        java.util.Date dateM;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateM = sdf.parse(dateMText);
            Date sqlDateM = new Date(dateM.getTime());

            ServiceMatch sm = new ServiceMatch();


            if (!TFnomm.getText().isEmpty() || !TFdureem.getText().isEmpty() || !TFidt.getText().isEmpty()) {
                idTour = Integer.parseInt(nomTourText);
                Tournois tournois = new Tournois();
                tournois.setId_tournois(idTour);
                sm.ajouter(new Matchs(TFnomm.getText(), sqlDateM, TFdureem.getText(), tournois));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Match ajouter!");
                alert.show();
                loadData1();

                LocalDate t = null;
                TFnomm.setText("");
                TFdatem.setValue(t);
                TFdureem.setText("");
                TFidt.setText("");
                TFnomm.requestFocus();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Information manquante!");
                alert.setContentText("Veuillez completez les champs manquant pour le match");
                alert.show();
            }
        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Parse Exception");
            alert.setContentText("Error parsing date: " + e.getMessage());
            alert.showAndWait();
        }

    }

    private void loadData1() {
        Vboxx.getChildren().clear(); // Clear existing display
        List<Matchs> matchsList = serviceMatch.getAll();
        System.out.println("Match List: " + matchsList); // Print the list

        for (Matchs matchs : matchsList) {
            System.out.println("Adding match to TitledPane: " + matchs);
            // Create layout for each reclamation
            Label idmLabel = new Label("ID: " + matchs.getId_match());
            Label nommLabel = new Label("Nom: " + matchs.getNom_match());
            Label dateLabel = new Label("Date: " + matchs.getDate_match());
            Label dureeLabel = new Label("Duree: " + matchs.getDuree_match());
            Label idtLabel = new Label("Nom Tournois: " + matchs.getId_tournois());

            GridPane gridPane = new GridPane();
            gridPane.add(idmLabel, 0, 0);
            gridPane.add(nommLabel, 0, 1);
            gridPane.add(dateLabel, 0, 2);
            gridPane.add(dureeLabel, 0, 3);
            gridPane.add(idtLabel, 0, 4);

            TitledPane titledPane = new TitledPane("Tournois " + matchs.getId_tournois(), gridPane);


            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    selectedIdm = "" + matchs.getId_match();
                    selectedNomm = matchs.getNom_match();
                    selectedDate = String.valueOf(matchs.getDate_match());
                    selectedDuree = matchs.getDuree_match();
                    selectedIdt = String.valueOf(matchs.getId_tournois());

                    // Perform any action with the selected values
                    System.out.println("Selected ID: " + selectedIdm);
                    System.out.println("Selected Nom: " + selectedNomm);
                    System.out.println("Selected Date: " + selectedDate);
                    System.out.println("Selected Duree: " + selectedDuree);
                    System.out.println("Selected ID Tournois: " + selectedIdt);

                    TFnomm.setText(selectedNomm);
                    LocalDate dateMatch = Date.valueOf(selectedDate).toLocalDate();
                    TFdatem.setValue(dateMatch);
                    TFdureem.setText(selectedDuree);
                    TFidt.setText(selectedIdt);

                }
            });


            Vboxx.getChildren().add(titledPane);
        }
    }
}
