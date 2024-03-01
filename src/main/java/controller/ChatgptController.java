package controller;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import javafx.scene.image.ImageView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.util.Duration;



/*
*
   Author: @Ghazi saoudi
*
*/



public class ChatgptController implements Initializable {

    @FXML
    private Label respondchat;
    @FXML
    private Button send;
    @FXML
    private TextArea talktochat;
    @FXML
    private ImageView image;
    @FXML
    private ImageView home;
    @FXML
    private Label Ai;



    public static String Read_Key_From_env() {
        String apiKey = null;

        try {
            Dotenv dotenv = Dotenv.load();
            apiKey = dotenv.get("MPD");
            System.out.println("API Key: " + apiKey);
        } catch (DotenvException e) {
            System.err.println("Error loading .env file: " + e.getMessage());
        }

        return apiKey;
    }




    private static final String API_KEY = "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";
    private static final String GPT_MODEL = "gpt-3.5-turbo";
    private static final String API_URL = "https://api.openai.com/v1/chat/completions";

    @FXML
    private void handleSendButtonAction(ActionEvent event) {
        String message = talktochat.getText();
        String response = chatGPT(message);
        respondchat.setWrapText(true);
        respondchat.setText("Tourney :" + response);
    }

    private static String chatGPT(String message) {
        try {
            URL obj = new URL(API_URL);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("Authorization", "Bearer " + API_KEY);
            con.setRequestProperty("Content-Type", "application/json");

            String body = "{\"model\": \"" + GPT_MODEL + "\", \"messages\": [{\"role\": \"user\", \"content\": \"" + message + "\"}]}";
            con.setDoOutput(true);

            try (OutputStreamWriter writer = new OutputStreamWriter(con.getOutputStream())) {
                writer.write(body);
            }

            int responseCode = con.getResponseCode();
            System.out.println("Response Code : " + responseCode);
            System.out.println("Response Message : " + con.getResponseMessage());

            if (responseCode == 429) {
                // Handle rate limit here
                return "Rate limit reached. Please try again later.";
            }

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                return extractContentFromResponse(response.toString());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    private static String extractContentFromResponse(String response) {
        int startMarker = response.indexOf("content") + 11;
        int endMarker = response.indexOf("\"", startMarker);
        return response.substring(startMarker, endMarker);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {





        send.setOnAction(this::handleSendButtonAction);

        //------------ animation for tourney ----------

        TranslateTransition translateTransition = new TranslateTransition();
        translateTransition.setNode(image);
        translateTransition.setByX(-350);
        translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        translateTransition.setDuration(Duration.seconds(3));
        translateTransition.play();

        ScaleTransition transition = new ScaleTransition();
        transition.setNode(Ai);
       // translateTransition.setByX(-260);
        //translateTransition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.setDuration(Duration.seconds(1));
        transition.setCycleCount(TranslateTransition.INDEFINITE);
        transition.setByX(0.3);
        transition.setByY(0.3);

        transition.setAutoReverse(true);

        transition.play();




        //----------Switching To Home --------
        home.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/HOME.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) talktochat.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("HOME");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
    }
}
