package controllers;

import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Callback;
import services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

public class UiadminController implements Initializable {

    @FXML
    private ListView<User> listView;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button usersButton;

    @FXML
    private Button logoutbtn;

    @FXML
    private TextField chercher;

    @FXML
    private TextField TFusername;

    private ObservableList<User> users;
    private UserService userService = new UserService();
    private List<User> userList;

    @FXML
    private VBox updateForm;

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

    @FXML
    private void handleDashboardButton(ActionEvent event) {
        displayCharts();
    }

    private void displayCharts() {
        // Get counts of each role from the database
        Map<String, Integer> roleCounts = userService.getRoleCounts();

        // Create data for the Pie Chart
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (Map.Entry<String, Integer> entry : roleCounts.entrySet()) {
            pieChartData.add(new PieChart.Data(entry.getKey(), entry.getValue()));
        }

        // Create and configure the Pie Chart
        PieChart pieChart = new PieChart(pieChartData);
        pieChart.setTitle("Role Distribution");

        // Create a new scene for the pie chart and display it
        VBox root = new VBox(pieChart);
        Scene scene = new Scene(root, 600, 400);
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        // Initialize user list
        userList = userService.getAllData();
        users = FXCollections.observableArrayList(userList);

        // Initially hide the updateForm and the listView
        updateForm.setVisible(false);
        listView.setVisible(false);

        listView.setItems(users);
        listView.refresh();

        listView.setCellFactory(new Callback<>() {
            @Override
            public ListCell<User> call(ListView<User> param) {
                return new ListCell<>() {
                    protected void updateItem(User item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            String passwordHash = item.getPassword();
                            VBox vbox = new VBox();

                            // Set spacing between the buttons
                            vbox.setStyle("-fx-spacing: 10;");

                            Button deleteButton = new Button("Delete");
                            deleteButton.setOnAction(event -> {
                                Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
                                confirmationAlert.setTitle("Confirmation");
                                confirmationAlert.setHeaderText("Delete User");
                                confirmationAlert.setContentText("Are you sure you want to delete this user?");

                                Optional<ButtonType> result = confirmationAlert.showAndWait();
                                if (result.isPresent() && result.get() == ButtonType.OK) {
                                    // User confirmed deletion, proceed with deletion
                                    userService.deleteEntity(item);
                                    users.remove(item);
                                }
                            });

                            // Set background color and text color for Delete button
                            deleteButton.setStyle("-fx-background-color: #1f3d1c; -fx-text-fill: #c3cfb6;");

                            Button updateButton = new Button("Update");
                            updateButton.setOnAction(event -> {
                                updateForm.setVisible(true);
                                firstNameField.setText(item.getFirstname());
                                lastNameField.setText(item.getLastname());
                                usernameField.setText(item.getUsername());
                                emailField.setText(item.getEmail());
                                numberField.setText(item.getNumber());

                                String role = item.getRole();
                                if (role != null) {
                                    roleComboBox.setValue(role);
                                } else {
                                    roleComboBox.setValue("User"); // Default value
                                }
                            });
                            updateButton.setStyle("-fx-background-color: #1f3d1c; -fx-text-fill: #c3cfb6;");

                            vbox.getChildren().addAll(deleteButton, updateButton);
                            setGraphic(vbox);

                            setText("First Name: " + item
                                    .getFirstname() +
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

        ObservableList<String> roles = FXCollections.observableArrayList("Admin", "User");
        roleComboBox.setItems(roles);
        chercher.setOnAction(event->advancedSearchUsers());
        String username = LoginController.username;
        TFusername.setText(username);
    }

    @FXML
    private void handleUsersButton(ActionEvent event) {
        // Set the visibility of the ListView to true
        listView.setVisible(true);
    }

    @FXML
    private void handleSave(ActionEvent event) {
        // Get the selected user from the ListView
        User selectedUser = listView.getSelectionModel().getSelectedItem();

        if (selectedUser != null) {
            selectedUser.setFirstname(firstNameField.getText());
            selectedUser.setLastname(lastNameField.getText());
            selectedUser.setUsername(usernameField.getText()); // Update the username
            selectedUser.setEmail(emailField.getText());
            selectedUser.setNumber(numberField.getText());
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
        } else {
            // Show an alert indicating that no user is selected
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("No User Selected");
            alert.setHeaderText(null);
            alert.setContentText("Please select a user before saving changes.");
            alert.showAndWait();
        }
    }

    @FXML
    private void logout(ActionEvent event) {
        // Load the login.fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/login.fxml"));
        try {
            Parent root = loader.load();
            Stage stage = (Stage) logoutbtn.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void advancedSearchUsers() {
        String searchText = chercher.getText().trim().toLowerCase();

        ObservableList<User> filteredUsers = FXCollections.observableArrayList();
        for (User user : userList) {
            if (user.getFirstname().toLowerCase().contains(searchText) ||
                    user.getLastname().toLowerCase().contains(searchText) ||
                    user.getUsername().toLowerCase().contains(searchText) ||
                    user.getEmail().toLowerCase().contains(searchText) ||
                    user.getNumber().toLowerCase().contains(searchText) ||
                    (user.getRole() != null && user.getRole().toLowerCase().contains(searchText))) {
                filteredUsers.add(user);
            }
        }
        listView.setItems(filteredUsers);
        listView.refresh();
    }

}
