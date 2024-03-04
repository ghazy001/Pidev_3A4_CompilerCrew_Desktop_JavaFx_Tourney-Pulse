package controller;

import entities.AvisJoueur;
import esprit.project.tools.MyDB;
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
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.controlsfx.control.Rating;
import service.ServiceAvisJoueur;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

/*
*
   Author: @Ghazi saoudi
*
*/


public class ShowAvisController implements Initializable {

    ServiceAvisJoueur serviceAvisJoueur = ServiceAvisJoueur.getInstance();
      static ResultSet rs;
      static Integer idSelct;
      static Integer idAvisSelct;
      static String commSelect;
      static float noteSelect;
      static String dateSelec;




    @FXML
    private VBox mainContainer;
    @FXML
    ScrollPane pane;

  

    @FXML
    private AnchorPane Pane;
    public void getGrid() {
        // Call recuperer() to get the data
        List<AvisJoueur> avisList;
        try {
            avisList = serviceAvisJoueur.recuperer();
        } catch (SQLException e) {
            e.printStackTrace();
            return; // Handle the exception according to your needs
        }

        // top rating b stream
        List<AvisJoueur> sortedAvisList = avisList.stream()
                .sorted(Comparator.comparing(AvisJoueur::getNote).reversed())
                .collect(Collectors.toList());

        // Add data to the VBox
        for (AvisJoueur avis : sortedAvisList) {
            TitledPane titledPane = new TitledPane();
            titledPane.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent mouseEvent) {

                    if (mouseEvent.getButton() == MouseButton.PRIMARY && mouseEvent.getClickCount() == 2) {

                        // Retrieve the idAvis when the TitledPane is clicked
                        int clickedIdAvis = avis.getIdAvis();
                        System.out.println("Clicked on TitledPane with idAvis: " + clickedIdAvis);


                        try {
                            Connection connection1 = MyDB.getInsatnce().getConnection();
                            PreparedStatement pre = connection1.prepareStatement("SELECT * FROM avisjoueur WHERE idAvis = ?");
                            pre.setInt(1, clickedIdAvis);
                            rs = pre.executeQuery();
                            while (rs.next()) {
                                idAvisSelct = Integer.valueOf(rs.getString("idAvis"));
                                System.out.println(idAvisSelct);
                                idSelct = Integer.valueOf(rs.getString("idJoueur"));
                                System.out.println(idSelct);
                                commSelect = rs.getString("commentaire");
                                System.out.println(commSelect);
                                noteSelect = Float.parseFloat(rs.getString("note"));
                                System.out.println(noteSelect);
                                dateSelec = rs.getString("dateAvis");
                                System.out.println(dateSelec);


                                FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateAvis.fxml"));
                                Parent root = loader.load();
                                Stage stage = (Stage) ((Node) mouseEvent.getSource()).getScene().getWindow();
                                //stage.setScene(new Scene(root, 900, 900));
                                stage.setScene(new Scene(root));
                                stage.setTitle("Gestion Avis");
                                stage.show();


                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
                });




           //------------------------titledpane & gridPane -----------------------
            String css = "-fx-font-size: 16px; -fx-font-family: 'Helvetica Neue'; -fx-text-fill: #333333; -fx-background-color: #FFFFFF; -fx-padding: 10px; -fx-border-radius: 5px; -fx-background-radius: 5px; ";
            titledPane.setText("Avis " + avis.getIdAvis());
            titledPane.setStyle(css);

            GridPane gridPane = new GridPane();
            gridPane.setStyle(css);

            Label labelCommentaire = new Label("Commentaire : " + avis.getCommentaire());
            labelCommentaire.setStyle(css);
            gridPane.add(labelCommentaire, 0, 1);

           // Use Rating control for the "Note" field
            Rating rating = new Rating();
            rating.setRating(avis.getNote());
            rating.setDisable(true); // Make it read-only
            rating.setPrefSize(50, 25);
            gridPane.add(new Label(""), 1, 2);
            gridPane.add(rating, 1, 2);

            Label labelJoueur = new Label("Joueur : " + avis.getUser().getName());
            labelJoueur.setStyle(css);
            gridPane.add(labelJoueur, 0, 3);

            Label labelDate = new Label("Date : " + avis.getDateAvis());
            labelDate.setStyle(css);
            gridPane.add(labelDate, 0, 4);



            titledPane.setContent(gridPane);
            mainContainer.getChildren().add(titledPane);
            pane.setContent(mainContainer);



        }


    }
    //--------------scroll function--------------------
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        getGrid();

    }
}
