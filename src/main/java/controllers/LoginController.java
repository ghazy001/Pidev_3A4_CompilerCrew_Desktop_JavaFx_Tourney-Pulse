package controllers;


import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import services.LoginService;

import java.io.IOException;


public class LoginController {

    @FXML
    private Button google;

    @FXML
    private TextField name;

    @FXML
    private TextField password;

    @FXML
    private Button loginbtn;

    @FXML
    private Button signinbtn;
    @FXML
    private Button forgetpass;

    private final LoginService loginService = new LoginService();
    @FXML
    void handleLogin(ActionEvent event) {

        String userName = name.getText();
        String pass = password.getText();

        User user = new LoginService().getUserByUsername(userName);
        if (user != null && user.getPassword().equals(pass)) {
            switchScene("/Uiadmin.fxml", event);
            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + user.getUsername() + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid Username or password!");
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
    @FXML
    void handleSignIn(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RegisterUser.fxml"));
            Parent root = loader.load();
            RegisterUserController controller = loader.getController();
            Scene scene = new Scene(root);
            Stage stage = (Stage) signinbtn.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void forgetpsswd(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Forgetpass.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



