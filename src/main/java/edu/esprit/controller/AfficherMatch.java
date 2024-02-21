package edu.esprit.controller;

import edu.esprit.entities.Matchs;
import edu.esprit.services.ServiceMatch;
import edu.esprit.entities.Tournois;
import edu.esprit.utils.DataSource;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.fxml.Initializable;

import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class AfficherMatch implements Initializable{

    @FXML
    private TableColumn<Matchs, Date> CDatem;

    @FXML
    private TableColumn<Matchs, String> CDureem;

    @FXML
    private TableColumn<Matchs, Integer> CIDm;

    @FXML
    private TableColumn<Matchs, String> CNomm;

    @FXML
    private TableColumn<Matchs, Integer> CNomt;

    @FXML
    private DatePicker TFdatem;

    @FXML
    private TextField TFdureem;

    @FXML
    private TextField TFnomm;

    @FXML
    private TextField TFnomt;

    @FXML
    private TableView<Matchs> tablem;

    PreparedStatement prepare;
    Connection connection;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        tablee();
    }

    public void tablee() {
        ObservableList<Matchs> match = FXCollections.observableArrayList();
        connection = DataSource.getInsatnce().getConnection();

        String sql = "SELECT * FROM `match`";

        try {
            prepare = connection.prepareStatement(sql);
            ResultSet resultSet = prepare.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id_match");
                String nomMatchs = resultSet.getString("nom_match");
                java.sql.Date dateMatchs = resultSet.getDate("date_debut");
                String dureeMatchs = resultSet.getString("duree_match");
                int idTournois = resultSet.getInt("id_tournois");
                Matchs m = new Matchs();
                m.setId_match(id);
                m.setNom_match(nomMatchs);
                m.setDate_match(dateMatchs);
                m.setDuree_match(dureeMatchs);
                Tournois tour = new Tournois();
                tour.setId_tournois(idTournois);
                m.setTournois(tour);
                match.add(m);
            }

            CIDm.setCellValueFactory(f -> new SimpleIntegerProperty(f.getValue().getId_match()).asObject());
            CNomm.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getNom_match()));
            CDatem.setCellValueFactory(f -> new SimpleObjectProperty<>((Date) f.getValue().getDate_match()));
            CDureem.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getDuree_match()));
            CNomt.setCellValueFactory(f -> new SimpleIntegerProperty(f.getValue().getTournois().getId_tournois()).asObject());

            tablem.setItems(match);

        } catch (SQLException ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Erreur lors du chargement de la table des matchs !");
            alert.show();
        }
        tablem.setRowFactory( tv -> {
            TableRow<Matchs> myRow = new TableRow<>();
            myRow.setOnMouseClicked (event ->
            {
                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                {
                    int myIndex = tablem.getSelectionModel().getSelectedIndex();
                    int id = Integer.parseInt(String.valueOf(tablem.getItems().get(myIndex).getId_match()));
                    TFnomm.setText(tablem.getItems().get(myIndex).getNom_match());
                    java.sql.Date dateDebutSQL = (java.sql.Date) tablem.getItems().get(myIndex).getDate_match();
                    Instant instant = dateDebutSQL.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                    LocalDate dateDebut = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                    TFdatem.setValue(dateDebut);
                    TFdureem.setText(tablem.getItems().get(myIndex).getDuree_match());
                    TFnomt.setText(String.valueOf(tablem.getItems().get(myIndex).getTournois()));



                }
            });
            return myRow;
        });
    }

    @FXML
    void ajouterMatchsAction(ActionEvent event) {
        String nomTourText = TFnomt.getText();
        int idTour;
        String dateMText = String.valueOf(TFdatem.getValue());
        java.util.Date dateM;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateM = sdf.parse(dateMText);
            Date sqlDateM = new Date(dateM.getTime());

            ServiceMatch sm = new ServiceMatch();


            if (!TFnomm.getText().isEmpty() || !TFdureem.getText().isEmpty() || !TFnomt.getText().isEmpty()) {
                idTour = Integer.parseInt(nomTourText);
                Tournois tournois = new Tournois();
                tournois.setId_tournois(idTour);
                sm.ajouter(new Matchs(TFnomm.getText(), sqlDateM, TFdureem.getText(), tournois));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Match ajouter!");
                alert.show();
                tablee();

                LocalDate t = null;
                TFnomm.setText("");
                TFdatem.setValue(t);
                TFdureem.setText("");
                TFnomt.setText("");
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

    @FXML
    void modifierMatchsAction(ActionEvent event) {
        String nomTourText = TFnomt.getText();
        int idTour;
        String dateMText = String.valueOf(TFdatem.getValue());
        java.util.Date dateM;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            dateM = sdf.parse(dateMText);
            Date sqlDateM = new Date(dateM.getTime());

            ServiceMatch sm = new ServiceMatch();
            int myIndex = tablem.getSelectionModel().getSelectedIndex();

            int id = Integer.parseInt(String.valueOf(tablem.getItems().get(myIndex).getId_match()));


            if (!TFnomm.getText().isEmpty() || !TFdureem.getText().isEmpty() || !TFnomt.getText().isEmpty()) {
                idTour = Integer.parseInt(nomTourText);
                Tournois tournois = new Tournois();
                tournois.setId_tournois(idTour);
                sm.modifier(id, new Matchs(TFnomm.getText(), sqlDateM, TFdureem.getText(), tournois));
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setContentText("Match ajouter!");
                alert.show();
                tablee();

                LocalDate t = null;
                TFnomm.setText("");
                TFdatem.setValue(t);
                TFdureem.setText("");
                TFnomt.setText("");
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

    @FXML
    void supprimerMatchsAction(ActionEvent event) {
        int myIndex = tablem.getSelectionModel().getSelectedIndex();

        int id = Integer.parseInt(String.valueOf(tablem.getItems().get(myIndex).getId_match()));
        ServiceMatch sm = new ServiceMatch();
        sm.supprimer(id);
        tablee();
        LocalDate t = null;
        TFnomm.setText("");
        TFdatem.setValue(t);
        TFdureem.setText("");
        TFnomt.setText("");
        TFnomm.requestFocus();

    }

}
