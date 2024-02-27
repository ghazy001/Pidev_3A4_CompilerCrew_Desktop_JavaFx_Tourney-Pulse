package controllers;


import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.LoginService;


public class Login {

    @FXML
    private Button google;

    @FXML
    private Button facebook;

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private Button loginbtn;

    @FXML
    private Button signinbtn;

    private final LoginService loginService = new LoginService();
    @FXML
    void handleLogin(ActionEvent event) {

        String userName = name.getText();
        String pass = password.getText();

        // Fetch user data from the database
        User user = new LoginService().getUserByUsername(userName);

        // Check if the user exists and the password matches
        if (user != null && user.getPassword().equals(pass)) {
            // Navigate to the next screen or perform any other action
            // For example:
            // switchScene();
            // Or show a success message
            switchScene("/Display.fxml", event);
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + user.getName() + "!");
        } else {
            // Show error message
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid email or password!");
        }
    }
    private void switchScene(String fxmlFile, ActionEvent event) {
        try {
            System.out.println("fxml:"+ fxmlFile);

            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}



