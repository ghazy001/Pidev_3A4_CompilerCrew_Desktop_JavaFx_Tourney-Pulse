package controllers;

import javax.mail.MessagingException;
import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
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
import services.UserService;
import utiles.MyConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Random;
import java.util.regex.Pattern;

public class ForgetpassController {

    @FXML
    private TextField TFemail;

    @FXML
    private Button resetpass;

    @FXML
    private Button Createbtn;

    @FXML
    private Button returnlogin;

    @FXML
    private TextField CodeField;
    static String adremail;
    private final UserService userService = new UserService();

    private static final String OTP_CHARS = "0123456789";
    private static final int OTP_LENGTH = 6;

    private static final String SENDGRID_API_KEY = "";


    @FXML
    void resetPassword(ActionEvent event) {
        String email = TFemail.getText();
        if (!isValidEmail(email)) {
            showAlert(Alert.AlertType.ERROR, "Invalid Email", "Please enter a valid email address.");
            return;
        }

        if (!userService.doesEmailExist(email)) {
            showAlert(Alert.AlertType.ERROR, "Email Not Found", "The provided email does not exist in our database.");
            return;
        }

        String otp = generateOTP();

        try {
            sendOTPByEmail(email, otp);
        } catch (IOException | MessagingException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to send OTP via email.");
        }
        adremail=TFemail.getText();
        userService.updateOTP(email, otp);
    }


    @FXML
    void verifcode(ActionEvent event) {
        String email = TFemail.getText();
        String enteredOTP = CodeField.getText();

        String storedOTP = userService.getOTP(email);

        if (enteredOTP.equals(storedOTP)) {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/resetpass.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);

                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            showAlert(Alert.AlertType.ERROR, "Error", "Entered OTP does not match the one sent to your email. Please try again.");
        }
    }

    private void sendOTPByEmail(String email, String otp) throws IOException, MessagingException {
        Email from = new Email("Youssefharrabi99@gmail.com");
        String subject = "Your OTP";
        Email to = new Email(email);
        String bodyContent = "Your verification code is: " + otp; // Use the provided OTP
        Content content = new Content("text/plain", bodyContent);
        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(SENDGRID_API_KEY); // Use the constant for API key
        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                showAlert(Alert.AlertType.INFORMATION, "Success", "Verification code sent to your email.");
            } else {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to send verification code. Please try again later.");
            }
        } catch (IOException ex) {
            ex.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "Failed to send verification code. Please try again later.");
        }
    }


    private String generateOTP() {
        Random random = new Random();
        StringBuilder otp = new StringBuilder();
        for (int i = 0; i < OTP_LENGTH; i++) {
            otp.append(OTP_CHARS.charAt(random.nextInt(OTP_CHARS.length())));
        }
        return otp.toString();
    }

    private boolean isValidEmail(String email) {
        // Regular expression pattern for basic email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void Backtoregister(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/RegisterUser.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void loginnavigate(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) returnlogin.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
