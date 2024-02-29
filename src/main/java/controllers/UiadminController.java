package controllers;

import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import services.UserService;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UiadminController implements Initializable {

    @FXML
    private ListView<User> listView;
    @FXML
    private Button dashboardButton;
    @FXML
    private Button usersButton;

    private ObservableList<User> users;
    private UserService userService = new UserService();
    private List<User> userList;

    @FXML
    private VBox updateForm;

    // Inject the text fields and combo box
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField passwordField;
    @FXML
    private ComboBox<String> roleComboBox;
    @FXML
    private Button savebtn;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize user list
        userList = userService.getAllData();
        users = FXCollections.observableArrayList(userList);

        listView.setItems(users);

        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                return new ListCell<>() {
                    @Override
                    protected void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            String passwordHash = String.valueOf(item.getPassword().hashCode());
                            VBox vbox = new VBox();

                            Button deleteButton = new Button("Delete");
                            deleteButton.setOnAction(event -> {
                                userService.deleteEntity(item);
                                users.remove(item);
                            });
                            // Set background color and text color for Delete button
                            deleteButton.setStyle("-fx-background-color: #1f3d1c; -fx-text-fill: #c3cfb6;");

                            Button updateButton = new Button("Update");
                            updateButton.setOnAction(event -> {
                                // Populate text fields and combo box with user information
                                updateForm.setVisible(true);
                                firstNameField.setText(item.getFirstname());
                                lastNameField.setText(item.getLastname());
                                usernameField.setText(item.getUsername());
                                emailField.setText(item.getEmail());
                                numberField.setText(item.getNumber());

                                // Set the role in the combo box
                                String role = item.getRole();
                                if (role != null) {
                                    roleComboBox.setValue(role);
                                } else {
                                    roleComboBox.setValue("User"); // Default value
                                }
                            });
                            // Set background color and text color for Update button
                            updateButton.setStyle("-fx-background-color: #1f3d1c; -fx-text-fill: #c3cfb6;");

                            vbox.getChildren().addAll(deleteButton, updateButton);
                            setGraphic(vbox);

                            setText("First Name: " + item.getFirstname() +
                                    "\nLast Name: " + item.getLastname() +
                                    "\nUsername: " + item.getUsername() +
                                    "\nEmail: " + item.getEmail() +
                                    "\nNumber: " + item.getNumber() +
                                    "\nPassword: " + passwordHash +
                                    "\nRole: " + item.getRole());
                        }
                    }
                };
            }
        });


        // Initialize combo box with values "admin" and "user"
        ObservableList<String> roles = FXCollections.observableArrayList("Admin", "User");
        roleComboBox.setItems(roles);
    }
    @FXML
    private void handleSave(ActionEvent event) {
        // Get the selected user from the ListView
        User selectedUser = listView.getSelectionModel().getSelectedItem();
        if (selectedUser != null) {
            // Update the user object with the new values
            selectedUser.setFirstname(firstNameField.getText());
            selectedUser.setLastname(lastNameField.getText());
            selectedUser.setUsername(usernameField.getText()); // Update the username
            selectedUser.setEmail(emailField.getText());
            selectedUser.setNumber(numberField.getText());
            // In a real application, you might want to handle password updates differently
            //selectedUser.setPassword(passwordField.getText());
            selectedUser.setRole(roleComboBox.getValue());

            // Update the user in the database
            userService.updateEntity(selectedUser);

            // Refresh the ListView
            listView.refresh();

            // Show an alert confirming that the user has been updated
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("User Updated");
            alert.setHeaderText(null);
            alert.setContentText("User information has been updated.");
            alert.showAndWait();
        }
    }

}