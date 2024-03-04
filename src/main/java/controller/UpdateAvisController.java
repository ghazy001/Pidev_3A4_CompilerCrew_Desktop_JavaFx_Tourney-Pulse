package controller;
import static controller.ShowAvisController.*;

import entities.AvisJoueur;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Rating;
import service.ServiceAvisJoueur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/*
*
   Author: @Ghazi saoudi
*
*/



public class UpdateAvisController implements Initializable {


    @FXML
    public TextField date;
    @FXML
    public TextArea com;
    @FXML
    public Rating rating;
    @FXML
    public Button update;

    @FXML
    private Button returnb;
    @FXML
    private Button delete;
    @FXML
    private Button home,btnEquipe;
    @FXML
    VBox myBox;




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        //--------------Hover-------------
        FadeTransition ft = new FadeTransition(Duration.millis(300), myBox);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        myBox.setOpacity(0.0);

        // Set up mouse entered event

        myBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //System.out.println("Mouse DAKHLEEET");
                ft.setRate(-1); // Reverse the animation
                ft.play(); // Play the animation
            }
        });
        // Set up mouse Exited event
        myBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // System.out.println("Mouse KHARJEEET");

                ft.setRate(3); // Play the animation in the forward direction
                ft.play(); // Make the VBox invisible (but still interactable) when the mouse exits
            }
        });






        //-----------------

      com.setText(commSelect);
      rating.setRating(noteSelect);
      //id.setText(String.valueOf(idSelct));
      date.setText(dateSelec);
     // idAvis.setText(String.valueOf(idAvisSelct));





      ServiceAvisJoueur serviceAvisJoueur = ServiceAvisJoueur.getInstance();

      //----------------------update--------------------------------
      update.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
             float noteupdated = (float) rating.getRating();
             int idj = idSelct;
           int idAvisUp = idAvisSelct;

              AvisJoueur avisJoueur = new AvisJoueur();
              avisJoueur.setCommentaire(com.getText());
              avisJoueur.setNote((float) rating.getRating());


              try {
                  serviceAvisJoueur.modifier(idAvisUp,avisJoueur,idj);
                  Alert alert = new Alert(Alert.AlertType.INFORMATION);
                  alert.setTitle("Success");
                  alert.setContentText("Merci de modfier votre avis");
                  alert.show();

                  FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowAvisPLayer.fxml"));
                  Parent root = loader.load();
                  Stage stage = (Stage) delete.getScene().getWindow();
                  stage.setScene(new Scene(root));
                  stage.setTitle("Gestion Avis");
                  stage.show();


              } catch (SQLException | IOException e) {
                  throw new RuntimeException(e);
              }

          }
      });


      //---------------------------return------------------------------------------------
      returnb.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowAvisPLayer.fxml"));
              try {
                  Parent root = loader.load();
                  Stage stage = (Stage) returnb.getScene().getWindow();
                  stage.setScene(new Scene(root));
                  stage.setTitle("Display");
                  stage.show();
              } catch (IOException e) {
                  throw new RuntimeException(e);
              }
          }
      });
       //----------------------delete function--------------------------------------------
      delete.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
              Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
              alert.setTitle("Alert");
              alert.setContentText("are you sure! do you really want to delete this avis");
              Optional<ButtonType> result =alert.showAndWait();
              if (result.get()==ButtonType.CANCEL){
                  return;
              } else if (result.get()==ButtonType.OK) {
                  try {

                          serviceAvisJoueur.supprimer(idAvisSelct);


                      FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowAvisPLayer.fxml"));
                      Parent root = loader.load();
                      Stage stage = (Stage) delete.getScene().getWindow();
                      stage.setScene(new Scene(root));
                      stage.setTitle("update");
                      stage.show();

                  }catch (SQLException e)
                  {e.printStackTrace();}
                  catch (IOException e) {
                      e.printStackTrace();
                  }
              }
          }
      });

      home.setOnAction(new EventHandler<ActionEvent>() {
          @Override
          public void handle(ActionEvent actionEvent) {
              FXMLLoader loader = new FXMLLoader(getClass().getResource("/HOME.fxml"));
              try {
                  Parent root = loader.load();
                  Stage stage = (Stage) home.getScene().getWindow();
                  stage.setScene(new Scene(root));
                  stage.setTitle("HOME");
                  stage.show();
              } catch (IOException e) {
                  throw new RuntimeException(e);
              }
          }
      });

      //-------------------- go to equipe for admin ----------------

        btnEquipe.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayEquipe.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) home.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Gestion Equipe");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });







    }
}
