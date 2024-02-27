package edu.esprit.controller;

import edu.esprit.entities.Tournois;
import edu.esprit.services.ServiceTournois;
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

public class AfficherTournois implements Initializable {


        @FXML
        private Button Bmodifier;

        @FXML
        private Button Bnavigatet;

        @FXML
        private Button Bsupprimer;

        @FXML
        private TextField TFaddress;

        @FXML
        private DatePicker DPdated;

        @FXML
        private DatePicker DPdatef;

        @FXML
        private TextField TFnbrmatch;

        @FXML
        private TextField TFnom;

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
                                        Parent root = FXMLLoader.load(getClass().getResource("/AfficherMatch.fxml"));

                                        // Create a Scene with custom dimensions
                                        Scene scene = new Scene(root); // Adjust width and height as needed

                                        // Get the current stage
                                        Stage stage = (Stage) TFnom.getScene().getWindow();

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
                        System.out.println("Tournois List: " + tournoisList); // Print the list


                TFnbrmatch.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("\\d*")) {
                                TFnbrmatch.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                });

                        for (Tournois tournois : tournoisList) {
                                System.out.println("Adding tournois to TitledPane: " + tournois);
                                // Create layout for each reclamation
                                Label nomtLabel = new Label("Nom: " + tournois.getNom_tournois());
                                Label addressLabel = new Label("Address: " + tournois.getAddress_tournois());
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
                                                selectedIdt = "" + tournois.getId_tournois();
                                                selectedNomt = tournois.getNom_tournois();
                                                selectedAddress = tournois.getAddress_tournois();
                                                selectedNombrem = String.valueOf(tournois.getNombre_match());
                                                selectedDated = String.valueOf(tournois.getDate_debut());
                                                selectedDatef = String.valueOf(tournois.getDate_fin());

                                                // Perform any action with the selected values
                                                System.out.println("Selected ID: " + selectedIdt);
                                                System.out.println("Selected Nom: " + selectedNomt);
                                                System.out.println("Selected Address: " + selectedAddress);
                                                System.out.println("Selected Nombre Matchs: " + selectedNombrem);
                                                System.out.println("Selected Date Debut: " + selectedDated);
                                                System.out.println("Selected Date Fin: " + selectedDatef);

                                                TFnom.setText(selectedNomt);
                                                TFaddress.setText(selectedAddress);
                                                TFnbrmatch.setText(selectedNombrem);
                                                LocalDate dateDebut = Date.valueOf(selectedDated).toLocalDate();
                                                DPdated.setValue(dateDebut);
                                                LocalDate dateFin = Date.valueOf(selectedDatef).toLocalDate();
                                                DPdatef.setValue(dateFin);

                                        }
                                });


                                Vbox.getChildren().add(titledPane);
                        }
                        loadData();

                        Bsupprimer.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                        String nomText = TFnom.getText();
                                        String addressText = TFaddress.getText();
                                        String nbrMatchText = TFnbrmatch.getText();
                                        LocalDate dateD = DPdated.getValue();
                                        LocalDate dateF = DPdatef.getValue();
                                        if (nomText.isEmpty() || addressText.isEmpty() || nbrMatchText.isEmpty() || dateD == null || dateF == null || dateD.isEqual(dateF)) {
                                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                                alert.setTitle("Information manquante!");
                                                alert.setHeaderText("Gestion Des Tournois");
                                                alert.setContentText("Veuillez compléter tous les champs et assurez-vous que les dates de début et de fin sont différentes.");
                                                alert.showAndWait();
                                                return;
                                        }

                                        if (selectedIdt != null) {
                                                serviceTournois.supprimer(Integer.parseInt(selectedIdt));
                                                loadData();
                                                LocalDate t = null;
                                                TFnom.setText("");
                                                TFaddress.setText("");
                                                TFnbrmatch.setText("");
                                                DPdated.setValue(t);
                                                DPdatef.setValue(t);
                                                TFnom.requestFocus();
                                        }
                                }

                        });

                        Bmodifier.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                        String nomText = TFnom.getText();
                                        String addressText = TFaddress.getText();
                                        String nbrMatchText = TFnbrmatch.getText();
                                        LocalDate dateD = DPdated.getValue();
                                        LocalDate dateF = DPdatef.getValue();
                                        if (nomText.isEmpty() || addressText.isEmpty() || nbrMatchText.isEmpty() || dateD == null || dateF == null || dateD.isEqual(dateF)) {
                                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                                alert.setTitle("Information manquante!");
                                                alert.setHeaderText("Gestion Des Tournois");
                                                alert.setContentText("Veuillez compléter tous les champs et assurez-vous que les dates de début et de fin sont différentes.");
                                                alert.showAndWait();
                                                return;
                                        }

                                        if (selectedIdt != null) {

                                                String nbrMText = TFnbrmatch.getText();
                                                int nbrMatch = 0;
                                                String dateDText = String.valueOf(DPdated.getValue());
                                                java.util.Date dateDebut = null;
                                                String dateFText = String.valueOf(DPdatef.getValue());
                                                java.util.Date dateFin = null;
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                try {
                                                        nbrMatch = Integer.parseInt(nbrMText);
                                                } catch (NumberFormatException e) {
                                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                                        alert.setTitle("Erreur de format");
                                                        alert.setContentText("Le nombre de matchs doit être un entier valide.");
                                                        alert.showAndWait();
                                                        return;
                                                }
                                                try {
                                                        dateDebut = sdf.parse(dateDText);
                                                        dateFin = sdf.parse(dateFText);
                                                } catch (ParseException e) {
                                                        Alert alert = new Alert(Alert.AlertType.ERROR);
                                                        alert.setTitle("Format de date invalide");
                                                        alert.setHeaderText("Format de date incorrect");
                                                        alert.setContentText("Le format de date doit être 'yyyy-MM-dd'.");
                                                        alert.showAndWait();

                                                }
                                                Date sqlDateD = new Date(dateDebut.getTime());
                                                Date sqlDateF = new Date(dateFin.getTime());
                                                serviceTournois.modifier(new Tournois(Integer.parseInt(selectedIdt),TFnom.getText(), TFaddress.getText(), nbrMatch, sqlDateD, sqlDateF));
                                                loadData();
                                                LocalDate t = null;
                                                TFnom.setText("");
                                                TFaddress.setText("");
                                                TFnbrmatch.setText("");
                                                DPdated.setValue(t);
                                                DPdatef.setValue(t);
                                                TFnom.requestFocus();
                                        }
                                }


                        });



        }

        @FXML
        void ajouterTournoisAction(ActionEvent event) {
                String nomText = TFnom.getText();
                String addressText = TFaddress.getText();
                String nbrMatchText = TFnbrmatch.getText();
                LocalDate dateD = DPdated.getValue();
                LocalDate dateF = DPdatef.getValue();

                if (nomText.isEmpty() || addressText.isEmpty() || nbrMatchText.isEmpty() || dateD == null || dateF == null || dateD.isEqual(dateF)) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Information manquante!");
                        alert.setHeaderText("Gestion Des Tournois");
                        alert.setContentText("Veuillez compléter tous les champs et assurez-vous que les dates de début et de fin sont différentes.");
                        alert.showAndWait();
                        return;
                }
                int nbrMatch = 0;
                try {
                        nbrMatch = Integer.parseInt(nbrMatchText);
                } catch (NumberFormatException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Erreur de format");
                        alert.setContentText("Le nombre de tournois doit être un entier valide.");
                        alert.showAndWait();
                        return;
                }
                String dateDText = String.valueOf(DPdated.getValue());
                java.util.Date dateDebut = null;
                String dateFText = String.valueOf(DPdatef.getValue());
                java.util.Date dateFin = null;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                        dateDebut = sdf.parse(dateDText);
                        dateFin = sdf.parse(dateFText);
                } catch (ParseException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Format de date invalide");
                        alert.setHeaderText("Format de date incorrect");
                        alert.setContentText("Le format de date doit être 'yyyy-MM-dd'.");
                        alert.showAndWait();

                }
                Date sqlDateD = new Date(dateDebut.getTime());
                Date sqlDateF = new Date(dateFin.getTime());

                ServiceTournois st = new ServiceTournois();
                st.ajouter(new Tournois(nomText, addressText, nbrMatch, sqlDateD, sqlDateF));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Tournois ajouté!");
                alert.showAndWait();

                loadData();

                TFnom.clear();
                TFaddress.clear();
                TFnbrmatch.clear();
                DPdated.setValue(null);
                DPdatef.setValue(null);
                TFnom.requestFocus();

        }


        private void loadData() {
                Vbox.getChildren().clear(); // Clear existing display
                List<Tournois> tournoisList = serviceTournois.getAll();
                System.out.println("Tournois List: " + tournoisList); // Print the list

                for (Tournois tournois : tournoisList) {
                        System.out.println("Adding tournois to TitledPane: " + tournois);
                        // Create layout for each reclamation
                        Label nomtLabel = new Label("Nom: " + tournois.getNom_tournois());
                        Label addressLabel = new Label("Address: " + tournois.getAddress_tournois());
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
                                        selectedIdt = "" + tournois.getId_tournois();
                                        selectedNomt = tournois.getNom_tournois();
                                        selectedAddress = tournois.getAddress_tournois();
                                        selectedNombrem = String.valueOf(tournois.getNombre_match());
                                        selectedDated = String.valueOf(tournois.getDate_debut());
                                        selectedDatef = String.valueOf(tournois.getDate_fin());

                                        // Perform any action with the selected values
                                        System.out.println("Selected ID: " + selectedIdt);
                                        System.out.println("Selected Nom: " + selectedNomt);
                                        System.out.println("Selected Address: " + selectedAddress);
                                        System.out.println("Selected Nombre Matchs: " + selectedNombrem);
                                        System.out.println("Selected Date Debut: " + selectedDated);
                                        System.out.println("Selected Date Fin: " + selectedDatef);

                                        TFnom.setText(selectedNomt);
                                        TFaddress.setText(selectedAddress);
                                        TFnbrmatch.setText(selectedNombrem);
                                        LocalDate dateDebut = Date.valueOf(selectedDated).toLocalDate();
                                        DPdated.setValue(dateDebut);
                                        LocalDate dateFin = Date.valueOf(selectedDatef).toLocalDate();
                                        DPdatef.setValue(dateFin);

                                }
                        });


                        Vbox.getChildren().add(titledPane);
                }


        }





}
