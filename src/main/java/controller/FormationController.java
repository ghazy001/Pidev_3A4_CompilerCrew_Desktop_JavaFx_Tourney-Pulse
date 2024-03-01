package controller;

import entities.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class FormationController implements Initializable {




    @FXML
    private Label CF;

    @FXML
    private Label GK;

    @FXML
    private Label LB;

    @FXML
    private Label RB;

    @FXML
    Label ST;
    @FXML
    Label substitution;
    @FXML
    ImageView home;




    List<User> Players = DisplayTeamCard.allPlayers;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        for (int i = 0; i < Players.size(); i++) {
            switch (i) {
                case 0:
                    GK.setText(Players.get(i).getName());
                    break;
                case 1:
                    LB.setText(Players.get(i).getName());
                    break;
                case 2:
                    RB.setText(Players.get(i).getName());
                    break;
                case 3:
                    CF.setText(Players.get(i).getName());
                    break;
                case 4:
                    ST.setText(Players.get(i).getName());
                    break;
                default:
                    // Additional players for substitution
                    if (i > 4) {
                        addPlayerToSubstitution(Players.get(i).getName());
                    }
                    break;
            }
        }

        fillRemainingLabels();


        home.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/HOME.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) home.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("HOME");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });
    }

    private void fillRemainingLabels() {
        fillLabelIfEmpty(GK);
        fillLabelIfEmpty(LB);
        fillLabelIfEmpty(RB);
        fillLabelIfEmpty(CF);
        fillLabelIfEmpty(ST);
        // No need to fill substitution here, as it will be handled dynamically
    }

    private void fillLabelIfEmpty(Label label) {
        if (label.getText() == null || label.getText().isEmpty()) {

            label.setText("vide");
        }
    }

    private void addPlayerToSubstitution(String playerName) {
        substitution.setText(substitution.getText() + playerName + ", ");
        substitution.setWrapText(true);
    }
}
