package controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class NewsController implements Initializable {


    @FXML
    private TableView<JsonNode> classement;
    @FXML
    private TableColumn<JsonNode, String> teamcol;
    @FXML
    private TableColumn<JsonNode, Integer> playedcol;
    @FXML
    private TableColumn<JsonNode, Integer> wincol;
    @FXML
    private TableColumn<JsonNode, Integer> drawcol;
    @FXML
    private TableColumn<JsonNode, Integer> losscol;
    @FXML
    private TableColumn<JsonNode, Integer> golasforCol;
    @FXML
    private TableColumn<JsonNode, Integer> golasagiCol;
    @FXML
    private TableColumn<JsonNode, Integer> pointsCol;


    @FXML
    private ListView<String> List;


    private OkHttpClient client = new OkHttpClient();
    private ObjectMapper objectMapper = new ObjectMapper();
    private String teamName;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List.setVisible(false);
        try {
            Request request = new Request.Builder()
                    .url("https://heisenbug-premier-league-live-scores-v1.p.rapidapi.com/api/premierleague/table")
                    .get()
                    .addHeader("X-RapidAPI-Key", "ur api key")
                    .addHeader("X-RapidAPI-Host", "heisenbug-premier-league-live-scores-v1.p.rapidapi.com")
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                // Assuming the API response contains an array of team records
                JsonNode recordsArray = jsonNode.get("records");
                for (JsonNode record : recordsArray) {
                    classement.getItems().add(record);
                }

                // Set a custom cell factory to handle mouse clicks
                teamcol.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().get("team").asText()));
                teamcol.setCellFactory(column -> new TableCell<JsonNode, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        setText(empty ? null : item);
                    }

                    {
                        setOnMouseClicked(event -> {
                            if (!isEmpty() && event.getClickCount() == 1) {
                                JsonNode selectedTeam = getTableRow().getItem();
                                if (selectedTeam != null) {
                                    teamName = selectedTeam.get("team").asText();
                                    System.out.println("Selected Team: " + teamName);
                                    List.setVisible(true);
                                    InfoEquipe();
                                }
                            } else {
                                List.setVisible(false);
                            }
                        });
                    }
                });


                playedcol.setCellValueFactory(cellData -> {
                    JsonNode node = cellData.getValue();
                    // Assuming "points" is an integer property
                    int points = node.get("played").asInt();
                    return new SimpleIntegerProperty(points).asObject();
                });


                wincol.setCellValueFactory(cellData -> {
                    JsonNode node = cellData.getValue();
                    // Assuming "points" is an integer property
                    int points = node.get("win").asInt();
                    return new SimpleIntegerProperty(points).asObject();
                });


                drawcol.setCellValueFactory(cellData -> {
                    JsonNode node = cellData.getValue();
                    // Assuming "points" is an integer property
                    int points = node.get("draw").asInt();
                    return new SimpleIntegerProperty(points).asObject();
                });

                losscol.setCellValueFactory(cellData -> {
                    JsonNode node = cellData.getValue();
                    // Assuming "points" is an integer property
                    int points = node.get("loss").asInt();
                    return new SimpleIntegerProperty(points).asObject();
                });

                golasforCol.setCellValueFactory(cellData -> {
                    JsonNode node = cellData.getValue();
                    // Assuming "points" is an integer property
                    int points = node.get("goalsFor").asInt();
                    return new SimpleIntegerProperty(points).asObject();
                });

                golasagiCol.setCellValueFactory(cellData -> {
                    JsonNode node = cellData.getValue();
                    // Assuming "points" is an integer property
                    int points = node.get("goalsAgainst").asInt();
                    return new SimpleIntegerProperty(points).asObject();
                });

// Use IntegerPropertyValueFactory for "points" column
                pointsCol.setCellValueFactory(cellData -> {
                    JsonNode node = cellData.getValue();
                    // Assuming "points" is an integer property
                    int points = node.get("points").asInt();
                    return new SimpleIntegerProperty(points).asObject();
                });


            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    void InfoEquipe() {
        try {
            Request request = new Request.Builder()
                    .url("https://heisenbug-premier-league-live-scores-v1.p.rapidapi.com/api/premierleague/team?name=" + teamName)
                    .get()
                    .addHeader("X-RapidAPI-Key", "ur api key")
                    .addHeader("X-RapidAPI-Host", "heisenbug-premier-league-live-scores-v1.p.rapidapi.com")
                    .build();

            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String responseBody = response.body().string();
                JsonNode jsonNode = objectMapper.readTree(responseBody);

                // Clear existing items in the ListView
                List.getItems().clear();

                // Add new information to the ListView
                List.getItems().add("League : " + jsonNode.get("league").asText());
                List.getItems().add("Season : " + jsonNode.get("season").asText());
                List.getItems().add("Name : " + jsonNode.get("name").asText());
                List.getItems().add("Official Name : " + jsonNode.get("officialName").asText());
                List.getItems().add("Address : " + jsonNode.get("address").asText());
                List.getItems().add("Website : " + jsonNode.get("website").asText());
                List.getItems().add("Founded : " + jsonNode.get("founded").asText());
                List.getItems().add("Team Size : " + jsonNode.get("teamSize").asInt());
                List.getItems().add("Average Age : " + jsonNode.get("averageAge").asDouble());
                List.getItems().add("Foreigners : " + jsonNode.get("foreigners").asInt());
                List.getItems().add("National Team Players : " + jsonNode.get("nationaTeamPlayers").asInt());
                List.getItems().add("Team Value : " + jsonNode.get("teamValue").asText());
                List.getItems().add("Venue : " + jsonNode.get("venue").asText());
                List.getItems().add("Venue Capacity : " + jsonNode.get("venueCapacity").asInt());

                // Extract manager information and add to the ListView
                JsonNode manager = jsonNode.get("managers").get(0);
                List.getItems().add("Manager Information:");
                List.getItems().add("Name: " + manager.get("name").asText());
                List.getItems().add("Age: " + manager.get("age").asInt());
                List.getItems().add("Appointed: " + manager.get("appointed").asText());
                List.getItems().add("Contract Expires: " + manager.get("contractExpires").asText());
                List.getItems().add("Active: " + manager.get("active").asText());

            } else {
                System.out.println("Request failed with code: " + response.code());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
