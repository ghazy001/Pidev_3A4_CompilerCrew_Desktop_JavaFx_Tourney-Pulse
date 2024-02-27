package edu.esprit.controller;

import edu.esprit.entities.Equipe;
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
    private DatePicker DPdatem;

    @FXML
    private TextField TFdureem;

    @FXML
    private TextField TFequipe1;

    @FXML
    private TextField TFequipe2;

    @FXML
    private TextField TFnomm;

    @FXML
    private TextField TFtournois;

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
                    Parent root = FXMLLoader.load(getClass().getResource("/AfficherTournois.fxml"));

                    // Create a Scene with custom dimensions
                    Scene scene = new Scene(root); // Adjust width and height as needed

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
            Label nommLabel = new Label("Nom: " + matchs.getNom_match());
            Label dateLabel = new Label("Date: " + matchs.getDate_match());
            Label dureeLabel = new Label("Duree: " + matchs.getDuree_match());
            Label idtLabel = new Label("Nom Tournois: " + matchs.getTournois().getNom_tournois());
            Label ideLabel1 = new Label("Nom Equipe 1: " + matchs.getEquipe().getNom_equipe());
            Label ideLabel2 = new Label("Nom Equipe 2: " + matchs.getEquipe1().getNom_equipe());


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
                    selectedIdm = "" + matchs.getId_match();
                    selectedNomm = matchs.getNom_match();
                    selectedDate = String.valueOf(matchs.getDate_match());
                    selectedDuree = matchs.getDuree_match();
                    selectedIdt = String.valueOf(matchs.getTournois().getId_tournois());
                    selectedIde1 = String.valueOf(matchs.getEquipe().getId_equipe());
                    selectedIde2 = String.valueOf(matchs.getEquipe1().getId_equipe());

                    // Perform any action with the selected values
                    System.out.println("Selected ID: " + selectedIdm);
                    System.out.println("Selected Nom: " + selectedNomm);
                    System.out.println("Selected Date: " + selectedDate);
                    System.out.println("Selected Duree: " + selectedDuree);
                    System.out.println("Selected ID Tournois: " + selectedIdt);
                    System.out.println("Selected ID Equipe 1: " + selectedIde1);
                    System.out.println("Selected ID Equipe 2: " + selectedIde2);


                    TFnomm.setText(selectedNomm);
                    LocalDate dateMatch = Date.valueOf(selectedDate).toLocalDate();
                    DPdatem.setValue(dateMatch);
                    TFdureem.setText(selectedDuree);
                    TFtournois.setText(selectedIdt);
                    TFequipe1.setText(selectedIde1);
                    TFequipe2.setText(selectedIde2);

                }
            });


            Vboxx.getChildren().add(titledPane);
        }
        loadData();

        Bsuppm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String nomText = TFnomm.getText();
                String dureeText = TFdureem.getText();
                String tournoisText = TFtournois.getText();
                String equipe1Text = TFequipe1.getText();
                String equipe2Text = TFequipe2.getText();
                LocalDate date = DPdatem.getValue();

                if (nomText.isEmpty() || dureeText.isEmpty() || tournoisText.isEmpty() || equipe1Text.isEmpty() || equipe2Text.isEmpty() || date == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information manquante!");
                    alert.setHeaderText("Gestion Des Matchs");
                    alert.setContentText("Veuillez compléter tous les champs.");
                    alert.showAndWait();
                    return;
                }

                if (selectedIdm != null) {
                    serviceMatch.supprimer(Integer.parseInt(selectedIdm));
                    loadData();
                    LocalDate t = null;
                    TFnomm.setText("");
                    DPdatem.setValue(t);
                    TFdureem.setText("");
                    TFtournois.setText("");
                    TFequipe1.setText("");
                    TFequipe2.setText("");
                    TFnomm.requestFocus();
                }
            }

        });

        Bmodm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                String nomText = TFnomm.getText();
                String dureeText = TFdureem.getText();
                String tournoisText = TFtournois.getText();
                String equipe1Text = TFequipe1.getText();
                String equipe2Text = TFequipe2.getText();
                LocalDate date = DPdatem.getValue();

                if (nomText.isEmpty() || dureeText.isEmpty() || tournoisText.isEmpty() || equipe1Text.isEmpty() || equipe2Text.isEmpty() || date == null) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Information manquante!");
                    alert.setHeaderText("Gestion Des Matchs");
                    alert.setContentText("Veuillez compléter tous les champs.");
                    alert.showAndWait();
                    return;
                }

                if (selectedIdm != null) {
                    int idTourText;
                    int idEquipeText1;
                    int idEquipeText2;
                    String dateMText = String.valueOf(DPdatem.getValue());
                    java.util.Date dateM;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        idTourText = Integer.parseInt(TFtournois.getText());
                        idEquipeText1 = Integer.parseInt(TFequipe1.getText());
                        idEquipeText2 = Integer.parseInt(TFequipe2.getText());
                        dateM = sdf.parse(dateMText);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    Tournois tournois = new Tournois();
                    tournois.setId_tournois(idTourText);
                    Equipe equipe1 = new Equipe();
                    Equipe equipe2 = new Equipe();
                    equipe1.setId_equipe(idEquipeText1);
                    equipe2.setId_equipe(idEquipeText2);
                    Date sqlDateM = new Date(dateM.getTime());
                    serviceMatch.modifier(new Matchs(Integer.parseInt(selectedIdm),TFnomm.getText(), sqlDateM, TFdureem.getText(), tournois, equipe1, equipe2));
                    loadData();
                    LocalDate t = null;
                    TFnomm.setText("");
                    DPdatem.setValue(t);
                    TFdureem.setText("");
                    TFtournois.setText("");
                    TFequipe1.setText("");
                    TFequipe2.setText("");
                    TFnomm.requestFocus();
                }
            }


        });

    }

    @FXML
    void ajouterMatchsAction(ActionEvent event) {
        String nomText = TFnomm.getText();
        String dureeText = TFdureem.getText();
        String tournoisText = TFtournois.getText();
        String equipe1Text = TFequipe1.getText();
        String equipe2Text = TFequipe2.getText();
        LocalDate date = DPdatem.getValue();

        if (nomText.isEmpty() || dureeText.isEmpty() || tournoisText.isEmpty() || equipe1Text.isEmpty() || equipe2Text.isEmpty() || date == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Information manquante!");
            alert.setHeaderText("Gestion Des Matchs");
            alert.setContentText("Veuillez compléter tous les champs.");
            alert.showAndWait();
            return;
        }

        int idTour;
        int idequipe1;
        int idequipe2;

        try {
            idTour = Integer.parseInt(tournoisText);
            idequipe1 = Integer.parseInt(equipe1Text);
            idequipe2 = Integer.parseInt(equipe2Text);
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de format");
            alert.setContentText("Les ID du tournois et des équipes doivent être des entiers valides.");
            alert.showAndWait();
            return;
        }

        java.util.Date dateM = java.sql.Date.valueOf(date);

        Equipe equipe1 = new Equipe();
        Equipe equipe2 = new Equipe();

        equipe1.setId_equipe(idequipe1);
        equipe2.setId_equipe(idequipe2);

        Tournois tournois = new Tournois();
        tournois.setId_tournois(idTour);

        ServiceMatch sm = new ServiceMatch();
        sm.ajouter(new Matchs(nomText, dateM, dureeText, tournois, equipe1, equipe2));

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setContentText("Match ajouté!");
        alert.showAndWait();

        loadData();

        TFnomm.clear();
        TFdureem.clear();
        TFtournois.clear();
        TFequipe1.clear();
        TFequipe2.clear();
        DPdatem.setValue(null);
        TFnomm.requestFocus();
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
                    selectedIdm = "" + matchs.getId_match();
                    selectedNomm = matchs.getNom_match();
                    selectedDate = String.valueOf(matchs.getDate_match());
                    selectedDuree = matchs.getDuree_match();
                    selectedIdt = String.valueOf(matchs.getTournois().getId_tournois());
                    selectedIde1 = String.valueOf(matchs.getEquipe().getId_equipe());
                    selectedIde2 = String.valueOf(matchs.getEquipe1().getId_equipe());

                    // Perform any action with the selected values
                    System.out.println("Selected ID: " + selectedIdm);
                    System.out.println("Selected Nom: " + selectedNomm);
                    System.out.println("Selected Date: " + selectedDate);
                    System.out.println("Selected Duree: " + selectedDuree);
                    System.out.println("Selected ID Tournois: " + selectedIdt);
                    System.out.println("Selected ID Equipe 1: " + selectedIde1);
                    System.out.println("Selected ID Equipe 2: " + selectedIde2);


                    TFnomm.setText(selectedNomm);
                    LocalDate dateMatch = Date.valueOf(selectedDate).toLocalDate();
                    DPdatem.setValue(dateMatch);
                    TFdureem.setText(selectedDuree);
                    TFtournois.setText(selectedIdt);
                    TFequipe1.setText(selectedIde1);
                    TFequipe2.setText(selectedIde2);

                }
            });


            Vboxx.getChildren().add(titledPane);
        }
    }
}
