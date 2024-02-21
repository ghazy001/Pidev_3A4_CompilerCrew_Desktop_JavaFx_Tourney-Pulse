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
        private DatePicker TFdated;

        @FXML
        private DatePicker TFdatef;

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
                                        Scene scene = new Scene(root, 800, 600); // Adjust width and height as needed

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

                        for (Tournois tournois : tournoisList) {
                                System.out.println("Adding tournois to TitledPane: " + tournois);
                                // Create layout for each reclamation
                                Label idtLabel = new Label("ID: " + tournois.getId_tournois());
                                Label nomtLabel = new Label("Nom: " + tournois.getNom_tournois());
                                Label addressLabel = new Label("Address: " + tournois.getAddress_tournois());
                                Label nombremLabel = new Label("Nombre Match: " + tournois.getNombre_match());
                                Label datedLabel = new Label("Date Debut: " + tournois.getDate_debut());
                                Label datefLabel = new Label("Date Fin: " + tournois.getDate_fin());

                                GridPane gridPane = new GridPane();
                                gridPane.add(idtLabel, 0, 0);
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
                                                selectedNomt = "" + tournois.getNom_tournois();
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
                                                TFdated.setValue(dateDebut);
                                                LocalDate dateFin = Date.valueOf(selectedDatef).toLocalDate();
                                                TFdatef.setValue(dateFin);

                                        }
                                });


                                Vbox.getChildren().add(titledPane);
                        }
                        loadData();

                        Bsupprimer.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                        if (selectedIdt != null) {
                                                serviceTournois.supprimer(Integer.parseInt(selectedIdt));
                                                loadData();
                                        }
                                }

                        });

                        Bmodifier.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {

                                        if (selectedIdt != null) {
                                                String nbrMatchText = TFnbrmatch.getText();
                                                int nbrMatch;
                                                String dateDText = String.valueOf(TFdated.getValue());
                                                java.util.Date dateD;
                                                String dateFText = String.valueOf(TFdatef.getValue());
                                                java.util.Date dateF;
                                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                                                try {
                                                        nbrMatch = Integer.parseInt(nbrMatchText);
                                                        dateD = sdf.parse(dateDText);
                                                        dateF = sdf.parse(dateFText);
                                                } catch (ParseException e) {
                                                        throw new RuntimeException(e);
                                                }
                                                Date sqlDateD = new Date(dateD.getTime());
                                                Date sqlDateF = new Date(dateF.getTime());
                                                serviceTournois.modifier(new Tournois(TFnom.getText(), TFaddress.getText(), nbrMatch, sqlDateD, sqlDateF));
                                                loadData();
                                        }
                                }


                        });



        }

        @FXML
        void ajouterTournoisAction(ActionEvent event) {
                String nbrMatchText = TFnbrmatch.getText();
                int nbrMatch;
                String dateDText = String.valueOf(TFdated.getValue());
                java.util.Date dateD;
                String dateFText = String.valueOf(TFdatef.getValue());
                java.util.Date dateF;
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                try {
                        nbrMatch = Integer.parseInt(nbrMatchText);
                        dateD = sdf.parse(dateDText);
                        dateF = sdf.parse(dateFText);
                        Date sqlDateD = new Date(dateD.getTime());
                        Date sqlDateF = new Date(dateF.getTime());

                        ServiceTournois st = new ServiceTournois();

                        if (!TFnom.getText().isEmpty() || !TFaddress.getText().isEmpty() || !TFnbrmatch.getText().isEmpty() || !TFdated.getValue().isEqual(TFdatef.getValue()) || !TFdatef.getValue().isEqual(TFdated.getValue())) {
                                st.ajouter(new Tournois(TFnom.getText(), TFaddress.getText(), nbrMatch, sqlDateD, sqlDateF));
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Success");
                                alert.setContentText("Tournois ajouter!");
                                alert.show();
                                loadData();


                            LocalDate t = null;
                                TFnom.setText("");
                                TFaddress.setText("");
                                TFnbrmatch.setText("");
                                TFdated.setValue(t);
                                TFdatef.setValue(t);
                                TFnom.requestFocus();
                        } else {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Information manquante!");
                                alert.setContentText("Veuillez completez les champs manquant pour le match.");
                                alert.show();
                        }
                } catch (ParseException e) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Parse Exception");
                        alert.setContentText("Error parsing date: " + e.getMessage());
                        alert.showAndWait();
                }

        }


        private void loadData() {
                Vbox.getChildren().clear(); // Clear existing display
                List<Tournois> tournoisList = serviceTournois.getAll();
                System.out.println("Tournois List: " + tournoisList); // Print the list

                for (Tournois tournois : tournoisList) {
                        System.out.println("Adding tournois to TitledPane: " + tournois);
                        // Create layout for each reclamation
                        Label idtLabel = new Label("ID: " + tournois.getId_tournois());
                        Label nomtLabel = new Label("Nom: " + tournois.getNom_tournois());
                        Label addressLabel = new Label("Address: " + tournois.getAddress_tournois());
                        Label nombremLabel = new Label("Nombre Match: " + tournois.getNombre_match());
                        Label datedLabel = new Label("Date Debut: " + tournois.getDate_debut());
                        Label datefLabel = new Label("Date Fin: " + tournois.getDate_fin());

                        GridPane gridPane = new GridPane();
                        gridPane.add(idtLabel, 0, 0);
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
                                        selectedNomt = "" + tournois.getNom_tournois();
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
                                        TFdated.setValue(dateDebut);
                                        LocalDate dateFin = Date.valueOf(selectedDatef).toLocalDate();
                                        TFdatef.setValue(dateFin);

                                }
                        });


                        Vbox.getChildren().add(titledPane);
                }


        }





}
