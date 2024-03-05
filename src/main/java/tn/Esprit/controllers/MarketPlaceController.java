package tn.Esprit.controllers;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import tn.Esprit.models.MarketPlace;
import tn.Esprit.services.ServiceMarketPlace;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Base64;
import javafx.collections.ObservableList;

public class MarketPlaceController {

    @FXML
    private TableView<MarketPlace> TableViewA;

    @FXML
    private Button button_add;

    @FXML
    private Button button_browse;

    @FXML
    private Button button_delete;

    @FXML
    private Button button_update;

    @FXML
    private FlowPane cardContainer;

    @FXML
    private TableColumn<MarketPlace, Timestamp> col_dateprod;

    @FXML
    private TableColumn<MarketPlace, String> col_descriptionprod;

    @FXML
    private TableColumn<MarketPlace, Integer> col_id_prod;

    @FXML
    private TableColumn<MarketPlace, String> col_nameprod;

    @FXML
    private ImageView imageViewA;

    @FXML
    private DatePicker tf_dateprod;

    @FXML
    private TextField tf_descriptionprod;

    @FXML
    private TextField tf_nameprod;
    @FXML
    private TableColumn<MarketPlace, Image> col_image;
    private FileChooser fileChooser;
    private String base64Image;
    private byte[] imageData; // Store binary data directly


    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    int id = 0;

    ServiceMarketPlace sr = new ServiceMarketPlace();

    @FXML
    public void initialize() {
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image File");
        showMarketPlace();
        //initializeImageColumn();
        //ArrayList<MarketPlace> marketPlaces = sr.getAll();
      //  initializeCards(marketPlaces);
    }
    private void initializeImageColumn() {
        col_image.setCellValueFactory(new PropertyValueFactory<>("image"));
        col_image.setCellFactory(column -> new TableCell<MarketPlace, Image>() {
            private final ImageView imageView = new ImageView();

            @Override
            protected void updateItem(Image image, boolean empty) {
                super.updateItem(image, empty);
                if (empty || image == null) {
                    setGraphic(null);
                } else {
                    imageView.setImage(image);
                    imageView.setFitWidth(50);
                    imageView.setFitHeight(50);
                    setGraphic(imageView);
                }
            }
        });
    }

    public void showMarketPlace() {
        ArrayList<MarketPlace> marketPlaces = sr.getAll();

        // Create a new observable list for TableView
        ObservableList<MarketPlace> observableList = FXCollections.observableArrayList(marketPlaces);

        // Set the items to the TableView
        TableViewA.setItems(observableList);

        // Set the cell value factories for other columns
        col_nameprod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProdName()));
        col_descriptionprod.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getProdDescription()));
        col_dateprod.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getDateProd()));

        // Check if the image column already exists
        boolean imageColumnExists = false;
        for (TableColumn<MarketPlace, ?> column : TableViewA.getColumns()) {
            if (column.getText().equals("Image")) {
                imageColumnExists = true;
                break;
            }
        }

        // If image column doesn't exist, create and add it
        if (!imageColumnExists) {
            TableColumn<MarketPlace, Void> imageColumn = new TableColumn<>("Image");
            imageColumn.setCellFactory(param -> new TableCell<>() {
                private final ImageView imageView = new ImageView();

                @Override
                protected void updateItem(Void item, boolean empty) {
                    super.updateItem(item, empty);

                    // Clear the previous content
                    setGraphic(null);

                    if (!empty) {
                        // Get the MarketPlace object for the current row
                        MarketPlace marketPlace = getTableView().getItems().get(getIndex());

                        // Check if the MarketPlace has an image
                        byte[] imageData = marketPlace.getImage();
                        if (imageData != null && imageData.length > 0) {
                            // Set the image to the ImageView
                            Image image = new Image(new ByteArrayInputStream(imageData));
                            imageView.setImage(image);

                            // Display the image in the cell
                            setGraphic(imageView);
                        }
                    }
                }
            });

            // Add the image column to the TableView
            TableViewA.getColumns().add(imageColumn);
        }
    }



    private void initializeCards(ArrayList<MarketPlace> marketPlaces) {
        cardContainer.getChildren().clear(); // Clear existing cards if any

        for (MarketPlace marketPlace : marketPlaces) {
            BorderPane card = createCard(marketPlace);
            cardContainer.getChildren().add(card);
        }
    }

    private BorderPane createCard(MarketPlace marketPlace) {
        BorderPane card = new BorderPane();
        card.setPrefSize(200, 250);
        card.setStyle("-fx-background-color: #ffffff; -fx-border-color: #1e77bc; -fx-border-width: 2px; -fx-border-radius: 5px;");

        Label nameLabel = new Label(marketPlace.getProdName());
        nameLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14px;");
        Label descriptionLabel = new Label(marketPlace.getProdDescription());
        descriptionLabel.setWrapText(true);

        card.setTop(nameLabel);
        BorderPane.setMargin(nameLabel, new Insets(5, 0, 5, 10));

        card.setCenter(descriptionLabel);
        BorderPane.setMargin(descriptionLabel, new Insets(0, 0, 5, 10));

        card.setOnMouseClicked(event -> handleCardClick(marketPlace));

        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(150); // Adjust the height as needed

        // Set the image if available
       /* byte[] imageData = marketPlace.getImage();
        if (imageData != null && imageData.length > 0) {
            Image image = new Image(new ByteArrayInputStream(imageData));
            imageView.setImage(image);
        }*/
        byte[] imageData = marketPlace.getImage();
        if (imageData != null && imageData.length > 0) {
            InputStream inputStream = new ByteArrayInputStream(imageData);
            Image image = new Image(inputStream);
            imageView.setImage(image);
        }

        card.setBottom(imageView);
        BorderPane.setAlignment(imageView, Pos.CENTER);

        card.setOnMouseClicked(event -> handleCardClick(marketPlace));

        return card;
    }

    private void handleCardClick(MarketPlace marketPlace) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Card Clicked");
        alert.setHeaderText(null);
        alert.setContentText("Clicked on card for product: " + marketPlace.getProdName());
        alert.showAndWait();
        // Add your logic for handling card click event
    }

    @FXML
    void addMarketPlace(ActionEvent event) {
        String name = tf_nameprod.getText();
        String description = tf_descriptionprod.getText();

        if (name.isEmpty() || description.isEmpty() || imageData == null) { // Check for binary data instead of base64Image
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Name, description, and image cannot be empty!");
            alert.showAndWait();
            return;
        }

        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp currentTimestamp = Timestamp.valueOf(currentDateTime);

        MarketPlace newMarketPlace = new MarketPlace(0, name, description, currentTimestamp, imageData);
        sr.add(newMarketPlace);

        ArrayList<MarketPlace> marketPlaces = sr.getAll();
        //initializeCards(marketPlaces);
    }

    @FXML
    void deleteMarketPlace(ActionEvent event) {
        MarketPlace selectedMarketPlace = TableViewA.getSelectionModel().getSelectedItem();
        if (selectedMarketPlace != null) {
            sr.delete(selectedMarketPlace);
            showMarketPlace();
        }
    }

    @FXML
    void updateMarketPlace(ActionEvent event) {
        MarketPlace selectedMarketPlace = TableViewA.getSelectionModel().getSelectedItem();
        if (selectedMarketPlace != null) {
            selectedMarketPlace.setProdName(tf_nameprod.getText());
            selectedMarketPlace.setProdDescription(tf_descriptionprod.getText());
            selectedMarketPlace.setDateProd(Timestamp.valueOf(LocalDateTime.now()));
            sr.update(selectedMarketPlace);
            showMarketPlace();
        }
    }

    @FXML
    void handleImageSelection(ActionEvent event) {
        File file = fileChooser.showOpenDialog(null);
        if (file != null) {
            try {
                byte[] imageData = Files.readAllBytes(file.toPath());
                imageViewA.setImage(new Image(new ByteArrayInputStream(imageData)));
                this.imageData = imageData; // Store binary data directly
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    private String convertImageToBase64(File file) {
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private Image createImageFromBase64(String base64Image) {
        return new Image(new ByteArrayInputStream(Base64.getDecoder().decode(base64Image)));
    }
    @FXML
    void getRecData(MouseEvent event) {
        MarketPlace selectedMarketPlace = TableViewA.getSelectionModel().getSelectedItem();
        if (selectedMarketPlace != null) {
            tf_nameprod.setText(selectedMarketPlace.getProdName());
            tf_descriptionprod.setText(selectedMarketPlace.getProdDescription());
        }
    }

    public void addAvisToMarketPlace(ActionEvent actionEvent) {
    }
}
