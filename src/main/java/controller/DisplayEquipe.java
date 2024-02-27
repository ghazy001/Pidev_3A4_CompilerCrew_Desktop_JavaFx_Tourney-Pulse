package controller;

import entities.Equipe;
import entities.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import service.ServiceEquipe;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;


/*
*
   Author: Ghazi saoudi
*
*/



public class DisplayEquipe implements Initializable {

    @FXML
    ListView<Equipe> Mylist;


    public ObservableList<Equipe> gameList;
    ServiceEquipe serviceEquipe = ServiceEquipe.getInstance();
    List<Equipe> ListEquipe;

     static String selectedNom;
     static Date selectedDate;
     static String selectedImage;
     static int selectedId;

    {
        try {
            ListEquipe = serviceEquipe.recuperer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //------------------------display------------------------

        gameList = FXCollections.observableList(ListEquipe);
        Mylist.setItems(gameList);
        Mylist.setCellFactory(param -> new ListCell<>() {
            private final ImageView imageView = new ImageView();

            public void updateItem(Equipe equipe, boolean empty) {
                super.updateItem(equipe, empty);

                if (empty || equipe == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    Image image = new Image("file:" + equipe.getImage());

                    imageView.setImage(image);
                    imageView.setFitHeight(80);
                    imageView.setFitWidth(80);
                    imageView.setPreserveRatio(true);

                    StringBuilder jouersText = new StringBuilder();
                    for (User user : equipe.getJouers()) {
                        jouersText.append(user.getName()).append(", ");
                    }

                    if (jouersText.length() > 0) {
                        jouersText.setLength(jouersText.length() - 2);
                    }

                    String team = "TEAM";
                    String players = "PLAYERS :";
                    String date = "DATE";

                    String text = "\n" + equipe.getNom() + "\n" +
                            players + jouersText.toString() +
                            "\n" + String.valueOf(equipe.getDateCreation());
                    setText(text);
                    setGraphic(imageView);
                }
            }
        });

        //------------------------ Add a selection listener to the ListView ---------------------
        Mylist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                // Handle the selected item

                 selectedNom = newValue.getNom();
                 selectedDate =newValue.getDateCreation();
                 selectedImage = newValue.getImage();
                 selectedId = newValue.getId();

                // Now you have the selected data, you can use it as needed
                System.out.println("Selected Nom: " + selectedNom);
                System.out.println("Selected Date: " + selectedDate);
                System.out.println("Selected Image: " + selectedImage);
                System.out.println("Selected Id: " + selectedId);

                //--------------change page----------



                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateEquipe .fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) Mylist.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Gestion Equipe");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace(); // Print the exception stack trace
                    throw new RuntimeException(e);
                }



            }
        });


    }
}
