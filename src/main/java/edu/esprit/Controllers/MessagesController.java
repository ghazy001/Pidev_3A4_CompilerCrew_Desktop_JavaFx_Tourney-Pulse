package edu.esprit.Controllers;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import edu.esprit.Services.ServiceMessages;
import edu.esprit.Services.ServiceReclamation;
import edu.esprit.entities.Messages;
import edu.esprit.entities.Reclamation;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javafx.geometry.Insets;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.Date;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MessagesController implements Initializable {


    @FXML
    private Text contentId;

    @FXML
    private Text dateId;

    @FXML
    private AnchorPane listMessages;

    @FXML
    private Button message;

    @FXML
    private Pane messageId;

    @FXML
    private Button navigate;

    @FXML
    private Text objetRec;

    @FXML
    private Text recText;

    @FXML
    private ScrollPane scrollId;
    @FXML
    private Text userId;
    @FXML
    private TextField messageContent;
    private int user_id,rec_id;


    public void setUser_id(int user_id) {
        this.user_id = user_id;
        loadMessages();
    }



    public void setRec_id(int rec_id) {

        this.rec_id = rec_id;
        loadReclamation();
        loadMessages();
    }

private ServiceReclamation serviceReclamation=new ServiceReclamation();
    private ServiceMessages messagesService = new ServiceMessages();
    public void loadReclamation(){
        Reclamation reclamation=serviceReclamation.getReclamationById(rec_id);
        objetRec.setText(reclamation.getObject());
        recText.setText(reclamation.getRec());
    }
    @FXML
    void handleAddMessage(ActionEvent event) {
        System.out.println(rec_id+"   "+user_id);
        // Check for bad words
        if (checkForBadWords()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Message contains inappropriate content!");
            alert.show();
            messageContent.clear();
            return; // Exit the method if bad words are detected
        }


        String contentText=messageContent.getText();
        if(contentText!=null && !contentText.isEmpty()){
            messagesService.ajouter(new Messages(1,10,rec_id,contentText,new Date(System.currentTimeMillis())));
            loadMessages();


        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur!");
            alert.setContentText("Message est vide!!");

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        startRefreshingMessages();
        loadMessages();
        startRefreshingMessages();
        System.out.println("bbbbbbbbbbbbbbbbbb");

    }

   /* public void loadMessages(){

        List<Messages> messages = messagesService.getMessagesByReclamationId(rec_id);

        double currentY = 0.0; // Initialize Y position

        for (Messages message : messages) {
            Pane messagePane = createMessagePane(message);
            messagePane.setLayoutY(currentY); // Set Y position
            listMessages.getChildren().add(messagePane);
            currentY += messagePane.getPrefHeight() + 10; // Update Y position with spacing
        }
    }*/

    public void startRefreshingMessages() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(() -> {
            Platform.runLater(() -> {
                loadMessages();
            });
        }, 0, 1, TimeUnit.SECONDS); // Rafraîchir toutes les 1 seconde
    }

    public void loadMessages() {
        if (rec_id != 0) {
            List<Messages> messages = messagesService.getMessagesByReclamationId(rec_id);
            final double[] currentY = {0.0};

            Platform.runLater(() -> {
                boolean nearBottom = isNearBottom(scrollId);
                listMessages.getChildren().clear();

                for (Messages message : messages) {
                    Pane messagePane = createMessagePane(message);
                    messagePane.setLayoutY(currentY[0]);
                    listMessages.getChildren().add(messagePane);
                    currentY[0] += messagePane.getPrefHeight() + 10;
                }
                listMessages.setPrefHeight(currentY[0] + 20);

                if (nearBottom) {
                    scrollId.setVvalue(1.0);
                }
            });
        }
    }

    private boolean isNearBottom(ScrollPane scrollPane) {
        double threshold = 0.95; // Adjust as necessary
        return scrollPane.getVvalue() > threshold;
    }
    private Pane createMessagePane(Messages message) {

        Pane pane = new Pane();
        pane.setPrefSize(772, 126); // Set the preferred size for the pane

        Text userIdText = new Text(message.getRole()+" : " + message.getName()); // Example, adjust as needed
        userIdText.setLayoutX(34);
        userIdText.setLayoutY(27);

        Text contentText = new Text(message.getContent());
        contentText.setFont(new Font(21));
        contentText.setLayoutX(15);
        contentText.setLayoutY(72);

        Text dateText = new Text(message.getDate().toString());
        dateText.setFont(new Font(15));
        dateText.setFill(Color.web("#cccbcb"));
        dateText.setLayoutX(541);
        dateText.setLayoutY(23);
        FontAwesomeIconView icon =new FontAwesomeIconView(FontAwesomeIcon.USER);
        icon.setFill(Color.BLACK);
        icon.setSize("25");
        icon.setLayoutX(10);
        icon.setLayoutY(29);
        pane.getChildren().addAll(userIdText, contentText, dateText,icon); // Add all texts to the pane

        return pane;
    }
 @FXML
    void handleBack(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/DisplayReclamation.fxml"));

            // Create a Scene with custom dimensions
            Scene scene = new Scene(root, 800, 600); // Adjust width and height as needed

            // Get the current stage
            Stage stage = (Stage) messageContent.getScene().getWindow();

            // Set the new scene to the stage
            stage.setScene(scene);


        } catch (IOException var4) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Sorry");
            alert.setTitle("Error");
            alert.show();
        }

    }

    public boolean checkForBadWords() {
        try {
            String apiKey = ""; // Replace with your API key
            String text = messageContent.getText().trim();
            String encodedText = URLEncoder.encode(text, StandardCharsets.UTF_8.toString());

            // Initialize URL object with properly encoded text
            URL url = new URL("https://api.api-ninjas.com/v1/profanityfilter?text=" + encodedText);

            // Open connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Set request method
            connection.setRequestMethod("GET");

            // Set API key in the request header
            connection.setRequestProperty("X-Api-Key", apiKey);

            // Set accept header
            connection.setRequestProperty("accept", "application/json");

            // Get response code
            int responseCode = connection.getResponseCode();

            // Check if the request was successful
            if (responseCode == HttpURLConnection.HTTP_OK) {
                // Read response
                try (InputStream responseStream = connection.getInputStream()) {
                    ObjectMapper mapper = new ObjectMapper();
                    JsonNode root = mapper.readTree(responseStream);

                    // Extract censored word
                    String censoredWord = root.get("censored").asText();

                    System.out.println("Censored Word: " + censoredWord);

                    // Check if censoredWord is not empty, indicating the presence of bad words
                    return !censoredWord.isEmpty() && !censoredWord.equals(text); // Return true if censored word is found and not equal to the original text
                }
            } else {
                // Handle unsuccessful response
                throw new RuntimeException("Error: HTTP error code: " + responseCode);
            }
        } catch (IOException e) {
            throw new RuntimeException("Error connecting to API", e);
        }
    }




}
