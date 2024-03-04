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

public class AfficherTournois implements Initializable {


        @FXML
        private Button Bmodifier;

        @FXML
        private Button Bnavigatet;

        @FXML
        private ComboBox<String> CBtournois;

        @FXML
        private Button Bnavigatestats;

        @FXML
        private Button Bnavigatemail;

        @FXML
        private Button Bsupprimer;

        @FXML
        private TextField TFstade;

        @FXML
        private DatePicker DPdated;

        @FXML
        private DatePicker DPdatef;

        @FXML
        private TextField TFnbrmatch;

        @FXML
        private TextField TFnomt;

        @FXML
        private TextField TFrecherchet;

        @FXML
        private VBox Vbox;

        ServiceTournois serviceTournois = new ServiceTournois();

        private String selectedIdt;
        private String selectedNomt;
        private String selectedStade;
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
                                        Stage stage = (Stage) TFnomt.getScene().getWindow();

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
                                        Stage stage = (Stage) TFnomt.getScene().getWindow();

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
                                        Stage stage = (Stage) TFnomt.getScene().getWindow();

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
                TFnomt.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("[a-zA-Z0-9 ]*")) {
                                TFnomt.setText(newValue.replaceAll("[^a-zA-Z0-9 ]", ""));
                        }
                });
                TFstade.textProperty().addListener((observable, oldValue, newValue) -> {
                        if (!newValue.matches("[a-zA-Z0-9 ]*")) {
                                TFstade.setText(newValue.replaceAll("[^a-zA-Z0-9 ]", ""));
                        }
                });

                        for (Tournois tournois : tournoisList) {
                                System.out.println("Adding tournois to TitledPane: " + tournois);
                                // Create layout for each tournois
                                Label nomtLabel = new Label("Nom: " + tournois.getNom_tournois());
                                Label stadeLabel = new Label("Nom Stade: " + tournois.getStade());
                                Label nombremLabel = new Label("Nombre Match: " + tournois.getNombre_match());
                                Label datedLabel = new Label("Date Debut: " + tournois.getDate_debut());
                                Label datefLabel = new Label("Date Fin: " + tournois.getDate_fin());

                                GridPane gridPane = new GridPane();
                                gridPane.add(nomtLabel, 0, 1);
                                gridPane.add(stadeLabel, 0, 2);
                                gridPane.add(nombremLabel, 0, 3);
                                gridPane.add(datedLabel, 0, 4);
                                gridPane.add(datefLabel, 0, 5);

                                TitledPane titledPane = new TitledPane("Tournois " + tournois.getId_tournois(), gridPane);


                                titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                        @Override
                                        public void handle(MouseEvent mouseEvent) {
                                                selectedIdt = "" + tournois.getId_tournois();
                                                selectedNomt = tournois.getNom_tournois();
                                                selectedStade = tournois.getStade();
                                                selectedNombrem = String.valueOf(tournois.getNombre_match());
                                                selectedDated = String.valueOf(tournois.getDate_debut());
                                                selectedDatef = String.valueOf(tournois.getDate_fin());

                                                // Perform any action with the selected values
                                                System.out.println("Selected ID: " + selectedIdt);
                                                System.out.println("Selected Nom: " + selectedNomt);
                                                System.out.println("Selected Stade: " + selectedStade);
                                                System.out.println("Selected Nombre Matchs: " + selectedNombrem);
                                                System.out.println("Selected Date Debut: " + selectedDated);
                                                System.out.println("Selected Date Fin: " + selectedDatef);

                                                TFnomt.setText(selectedNomt);
                                                TFstade.setText(selectedStade);
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
                                        String nomText = TFnomt.getText();
                                        String stadeText = TFstade.getText();
                                        String nbrMatchText = TFnbrmatch.getText();
                                        LocalDate dateD = DPdated.getValue();
                                        LocalDate dateF = DPdatef.getValue();
                                        if (nomText.isEmpty() || stadeText.isEmpty() || nbrMatchText.isEmpty() || dateD == null || dateF == null || dateD.isEqual(dateF)) {
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
                                                TFnomt.setText("");
                                                TFstade.setText("");
                                                TFnbrmatch.setText("");
                                                DPdated.setValue(t);
                                                DPdatef.setValue(t);
                                                TFnomt.requestFocus();
                                        }
                                }

                        });

                        Bmodifier.setOnAction(new EventHandler<ActionEvent>() {
                                @Override
                                public void handle(ActionEvent actionEvent) {
                                        String nomText = TFnomt.getText();
                                        String stadeText = TFstade.getText();
                                        String nbrMatchText = TFnbrmatch.getText();
                                        LocalDate dateD = DPdated.getValue();
                                        LocalDate dateF = DPdatef.getValue();
                                        if (nomText.isEmpty() || stadeText.isEmpty() || nbrMatchText.isEmpty() || dateD == null || dateF == null || dateD.isEqual(dateF)) {
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
                                                serviceTournois.modifier(new Tournois(Integer.parseInt(selectedIdt), TFnomt.getText(), TFstade.getText(), nbrMatch, sqlDateD, sqlDateF));
                                                loadData();
                                                LocalDate t = null;
                                                TFnomt.setText("");
                                                TFstade.setText("");
                                                TFnbrmatch.setText("");
                                                DPdated.setValue(t);
                                                DPdatef.setValue(t);
                                                TFnomt.requestFocus();
                                        }
                                }


                        });

                CBtournois.getItems().addAll("Nom Tournois", "Nombre Match", "Date Début", "Date Fin");
                CBtournois.setValue("Nom Tournoi");
                CBtournois.setOnAction(event -> {
                        String selectedOption = CBtournois.getValue();
                        // Trier les tournois en fonction de l'option sélectionnée dans le ComboBox
                        if (selectedOption.equals("Nom Tournois")) {
                                tournoisList.sort(Comparator.comparing(Tournois::getNom_tournois));
                        } else if (selectedOption.equals("Date Début")) {
                                tournoisList.sort(Comparator.comparing(Tournois::getDate_debut));
                        } else if (selectedOption.equals("Date Fin")) {
                                tournoisList.sort(Comparator.comparing(Tournois::getDate_fin));
                        } else if (selectedOption.equals("Nombre Match")) {
                                tournoisList.sort(Comparator.comparing(Tournois::getNombre_match));
                        }

                        // Afficher les tournois triés dans les TitledPane
                        Vbox.getChildren().clear();
                        for (Tournois tournois : tournoisList) {
                                // Créer et ajouter les TitledPane avec les tournois triés
                                Label nomtLabel = new Label("Nom: " + tournois.getNom_tournois());
                                Label stadeLabel = new Label("Nom Stade: " + tournois.getStade());
                                Label nombremLabel = new Label("Nombre Match: " + tournois.getNombre_match());
                                Label datedLabel = new Label("Date Début: " + tournois.getDate_debut());
                                Label datefLabel = new Label("Date Fin: " + tournois.getDate_fin());

                                GridPane gridPane = new GridPane();
                                gridPane.add(nomtLabel, 0, 1);
                                gridPane.add(stadeLabel, 0, 2);
                                gridPane.add(nombremLabel, 0, 3);
                                gridPane.add(datedLabel, 0, 4);
                                gridPane.add(datefLabel, 0, 5);

                                TitledPane titledPane = new TitledPane("Tournois " + tournois.getId_tournois(), gridPane);
                                Vbox.getChildren().add(titledPane);
                        }
                });

                        TFrecherchet.setOnAction(event -> advancedSearchTournaments());



        }

        @FXML
        void ajouterTournoisAction(ActionEvent event) {
                String nomText = TFnomt.getText();
                String stadeText = TFstade.getText();
                String nbrMatchText = TFnbrmatch.getText();
                LocalDate dateD = DPdated.getValue();
                LocalDate dateF = DPdatef.getValue();

                if (nomText.isEmpty() || stadeText.isEmpty() || nbrMatchText.isEmpty() || dateD == null || dateF == null || dateD.isEqual(dateF)) {
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
                st.ajouter(new Tournois(nomText, stadeText, nbrMatch, sqlDateD, sqlDateF));

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Tournois ajouté!");
                alert.showAndWait();

                loadData();

                TFnomt.clear();
                TFstade.clear();
                TFnbrmatch.clear();
                DPdated.setValue(null);
                DPdatef.setValue(null);
                TFnomt.requestFocus();

        }

        @FXML
        void pdfTournoisAction(ActionEvent event) {
                String nomText = TFnomt.getText();
                String stadeText = TFstade.getText();
                String nbrMatchText = TFnbrmatch.getText();
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
                if (selectedIdt != null) {
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
                                contentStream.moveTextPositionByAmount(0, -40);
                                contentStream.setNonStrokingColor(couleur);
                                contentStream.showText("Venez participer à notre tourois et gagnez des recomponse.");

                                contentStream.moveTextPositionByAmount(0, -80);
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                                // Définir la couleur de non-contour
                                contentStream.setNonStrokingColor(couleur);
                                contentStream.showText("Nom: ");
                                contentStream.setFont(PDType1Font.HELVETICA, 15); // Revenir à une police normale pour le reste du texte
                                // Définir la couleur de non-contour
                                contentStream.setNonStrokingColor(color);
                                contentStream.showText(nomText);

                                contentStream.moveTextPositionByAmount(0, -40);
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                                // Définir la couleur de non-contour
                                contentStream.setNonStrokingColor(couleur);
                                contentStream.showText("Stade:");
                                contentStream.setFont(PDType1Font.HELVETICA, 15); // Revenir à une police normale pour le reste du texte
                                // Définir la couleur de non-contour
                                contentStream.setNonStrokingColor(color);
                                contentStream.showText(stadeText);

                                contentStream.moveTextPositionByAmount(0, -40);
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                                // Définir la couleur de non-contour
                                contentStream.setNonStrokingColor(couleur);
                                contentStream.showText("Nombre Match:");
                                contentStream.setFont(PDType1Font.HELVETICA, 15); // Revenir à une police normale pour le reste du texte
                                // Définir la couleur de non-contour
                                contentStream.setNonStrokingColor(color);
                                contentStream.showText(nbrMatchText);

                                contentStream.moveTextPositionByAmount(0, -40);
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                                // Définir la couleur de non-contour
                                contentStream.setNonStrokingColor(couleur);
                                contentStream.showText("Date Debut:");
                                contentStream.setFont(PDType1Font.HELVETICA, 15); // Revenir à une police normale pour le reste du texte
                                // Définir la couleur de non-contour
                                contentStream.setNonStrokingColor(color);
                                contentStream.showText(String.valueOf(dateDebut));

                                contentStream.moveTextPositionByAmount(0, -40);
                                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);
                                // Définir la couleur de non-contour
                                contentStream.setNonStrokingColor(couleur);
                                contentStream.showText("Date Fin:");
                                contentStream.setFont(PDType1Font.HELVETICA, 15); // Revenir à une police normale pour le reste du texte
                                // Définir la couleur de non-contour
                                contentStream.setNonStrokingColor(color);
                                contentStream.showText(String.valueOf(dateFin));

                                contentStream.moveTextPositionByAmount(0, -40);
                                contentStream.setFont(PDType1Font.HELVETICA, 15); // Revenir à une police normale pour le reste du texte
                                // Définir la couleur de non-contour
                                contentStream.setNonStrokingColor(color);
                                contentStream.showText("Notre site web: www.TourneyPulse.com");

                                contentStream.endText();
                                contentStream.close();

                                // Enregistrement du document
                                String filePath = "C:\\Users\\msi\\OneDrive\\Bureau\\tournois.pdf";
                                document.save(filePath);
                                document.close();

                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Gestion Des Tournois");

                                alert.setHeaderText("Gestion Des Tournois");
                                alert.setContentText("Fichier PDF créé avec succès : " + filePath);
                                alert.showAndWait();
                        } catch (IOException e) {
                                Alert alert = new Alert(Alert.AlertType.ERROR);
                                alert.setTitle("Erreur");
                                alert.setContentText("Erreur lors de la création du fichier PDF : " + e.getMessage());
                                alert.showAndWait();
                        }
                        TFnomt.clear();
                        TFstade.clear();
                        TFnbrmatch.clear();
                        DPdated.setValue(null);
                        DPdatef.setValue(null);
                        TFnomt.requestFocus();
                }

        }

        @FXML
        void refrechTournoisAction(ActionEvent event) {
                loadData();
                TFrecherchet.clear();

        }

        private void advancedSearchTournaments() {
                List<Tournois> tournoisList = serviceTournois.getAll();
                System.out.println("Tournois List: " + tournoisList);
                String searchText = TFrecherchet.getText().trim().toLowerCase(); // Récupérez le texte de recherche
                Vbox.getChildren().clear(); // Effacez les résultats précédents

                // Parcourez la liste des tournois et recherchez ceux correspondant au critère de recherche
                for (Tournois tournois : tournoisList) {
                        boolean tournoisSearch = false;
                        // Vérifiez si le nom du tournoi ou le nom du stade correspond au critère de recherche
                        if (tournois.getNom_tournois().toLowerCase().contains(searchText) ||
                                tournois.getStade().toLowerCase().contains(searchText)) {
                                tournoisSearch = true;
                        }

                        if (tournoisSearch) {
                                // Affichez les informations sur le tournoi
                                Label nomtLabel = new Label("Nom: " + tournois.getNom_tournois());
                                Label stadeLabel = new Label("Nom Stade: " + tournois.getStade());
                                Label nombremLabel = new Label("Nombre Match: " + tournois.getNombre_match());
                                Label datedLabel = new Label("Date Debut: " + tournois.getDate_debut());
                                Label datefLabel = new Label("Date Fin: " + tournois.getDate_fin());

                                GridPane gridPane = new GridPane();
                                gridPane.add(nomtLabel, 0, 1);
                                gridPane.add(stadeLabel, 0, 2);
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
                        // Create layout for each tournois
                        Label nomtLabel = new Label("Nom: " + tournois.getNom_tournois());
                        Label stadeLabel = new Label("Nom Stade: " + tournois.getStade());
                        Label nombremLabel = new Label("Nombre Match: " + tournois.getNombre_match());
                        Label datedLabel = new Label("Date Debut: " + tournois.getDate_debut());
                        Label datefLabel = new Label("Date Fin: " + tournois.getDate_fin());

                        GridPane gridPane = new GridPane();
                        gridPane.add(nomtLabel, 0, 1);
                        gridPane.add(stadeLabel, 0, 2);
                        gridPane.add(nombremLabel, 0, 3);
                        gridPane.add(datedLabel, 0, 4);
                        gridPane.add(datefLabel, 0, 5);

                        TitledPane titledPane = new TitledPane("Tournois " + tournois.getId_tournois(), gridPane);


                        titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                        selectedIdt = "" + tournois.getId_tournois();
                                        selectedNomt = tournois.getNom_tournois();
                                        selectedStade = tournois.getStade();
                                        selectedNombrem = String.valueOf(tournois.getNombre_match());
                                        selectedDated = String.valueOf(tournois.getDate_debut());
                                        selectedDatef = String.valueOf(tournois.getDate_fin());

                                        // Perform any action with the selected values
                                        System.out.println("Selected ID: " + selectedIdt);
                                        System.out.println("Selected Nom: " + selectedNomt);
                                        System.out.println("Selected Stade: " + selectedStade);
                                        System.out.println("Selected Nombre Matchs: " + selectedNombrem);
                                        System.out.println("Selected Date Debut: " + selectedDated);
                                        System.out.println("Selected Date Fin: " + selectedDatef);

                                        TFnomt.setText(selectedNomt);
                                        TFstade.setText(selectedStade);
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
