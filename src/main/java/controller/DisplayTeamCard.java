package controller;

import entities.Equipe;
import entities.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import service.ServiceEquipe;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/*
*
   Author: @Ghazi saoudi
*
*/



public class DisplayTeamCard implements Initializable {

    @FXML
    Pane Mypane;
    @FXML
   ImageView teampic,nextteam,perviousteam;
    @FXML
    Label teamname;
    @FXML
    Label dateteam;
    @FXML
    Label listeplayer;


    private List<Equipe> equipes;
    static List<User> allPlayers = new ArrayList<>();
    private int currentIndex = 0;
    ServiceEquipe serviceEquipe = new ServiceEquipe();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            equipes = serviceEquipe.recuperer();
            displayTeam(equipes.get(currentIndex));
        } catch (SQLException e) {
            e.printStackTrace();
        }



        nextteam.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentIndex = (currentIndex + 1) % equipes.size();
                displayTeam(equipes.get(currentIndex));

            }
        });

        perviousteam.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                currentIndex = (currentIndex - 1 + equipes.size()) % equipes.size();
                displayTeam(equipes.get(currentIndex));

            }
        });
        teampic.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

                try {
                    System.out.println(allPlayers);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/Formation.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) Mypane.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Gestion Equipe");
                    stage.show();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

            }


        });


    }

    private void displayTeam(Equipe equipe) {
        try {
            teamname.setText(equipe.getNom());
            dateteam.setText(equipe.getDateCreation().toString());
           //listeplayer.setText(String.join(", ", equipe.getJouers().stream().map(User::getName).collect(Collectors.toList())));
           // listeplayer.setWrapText(true);
            listeplayer.setText("Squad size : " + String.valueOf(equipe.getJouers().size()));
            Image image = new Image("file:" + equipe.getImage());
            teampic.setImage(image);
            allPlayers = equipe.getJouers();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Invalid URL: " + equipe.getImage());
        }
    }



}
