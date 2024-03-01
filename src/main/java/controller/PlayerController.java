package controller;

import entities.AvisJoueur;
import entities.User;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TitledPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import service.ServiceAvisJoueur;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class PlayerController implements Initializable {
    @FXML
    VBox MyVbox;
    @FXML
    ScrollPane Mypane;
    @FXML
    static int SelectedUserid;






    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        getGrid();



    }
    public void getGrid() {
        ServiceAvisJoueur serviceAvisJoueur = ServiceAvisJoueur.getInstance();
        List<User> users;
        try {
            users = serviceAvisJoueur.getUsers();
        } catch (SQLException e) {
            e.printStackTrace();
            return; // Handle the exception according to your needs
        }

        for (User user : users) {
            TitledPane titledPane = new TitledPane();

            //------------------------titledpane & gridPane -----------------------
            String css = "-fx-font-size: 16px; -fx-font-family: 'Helvetica Neue'; -fx-text-fill: #333333; -fx-background-color: #FFFFFF; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px; ";
            titledPane.setText("player " + user.getId());
            titledPane.setStyle(css);

            GridPane gridPane = new GridPane();
            gridPane.setStyle(css);

            Label labelCommentaire = new Label("Name : " + user.getName());
            labelCommentaire.setStyle(css);
            gridPane.add(labelCommentaire, 0, 1);

            Label labelemail = new Label("email : " + user.getEmail());
            labelCommentaire.setStyle(css);
            gridPane.add(labelemail, 0, 2);


            gridPane.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    SelectedUserid = user.getId();
                    System.out.println(SelectedUserid);

                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/AddAvisPlayer.fxml"));
                        Parent root = loader.load();
                        Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.setTitle("Gestion Avis");
                        stage.show();

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }


                }
            });


            titledPane.setContent(gridPane);
            MyVbox.getChildren().add(titledPane);
            Mypane.setContent(MyVbox);


        }

    }
}
