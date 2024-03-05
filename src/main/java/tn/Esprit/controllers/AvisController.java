package tn.Esprit.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import tn.Esprit.models.Avis;
import tn.Esprit.services.ServiceAvis;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class AvisController {
    @FXML
    private TableView<Avis> avisTableView;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Button updateButton;

    @FXML
    private TableColumn<Avis, Integer> idColumn;

    @FXML
    private TableColumn<Avis, Integer> marketPlaceIdColumn;

    @FXML
    private TableColumn<Avis, Date> dateColumn;

    @FXML
    private TableColumn<Avis, Integer> noteColumn;

    @FXML
    private TextField marketPlaceIdField;

    @FXML
    private TextField contentField;

    private int selectedAvisId = 0;

    private ServiceAvis serviceAvis = new ServiceAvis();
    @FXML


    // Method to set the marketPlace ID
    public void setMarketPlaceId(int marketPlaceId) {
        marketPlaceIdField.setText(String.valueOf(marketPlaceId));
    }

    @FXML
    public void initialize() {
        showAvis();
    }

    public void showAvis() {
        ArrayList<Avis> avi = serviceAvis.getAll();
        ObservableList<Avis> avisObservableList = FXCollections.observableList(avi);
        avisTableView.setItems(avisObservableList);

       // idColumn.setCellValueFactory(new PropertyValueFactory<>("idAvis"));
        marketPlaceIdColumn.setCellValueFactory(new PropertyValueFactory<>("idProd"));
        dateColumn.setCellValueFactory(cellData -> {
            Avis avis = cellData.getValue();
            Timestamp timestamp = avis.getdateAvis();
            Date date = new Date(timestamp.getTime());
            return new SimpleObjectProperty<>(date);
        });
        noteColumn.setCellValueFactory(new PropertyValueFactory<>("note"));
    }

    @FXML
    void addAvis(ActionEvent event) {
        String marketPlaceIdText = marketPlaceIdField.getText();
        String noteText = contentField.getText();

        // Check if the fields are empty
        if (marketPlaceIdText.isEmpty() || noteText.isEmpty()) {
            showAlert("MarketPlace ID and note cannot be empty.");
            return;
        }

        // Parse the values
        int marketPlaceId;
        int note;
        try {
            marketPlaceId = Integer.parseInt(marketPlaceIdText);
            note = Integer.parseInt(noteText);
        } catch (NumberFormatException e) {
            showAlert("Please enter valid numeric values for MarketPlace ID and note.");
            return;
        }

        // Check if note is within the range of 0 to 5
        if (note < 0 || note > 5) {
            showAlert("Note must be between 0 and 5.");
            return;
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);

        Avis newAvis = new Avis(0, marketPlaceId, currentTimestamp, note);
        serviceAvis.add(newAvis);
        showAvis();
    }


    @FXML
    void deleteAvis(ActionEvent event) {
        Avis selectedAvis = avisTableView.getSelectionModel().getSelectedItem();
        if (selectedAvis != null) {
            serviceAvis.delete(selectedAvis);
            showAvis();
        } else {
            showAlert("Please select a Avis to delete.");
        }
    }

    @FXML
    void updateAvis(ActionEvent event) {
        int note = Integer.parseInt(contentField.getText());
        Avis avis = avisTableView.getSelectionModel().getSelectedItem();
        if (avis != null) {
            avis.setNote(note);
            serviceAvis.update(avis);
            showAvis();
        } else {
            showAlert("Please select a avis to update.");
        }
    }

    @FXML
    void getAvisData(javafx.scene.input.MouseEvent event) {
        Avis avis = avisTableView.getSelectionModel().getSelectedItem();
        if (avis != null) {
            selectedAvisId = avis.getIdAvis();
            marketPlaceIdField.setText(String.valueOf(avis.getIdProd()));
            contentField.setText(String.valueOf(avis.getNote()));
        } else {
            selectedAvisId = 0;
            marketPlaceIdField.clear();
            contentField.clear();
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public void backMenu(ActionEvent actionEvent) {
    }

    public void toReservation(ActionEvent actionEvent) {
    }
}
