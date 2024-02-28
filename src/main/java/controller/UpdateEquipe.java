package controller;

import entities.Equipe;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import service.ServiceEquipe;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;
import static controller.DisplayEquipe.*;
import static controller.ShowAvis.idAvisSelct;
/*
*
   Author: Ghazi saoudi
*
*/



public class UpdateEquipe implements Initializable {

    @FXML
    ImageView image;
    @FXML
    Button upload,update,delete,home,JoinTeam;
    @FXML
    TextField Nomequipe;
    @FXML
    DatePicker Dateequipe;
    @FXML
    VBox myBox;



    Date Selcdate = DisplayEquipe.selectedDate;
    String SelcNom = DisplayEquipe.selectedNom;
    String Selcimage = DisplayEquipe.selectedImage;

    int Selcid = DisplayEquipe.selectedId;


    ServiceEquipe serviceEquipe = new ServiceEquipe();


    private String imagePath;
    private void chooseImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            imagePath = selectedFile.getAbsolutePath(); // Save the image path
            Image selectedImage = new Image(new File(imagePath).toURI().toString());
            image.setImage(selectedImage);
        }
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {



        //-------------------------Hover Side bar ---------------------------------
        FadeTransition ft = new FadeTransition(Duration.millis(300), myBox);
        ft.setFromValue(1.0);
        ft.setToValue(0.0);
        myBox.setOpacity(0.0);

        // Set up mouse entered event

        myBox.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //System.out.println("Mouse Entered");
                // myBox.setOpacity(1.0); // Make the VBox fully visible when the mouse enters
                ft.setRate(-1); // Reverse the animation
                ft.play(); // Play the animation
            }
        });
        // Set up mouse Exited event
        myBox.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                // System.out.println("Mouse Exited");
                //myBox.setOpacity(0.0);
                ft.setRate(3); // Play the animation in the forward direction
                ft.play(); // Make the VBox invisible (but still interactable) when the mouse exits
            }
        });







//-------------------------set data in my filed------------------------------------
        Nomequipe.setText(selectedNom);
        LocalDate local = java.sql.Date.valueOf(String.valueOf(Selcdate)).toLocalDate();
        Dateequipe.setValue(local);
        Image selectedImage = new Image(new File(Selcimage).toURI().toString());
        image.setImage(selectedImage);



        //-------------upload my image--------------
        upload.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                chooseImage();
            }
        });
        //------------------------update my team-------------
        update.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Equipe equipe = new Equipe();
                equipe.setNom(Nomequipe.getText());
                LocalDate localDate = Dateequipe.getValue();
                java.sql.Date sqlDate = java.sql.Date.valueOf(localDate);
                equipe.setDateCreation(sqlDate);

                // Check if imagePath is null
                if (imagePath != null) {
                    equipe.setImage(imagePath);
                } else {
                    equipe.setImage(Selcimage); // Set to current image if no new image is selected
                }

                try {

                    serviceEquipe.modifier(Selcid,equipe);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setContentText("Merci de modfier votre Equipe");
                    alert.show();

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayEquipe.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) update.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Gestion Equipe");
                    stage.show();

                } catch (SQLException | IOException e ) {
                    throw new RuntimeException(e);
                }
            }
        });

        delete.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Alert");
                alert.setContentText("are you sure! do you really want to delete this team");
                Optional<ButtonType> result =alert.showAndWait();
                if (result.get()==ButtonType.CANCEL){
                    return;
                } else if (result.get()==ButtonType.OK) {

                    try {
                        serviceEquipe.supprimer(selectedId);
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayEquipe.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) delete.getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Gestion Equipe");
                        stage.show();
                    } catch (IOException | SQLException e ) {
                        throw new RuntimeException(e);
                    }


                    }
                }
        });

        //-----------------switch to home ---------------------
        home.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/HOME.fxml"));
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
        //--------------switching to build team
        JoinTeam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffecterPlayers.fxml"));
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
