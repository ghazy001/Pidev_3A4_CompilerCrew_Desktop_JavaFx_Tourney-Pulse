package controllers;


import entities.User;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import services.LoginService;
import java.io.IOException;
import org.mindrot.jbcrypt.BCrypt;
import java.sql.SQLException;


public class LoginController {

    @FXML
    private TextField name;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginbtn;

    @FXML
    private Button signinbtn;

    @FXML
    private Button forgetpass;
    @FXML
    private WebView captchaWebView;
    static String username;


    private final LoginService loginService = new LoginService();

    public void initialize() throws SQLException {
        WebEngine engine = captchaWebView.getEngine();
        engine.load("http://localhost/captcha.html");
    }

    @FXML
    void handleLogin(ActionEvent event) {
        String userName = name.getText();
        String pass = password.getText();

        try{
            WebEngine engine = captchaWebView.getEngine();
            String result = (String) engine.executeScript(
                    "function isRecaptchaVerified() {" +
                            " var isVerified = grecaptcha.getResponse().length > 0;" +
                            " return String(isVerified);" +
                            "} " +
                            "isRecaptchaVerified();"
            );
            if(result.equals("false")){
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("captcha");
                alert.setContentText("Please check the captcha.");
                alert.showAndWait();
                System.out.println("erreur");
                return;

            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        User user = loginService.getUserByUsername(userName);
        if (user != null && BCrypt.checkpw(pass, user.getPassword())) {
            // Store the username
            username = userName;

            String fxmlFile;
            String role = user.getRole();
            if (role.equals("Admin")) {
                switchScene("/Uiadmin.fxml", event);
            } else if (role.equals("User")) {
                switchScene("/Uiuser.fxml", event);
            } else {
                showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid role for the user!");
            }

            showAlert(Alert.AlertType.INFORMATION, "Login Successful", "Welcome " + user.getUsername() + "!");
        } else {
            showAlert(Alert.AlertType.ERROR, "Login Failed", "Invalid Username or password!");
        }
    }

    @FXML
    void handleSignIn(ActionEvent event) {
        switchScene("/RegisterUser.fxml", event);
    }

    @FXML
    void forgetpsswd(ActionEvent event) {
        switchScene("/Forgetpass.fxml", event);
    }

    private void switchScene(String fxmlFile, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
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
