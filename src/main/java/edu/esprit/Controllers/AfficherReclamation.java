package edu.esprit.Controllers;
import edu.esprit.Services.ServiceReclamation;
import edu.esprit.entities.Reclamation;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class AfficherReclamation {
    @FXML
    GridPane ihebGrid;
    ServiceReclamation serviceReclamation = new ServiceReclamation();


    public void initialize(URL url, ResourceBundle resourceBundle) {

        List<Reclamation> reclamationList = serviceReclamation.getAll();
        System.out.println("Reclamation List: " + reclamationList); // Print the list

        // Loop through the list and create layout for each reclamation
        int rowIndex = 0;
        int colIndex = 0;
        for (Reclamation reclamation : reclamationList) {
            System.out.println("Adding reclamation to grid: " + reclamation);
            // Create layout for each reclamation
            Label idLabel = new Label("ID: " + reclamation.getId_rec());
            Label userIdLabel = new Label("User ID: " + reclamation.getId());
            Label emailLabel = new Label("Email: " + reclamation.getEmail());
            Label objectLabel = new Label("Object: " + reclamation.getObject());
            Label recLabel = new Label("Reclamation: " + reclamation.getRec());
            Label dateLabel = new Label("Date: " + reclamation.getDate_rec());

            // Add layout to gridPane
            ihebGrid.add(idLabel, colIndex, rowIndex);
            ihebGrid.add(userIdLabel, colIndex + 1, rowIndex);
            ihebGrid.add(emailLabel, colIndex + 2, rowIndex);
            ihebGrid.add(objectLabel, colIndex + 3, rowIndex);
            ihebGrid.add(recLabel, colIndex + 4, rowIndex);
            ihebGrid.add(dateLabel, colIndex + 5, rowIndex);

            // Increment row index
            rowIndex++;
    }


}}
