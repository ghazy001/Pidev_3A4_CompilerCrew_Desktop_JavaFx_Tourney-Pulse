package controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UiuserController implements Initializable {


    @FXML
    private ImageView logout;

    @FXML
    private Button manageteams;

    @FXML
    private Button mngtourn;

    @FXML
    private Button playerrat;

    @FXML
    private Button feedback;


    @FXML
    private TextField TFusername;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // You can add initialization code here
        // For example, you can add event handlers for your buttons
        manageteams.setOnAction(event -> {
            handleManageTeamsButtonAction();
        });

        mngtourn.setOnAction(event -> {
            handleManageTournamentButtonAction();
        });

        playerrat.setOnAction(event -> {
            handlePlayerRatingButtonAction();
        });

        feedback.setOnAction(event -> {
            handleFeedbackButtonAction();
        });

        String username = LoginController.username;
        TFusername.setText(username);
    }


    private void handleManageTeamsButtonAction() {
        showAlert("Manage Teams Button Clicked", "Manage Teams button was clicked.");
    }

    private void handleManageTournamentButtonAction() {
        showAlert("Manage Tournament Button Clicked", "Manage Tournament button was clicked.");
    }

    private void handlePlayerRatingButtonAction() {
        showAlert("Player Rating Button Clicked", "Player Rating button was clicked.");
    }

    private void handleFeedbackButtonAction() {
        showAlert("Feedback Button Clicked", "Feedback button was clicked.");
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    @FXML
    private void logout(ActionEvent event) {
        // Load the login.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = (Stage) logout.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
