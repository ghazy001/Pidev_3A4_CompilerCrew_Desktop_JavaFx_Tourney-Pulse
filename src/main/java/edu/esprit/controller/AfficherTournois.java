package edu.esprit.controller;

import edu.esprit.entities.Tournois;
import edu.esprit.services.ServiceTournois;
import edu.esprit.utils.DataSource;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class AfficherTournois implements Initializable {

        @FXML
        private TableColumn<Tournois, String> CAddresst;

        @FXML
        private TableColumn<Tournois, Date> CDatedt;

        @FXML
        private TableColumn<Tournois, Date> CDateft;

        @FXML
        private TableColumn<Tournois, Integer> CIDt;

        @FXML
        private TableColumn<Tournois, Integer> CNombret;

        @FXML
        private TableColumn<Tournois, String> CNomt;

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
        private TableView<Tournois> tablet;

        PreparedStatement prepare;
        Connection connection;

        public void initialize(URL url, ResourceBundle resourceBundle) {
                table();
        }

        public void table() {
                ObservableList<Tournois> tournois = FXCollections.observableArrayList();
                connection = DataSource.getInsatnce().getConnection();

                String sql = "SELECT * FROM tournois";

                try {
                        prepare = connection.prepareStatement(sql);
                        ResultSet resultSet = prepare.executeQuery();

                        while (resultSet.next()) {
                                int id = resultSet.getInt("id_tournois");
                                String nomTournois = resultSet.getString("nom_tournois");
                                String addressTournois = resultSet.getString("address_tournois");
                                int nombreMatch = resultSet.getInt("nombre_match");
                                Date dateDebut = resultSet.getDate("date_debut");
                                Date dateFin = resultSet.getDate("date_fin");
                                Tournois t = new Tournois();
                                t.setId_tournois(id);
                                t.setNom_tournoi(nomTournois);
                                t.setAddress_tournois(addressTournois);
                                t.setNombre_match(nombreMatch);
                                t.setDate_debut(dateDebut);
                                t.setDate_fin(dateFin);
                                tournois.add(t);
                        }

                        CIDt.setCellValueFactory(f -> new SimpleIntegerProperty(f.getValue().getId_tournois()).asObject());
                        CNomt.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getNom_tournois()));
                        CAddresst.setCellValueFactory(f -> new SimpleStringProperty(f.getValue().getAddress_tournois()));
                        CNombret.setCellValueFactory(f -> new SimpleIntegerProperty(f.getValue().getNombre_match()).asObject());
                        CDatedt.setCellValueFactory(f -> new SimpleObjectProperty<>((Date) f.getValue().getDate_debut()));
                        CDateft.setCellValueFactory(f -> new SimpleObjectProperty<>((Date) f.getValue().getDate_fin()));

                        tablet.setItems(tournois);

                } catch (SQLException ex) {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("Erreur lors du chargement de la table des tournois !");
                        alert.show();
                }
                tablet.setRowFactory( tv -> {
                        TableRow<Tournois> myRow = new TableRow<>();
                        myRow.setOnMouseClicked (event ->
                        {
                                if (event.getClickCount() == 1 && (!myRow.isEmpty()))
                                {
                                        int myIndex = tablet.getSelectionModel().getSelectedIndex();
                                        int id = Integer.parseInt(String.valueOf(tablet.getItems().get(myIndex).getId_tournois()));
                                        TFnom.setText(tablet.getItems().get(myIndex).getNom_tournois());
                                        TFaddress.setText(tablet.getItems().get(myIndex).getAddress_tournois());
                                        TFnbrmatch.setText(String.valueOf(tablet.getItems().get(myIndex).getNombre_match()));
                                        java.sql.Date dateDebutSQL = (Date) tablet.getItems().get(myIndex).getDate_debut();
                                        Instant instant = dateDebutSQL.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                                        LocalDate dateDebut = instant.atZone(ZoneId.systemDefault()).toLocalDate();
                                        TFdated.setValue(dateDebut);
                                        java.sql.Date dateFinSQL = (Date) tablet.getItems().get(myIndex).getDate_debut();
                                        Instant ins = dateFinSQL.toLocalDate().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
                                        LocalDate dateFin = ins.atZone(ZoneId.systemDefault()).toLocalDate();
                                        TFdatef.setValue(dateFin);



                                }
                        });
                        return myRow;
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
                                table();

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

        @FXML
        void modifierTournoisAction(ActionEvent event) {
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
                        int myIndex = tablet.getSelectionModel().getSelectedIndex();

                        int id = Integer.parseInt(String.valueOf(tablet.getItems().get(myIndex).getId_tournois()));

                        if (!TFnom.getText().isEmpty() || !TFaddress.getText().isEmpty() || !TFnbrmatch.getText().isEmpty() || !TFdated.getValue().isEqual(TFdatef.getValue()) || !TFdatef.getValue().isEqual(TFdated.getValue())) {
                                st.modifier(id, new Tournois(TFnom.getText(), TFaddress.getText(), nbrMatch, sqlDateD, sqlDateF));
                                table();
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

        @FXML
        void navigatetoGmatchPersonneAction(ActionEvent event) {
                try {
                        Parent root = FXMLLoader.load(getClass().getResource("/AfficherMatch.fxml"));
                        TFnom.getScene().setRoot(root);
                } catch (IOException e) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setContentText("Sorry");
                        alert.setTitle("Error");
                        alert.show();
                }

        }

        @FXML
        void supprimerTournoisAction(ActionEvent event) {
                int myIndex = tablet.getSelectionModel().getSelectedIndex();

                int id = Integer.parseInt(String.valueOf(tablet.getItems().get(myIndex).getId_tournois()));
                ServiceTournois st = new ServiceTournois();
                st.supprimer(id);
                table();
                LocalDate t = null;
                TFnom.setText("");
                TFaddress.setText("");
                TFnbrmatch.setText("");
                TFdated.setValue(t);
                TFdatef.setValue(t);
                TFnom.requestFocus();

        }
}
