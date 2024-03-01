package controller;
import entities.AvisJoueur;
import entities.User;
import esprit.project.tools.MyDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import service.ServiceAvisJoueur;


import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.*;
import javafx.animation.FadeTransition;
import javafx.util.Duration;

/*
*
   Author: @Ghazi saoudi
*
*/



public class AddAvisController implements Initializable {





    @FXML
    private Button buttonone;
    @FXML
    private Button ajouter;
    @FXML
    private TextArea commaintre;
    @FXML
    private DatePicker date;
   // @FXML
   // private ComboBox<String> idplayer;

    @FXML
    private Rating rating;
    @FXML
    VBox myBox;
    @FXML
    AnchorPane myAnchorPane;





    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //-------------------------------Side Bar Hover------------------------------
        FadeTransition ft = new FadeTransition(Duration.millis(300), myBox);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        myBox.setOpacity(0.0);

        // Set up mouse entered event

        myBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ft.setRate(-1); // Reverse the animation
                ft.play(); // Play the animation
            }
        });
        // Set up mouse Exited event
        myBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                ft.setRate(3); // Play the animation in the forward direction
                ft.play(); // Make the VBox invisible (but still interactable) when the mouse exits
            }
        });




        //------------------------------switching page----------------------------
        buttonone.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/HOME.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) buttonone.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("HOME");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

       // set up combobox
        //Map<Integer,String> items=setUpComboBox();
       // ObservableList<String> observableList = FXCollections.observableArrayList(items.values());
      //  idplayer.setItems(observableList);
        //------------------------------add function----------------------------

        ajouter.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                ServiceAvisJoueur serviceAvis = ServiceAvisJoueur.getInstance();
                AvisJoueur avisJoueur = new AvisJoueur();
                User user = new User();

                String Comm = commaintre.getText();
                boolean isCommentaireEmpty = Comm.trim().isEmpty();
                //boolean isIdPayerEmpty=idplayer.getValue() == null;
                //boolean isRatingEmpty= rating.isDisabled();



                if ( isCommentaireEmpty ){  //isIdPayerEmpty ) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input is empty");
                    alert.setContentText("Input or select fields can't be empty");
                    alert.show();
                    return;

                }

                avisJoueur.setCommentaire(commaintre.getText());
                avisJoueur.setDateAvis(LocalDate.now());
                float note = (float) rating.getRating();
                avisJoueur.setNote(note);


               // Integer selectedKey = getKeyByValue(items, idplayer.getValue());








                    // Set the user ID and save the avisJoueur
                    user.setId(PlayerController.SelectedUserid);


                    try {
                        serviceAvis.ajouter(avisJoueur, user);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Success");
                        alert.setContentText("Merci de nous donner votre avis");
                        alert.show();
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowAvisPLayer.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) ajouter.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Gestion Avis");
                        stage.show();

                    } catch (SQLException | IOException e) {
                        e.printStackTrace();
                    }
            }

        });



    }

    Map<Integer,String> setUpComboBox() {
        Map<Integer,String> items = new HashMap<Integer,String>();


        try {
            String sql = "SELECT id,name FROM user";
            Statement stmt = MyDB.getInsatnce().getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                items.put(rs.getInt("id"),rs.getString("name"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println(items);
        return items;
    }

    private <K, V> K getKeyByValue(Map<K, V> map, V value) {
        for (Map.Entry<K, V> entry : map.entrySet()) {
            if (value.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
    }





}
