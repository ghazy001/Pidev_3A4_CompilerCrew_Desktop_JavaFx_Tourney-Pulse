package controllers;

import entities.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import services.UserService;
import utiles.MyConnection;

import java.io.File;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class RegisterUserController {

    @FXML
    private TextField TFfirstname;

    @FXML
    private TextField TFlastname;

    @FXML
    private TextField TFemail;

    @FXML
    private TextField TFusername;

    @FXML
    private TextField Tfnumber;

    @FXML
    private PasswordField TFpassword;

    @FXML
    private PasswordField TFconfirmpass;

    @FXML
    private CheckBox checkbx;

    @FXML
    private Button loginbtn;

    @FXML
    private ImageView imgviewimport;

    private File selectedImageFile;
    private final UserService userService = new UserService();

    @FXML
    void registerUserToDatabase(ActionEvent event) {
        try {
            // Check if any field is empty
            if (TFfirstname.getText().isEmpty() || TFlastname.getText().isEmpty() || TFemail.getText().isEmpty() ||
                    TFusername.getText().isEmpty() || Tfnumber.getText().isEmpty() || TFpassword.getText().isEmpty() || TFconfirmpass.getText().isEmpty()) {
                throw new Exception("Please fill in all fields");
            }

            // Check if passwords match
            if (!TFpassword.getText().equals(TFconfirmpass.getText())) {
                throw new Exception("Passwords do not match");
            }

            // Validate email format
            String email = TFemail.getText();
            if (!isValidEmail(email)) {
                throw new Exception("Invalid email format");
            }

            // Validate number format
            String number = Tfnumber.getText();
            if (!isValidNumber(number)) {
                throw new Exception("Number should contain only numbers");
            }

            // Validate password format
            String password = TFpassword.getText();
            if (!isValidPassword(password)) {
                throw new Exception("Password must be at least 8 characters long and contain at least one digit and one capital letter");
            }

            // Validate first name format
            String firstName = TFfirstname.getText();
            if (!isValidName(firstName)) {
                throw new Exception("First name should contain only alphabetic characters");
            }

            // Validate last name format
            String lastName = TFlastname.getText();
            if (!isValidName(lastName)) {
                throw new Exception("Last name should contain only alphabetic characters");
            }

            if (!checkbx.isSelected()) {
                throw new Exception("Please agree to the Terms of Service and Privacy Policy");
            }

            // Check if email is already taken
            if (isEmailTaken(email)) {
                throw new Exception("Email is already taken");
            }

            // Check if username is already taken
            String username = TFusername.getText();
            if (isUsernameTaken(username)) {
                throw new Exception("Username is already taken");
            }

            // Check if phone number is already taken
            if (isPhoneNumberTaken(number)) {
                throw new Exception("Phone number is already taken");
            }

            // Check if image is selected
            if (selectedImageFile == null) {
                throw new Exception("Please upload an image");
            }

            // Assuming you have a User class and set methods for first name, last name, etc.
            User user = new User();
            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setEmail(email);
            user.setUsername(username);
            user.setNumber(number);
            user.setPassword(password);

            // Set the image file path
            user.setImageFile(selectedImageFile);

            userService.addEntity(user);

            // Clear input fields
            TFfirstname.clear();
            TFlastname.clear();
            TFemail.clear();
            TFusername.clear();
            Tfnumber.clear();
            TFpassword.clear();
            TFconfirmpass.clear();
            checkbx.setSelected(false);
            imgviewimport.setImage(null); // Clear the image view

            // Show success message
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setContentText("User added successfully!");
            alert.show();
        } catch (Exception e) {
            // Show error message
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }

    // Method to validate email format
    private boolean isValidEmail(String email) {
        // Regular expression pattern for basic email validation
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

        // Compile the pattern into a regex pattern object
        Pattern pattern = Pattern.compile(emailRegex);

        // Check if the email matches the pattern
        return pattern.matcher(email).matches();
    }

    private boolean isValidNumber(String number) {
        // Regular expression pattern for digits only
        String numberRegex = "\\d+";

        // Compile the pattern into a regex pattern object
        Pattern pattern = Pattern.compile(numberRegex);

        // Check if the number matches the pattern
        return pattern.matcher(number).matches();
    }

    private boolean isValidPassword(String password) {
        // Password must be at least 8 characters long and contain at least one digit and one capital letter
        String passwordRegex = "^(?=.*[0-9])(?=.*[A-Z]).{8,}$";

        // Compile the pattern into a regex pattern object
        Pattern pattern = Pattern.compile(passwordRegex);

        // Check if the password matches the pattern
        return pattern.matcher(password).matches();
    }

    // Method to validate name format
    private boolean isValidName(String name) {
        // Regular expression pattern for alphabetic characters only
        String nameRegex = "^[a-zA-Z]+$";

        // Compile the pattern into a regex pattern object
        Pattern pattern = Pattern.compile(nameRegex);

        // Check if the name matches the pattern
        return pattern.matcher(name).matches();
    }

    @FXML
    void closeWindow(ActionEvent event) {
        Stage stage = (Stage) loginbtn.getScene().getWindow();
        stage.close();
    }

    @FXML
    void goToLogin(ActionEvent event) {
        try {
            // Load the FXML file
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
            Parent root = loader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            closeWindow(event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Method to check if the email is already taken
    private boolean isEmailTaken(String email) {
        String query = "SELECT COUNT(*) FROM user WHERE email = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setString(1, email);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // If count > 0, email is already taken
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to check if the username is already taken
    private boolean isUsernameTaken(String username) {
        String query = "SELECT COUNT(*) FROM user WHERE username = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // If count > 0, username is already taken
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to check if the phone number is already taken
    private boolean isPhoneNumberTaken(String phoneNumber) {
        String query = "SELECT COUNT(*) FROM user WHERE number = ?";
        try {
            PreparedStatement pst = MyConnection.getInstance().getCnx().prepareStatement(query);
            pst.setString(1, phoneNumber);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                int count = rs.getInt(1);
                return count > 0; // If count > 0, phone number is already taken
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @FXML
    void handleUploadImage(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif"));

        selectedImageFile = fileChooser.showOpenDialog(null);

        if (selectedImageFile != null) {
            Image image = new Image(selectedImageFile.toURI().toString());
            imgviewimport.setImage(image);
        }
    }
}
