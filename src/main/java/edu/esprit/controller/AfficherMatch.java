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
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherMatch implements Initializable {

    @FXML
    private Button Bmodm;

    @FXML
    private Button Bnavigatem;

    @FXML
    private ComboBox<String> CBmatch;

    @FXML
    private Button Bnavigatestats;

    @FXML
    private Button Bsuppm;

    @FXML
    private Button Bnavigatemail;

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
    private TextField TFrecherchem;

    @FXML
    private TextField TFtournois;

    @FXML
    private VBox Vboxx;

    ServiceMatch serviceMatch = new ServiceMatch();

    private String selectedIdm;
    private String selectedNomm;
    private String selectedDate;
    private String selectedDuree;
    private String selectedTournois;
    private String selectedEquipe1;
    private String selectedEquipe2;

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

        Bnavigatemail.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/SendMail.fxml"));

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

        Bnavigatestats.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/Statistique.fxml"));

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

        TFnomm.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[a-zA-Z0-9 ]*")) {
                TFnomm.setText(newValue.replaceAll("[^a-zA-Z0-9 ]", ""));
            }
        });

        for (Matchs matchs : matchsList) {
            System.out.println("Adding match to TitledPane: " + matchs);
            // Create layout for each match
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
                    selectedTournois = matchs.getTournois().getNom_tournois();
                    selectedEquipe1 = matchs.getEquipe().getNom_equipe();
                    selectedEquipe2 = matchs.getEquipe1().getNom_equipe();

                    // Perform any action with the selected values
                    System.out.println("Selected ID: " + selectedIdm);
                    System.out.println("Selected Nom: " + selectedNomm);
                    System.out.println("Selected Date: " + selectedDate);
                    System.out.println("Selected Duree: " + selectedDuree);
                    System.out.println("Selected Tournois: " + selectedTournois);
                    System.out.println("Selected Equipe 1: " + selectedEquipe1);
                    System.out.println("Selected Equipe 2: " + selectedEquipe2);


                    TFnomm.setText(selectedNomm);
                    LocalDate dateMatch = Date.valueOf(selectedDate).toLocalDate();
                    DPdatem.setValue(dateMatch);
                    TFdureem.setText(selectedDuree);
                    TFtournois.setText(selectedTournois);
                    TFequipe1.setText(selectedEquipe1);
                    TFequipe2.setText(selectedEquipe2);

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
                    String dateMText = String.valueOf(DPdatem.getValue());
                    java.util.Date dateM;
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    try {
                        dateM = sdf.parse(dateMText);
                    } catch (ParseException e) {
                        throw new RuntimeException(e);
                    }

                    Tournois tournois = new Tournois();
                    tournois.setNom_tournoi(tournoisText);
                    Equipe equipe1 = new Equipe();
                    Equipe equipe2 = new Equipe();
                    equipe1.setNom_equipe(equipe1Text);
                    equipe2.setNom_equipe(equipe2Text);
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

        CBmatch.getItems().addAll("Nom Matchs", "Date Match");
        CBmatch.setValue("Nom Tournoi");
        CBmatch.setOnAction(event -> {
            String selectedOption = CBmatch.getValue();
            // Trier les tournois en fonction de l'option sélectionnée dans le ComboBox
            if (selectedOption.equals("Nom Matchs")) {
                matchsList.sort(Comparator.comparing(Matchs::getNom_match));
            } else if (selectedOption.equals("Date Match")) {
                matchsList.sort(Comparator.comparing(Matchs::getDate_match));
            }

            // Afficher les tournois triés dans les TitledPane
            Vboxx.getChildren().clear();
            for (Matchs matchs : matchsList) {
                // Créer et ajouter les TitledPane avec les matchs triés
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
        });

        TFrecherchem.setOnAction(event -> advancedSearchMatch());

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


        java.util.Date dateM = java.sql.Date.valueOf(date);

        Equipe equipe1 = new Equipe();
        Equipe equipe2 = new Equipe();

        equipe1.setNom_equipe(equipe1Text);
        equipe2.setNom_equipe(equipe2Text);

        Tournois tournois = new Tournois();
        tournois.setNom_tournoi(tournoisText);

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

    @FXML
    void pdfMatchAction(ActionEvent event) {
        String nomText = TFnomm.getText();
        String dureeText = TFdureem.getText();
        String tournoisText = TFtournois.getText();
        String equipe1Text = TFequipe1.getText();
        String equipe2Text = TFequipe2.getText();
        String date = String.valueOf(DPdatem.getValue());
        java.util.Date dateMatch = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateMatch = sdf.parse(date);
        } catch (ParseException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Format de date invalide");
            alert.setHeaderText("Format de date incorrect");
            alert.setContentText("Le format de date doit être 'yyyy-MM-dd'.");
            alert.showAndWait();

        }
        if (selectedIdm != null) {
            try {
                // Création d'un document PDF
                PDDocument document = new PDDocument();
                PDPage page = new PDPage();
                document.addPage(page);

                // Ajout de contenu au document
                PDPageContentStream contentStream = new PDPageContentStream(document, page);
                // Créer un objet PDColor avec les valeurs RVB
                PDColor greenColor = new PDColor(new float[]{104/255f, 138/255f, 56/255f}, PDDeviceRGB.INSTANCE);
                PDColor couleur = new PDColor(new float[]{195 / 255f, 207 / 255f, 182 / 255f}, PDDeviceRGB.INSTANCE);
                PDColor color = new PDColor(new float[]{31 / 255f, 61 / 255f, 28 / 255f}, PDDeviceRGB.INSTANCE);

                contentStream.setNonStrokingColor(greenColor);
                // Dessine un rectangle rempli de la couleur de fond
                contentStream.fillRect(0, 0, (int) page.getMediaBox().getWidth(), (int) page.getMediaBox().getHeight());
                //Ajouter Image
                PDImageXObject pdImage = PDImageXObject.createFromFile("C:\\Users\\msi\\IdeaProjects\\GestionTournois\\src\\main\\resources\\Image\\logopi.png", document);
                contentStream.drawImage(pdImage, 100, 50);

                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 18);
                contentStream.beginText();
                contentStream.newLineAtOffset(100, 700);
                contentStream.moveTextPositionByAmount(0, -30);
                contentStream.setNonStrokingColor(couleur);
                contentStream.showText("Venez participer à notre Match et gagnez des recomponse.");

                contentStream.moveTextPositionByAmount(0, -40);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                // Définir la couleur de non-contour
                contentStream.setNonStrokingColor(couleur);
                contentStream.showText("Nom: ");
                contentStream.setFont(PDType1Font.HELVETICA, 15); // Revenir à une police normale pour le reste du texte
                // Définir la couleur de non-contour
                contentStream.setNonStrokingColor(color);
                contentStream.showText(nomText);

                contentStream.moveTextPositionByAmount(0, -30);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                // Définir la couleur de non-contour
                contentStream.setNonStrokingColor(couleur);
                contentStream.showText("Date du match:");
                contentStream.setFont(PDType1Font.HELVETICA, 15); // Revenir à une police normale pour le reste du texte
                // Définir la couleur de non-contour
                contentStream.setNonStrokingColor(color);
                contentStream.showText(String.valueOf(dateMatch));

                contentStream.moveTextPositionByAmount(0, -30);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                // Définir la couleur de non-contour
                contentStream.setNonStrokingColor(couleur);
                contentStream.showText("Duree:");
                contentStream.setFont(PDType1Font.HELVETICA, 15); // Revenir à une police normale pour le reste du texte
                // Définir la couleur de non-contour
                contentStream.setNonStrokingColor(color);
                contentStream.showText(dureeText);

                contentStream.moveTextPositionByAmount(0, -30);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                // Définir la couleur de non-contour
                contentStream.setNonStrokingColor(couleur);
                contentStream.showText("Nom du tournois:");
                contentStream.setFont(PDType1Font.HELVETICA, 15); // Revenir à une police normale pour le reste du texte
                // Définir la couleur de non-contour
                contentStream.setNonStrokingColor(color);
                contentStream.showText(tournoisText);

                contentStream.moveTextPositionByAmount(0, -30);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                // Définir la couleur de non-contour
                contentStream.setNonStrokingColor(couleur);
                contentStream.showText("Equipe 1:");
                contentStream.setFont(PDType1Font.HELVETICA, 15); // Revenir à une police normale pour le reste du texte
                // Définir la couleur de non-contour
                contentStream.setNonStrokingColor(color);
                contentStream.showText(equipe1Text);

                contentStream.moveTextPositionByAmount(0, -30);
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                // Définir la couleur de non-contour
                contentStream.setNonStrokingColor(couleur);
                contentStream.showText("Equipe 2:");
                contentStream.setFont(PDType1Font.HELVETICA, 15); // Revenir à une police normale pour le reste du texte
                // Définir la couleur de non-contour
                contentStream.setNonStrokingColor(color);
                contentStream.showText(equipe2Text);

                contentStream.moveTextPositionByAmount(0, -30);
                contentStream.setFont(PDType1Font.HELVETICA, 15); // Revenir à une police normale pour le reste du texte
                // Définir la couleur de non-contour
                contentStream.setNonStrokingColor(color);
                contentStream.showText("Notre site web: www.TourneyPulse.com");

                contentStream.endText();
                contentStream.close();

                // Enregistrement du document
                String filePath = "C:\\Users\\msi\\OneDrive\\Bureau\\match.pdf";
                document.save(filePath);
                document.close();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Gestion Des Matchs");

                alert.setHeaderText("Gestion Des Matchs");
                alert.setContentText("Fichier PDF créé avec succès : " + filePath);
                alert.showAndWait();
            } catch (IOException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur");
                alert.setContentText("Erreur lors de la création du fichier PDF : " + e.getMessage());
                alert.showAndWait();
            }
            TFnomm.clear();
            TFdureem.clear();
            TFtournois.clear();
            TFequipe1.clear();
            TFequipe2.clear();
            DPdatem.setValue(null);
            TFnomm.requestFocus();
        }

    }

    @FXML
    void refrechMatchAction(ActionEvent event) {
        loadData();
        TFrecherchem.clear();
    }

    private void advancedSearchMatch() {
        List<Matchs> matchsList = serviceMatch.getAll();
        System.out.println("Match List: " + matchsList);
        String searchText = TFrecherchem.getText().trim().toLowerCase(); // Récupérez le texte de recherche
        Vboxx.getChildren().clear(); // Effacez les résultats précédents

        // Parcourez la liste des matchs et recherchez ceux correspondant au critère de recherche
        for (Matchs matchs : matchsList) {
            boolean matchsSearch = false;
            if (matchs.getNom_match().toLowerCase().contains(searchText) ||
                    matchs.getTournois().getNom_tournois().toLowerCase().contains(searchText)) {
                matchsSearch = true;
                }
                if(matchsSearch){
                // Si les critères de recherche correspondent, affichez le match
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
            // Create layout for each match
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
                    selectedTournois = matchs.getTournois().getNom_tournois();
                    selectedEquipe1 = matchs.getEquipe().getNom_equipe();
                    selectedEquipe2 = matchs.getEquipe1().getNom_equipe();

                    // Perform any action with the selected values
                    System.out.println("Selected ID: " + selectedIdm);
                    System.out.println("Selected Nom: " + selectedNomm);
                    System.out.println("Selected Date: " + selectedDate);
                    System.out.println("Selected Duree: " + selectedDuree);
                    System.out.println("Selected Tournois: " + selectedTournois);
                    System.out.println("Selected Equipe 1: " + selectedEquipe1);
                    System.out.println("Selected Equipe 2: " + selectedEquipe2);


                    TFnomm.setText(selectedNomm);
                    LocalDate dateMatch = Date.valueOf(selectedDate).toLocalDate();
                    DPdatem.setValue(dateMatch);
                    TFdureem.setText(selectedDuree);
                    TFtournois.setText(selectedTournois);
                    TFequipe1.setText(selectedEquipe1);
                    TFequipe2.setText(selectedEquipe2);

                }
            });


            Vboxx.getChildren().add(titledPane);
        }
    }

}
