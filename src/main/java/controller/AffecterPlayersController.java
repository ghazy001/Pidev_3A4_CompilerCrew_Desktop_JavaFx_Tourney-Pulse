/*
*
   Author: @Ghazy001
   * ghazi saoudi
*
*/


package controller;




import entities.Equipe;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import service.ServiceEquipe;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;


/*
*
   Author: @Ghazi saoudi
*
*/




public class AffecterPlayersController implements Initializable {

    ServiceEquipe serviceEquipe = ServiceEquipe.getInstance();

    @FXML
    ComboBox<String> players,teams;
    @FXML
    Button add;
    @FXML
    ImageView BackHome;









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

    Map<Integer,String> setUpComboBoxTeams() {
        Map<Integer,String> items = new HashMap<Integer,String>();


        try {
            String sql = "SELECT id,nom FROM equipe";
            Statement stmt = MyDB.getInsatnce().getConnection().createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                items.put(rs.getInt("id"),rs.getString("nom"));
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // ---------------------- set up combox ------------------
        Map<Integer,String> PLayerMap=setUpComboBox();
        ObservableList<String> observableList = FXCollections.observableArrayList(PLayerMap.values());
        players.setItems(observableList);


        Map<Integer,String> TeamsMap=setUpComboBoxTeams();
        ObservableList<String> observableList2 = FXCollections.observableArrayList(TeamsMap.values());
        teams.setItems(observableList2);

        add.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                //--------------------------------
                User user = new User();
                Equipe equipe = new Equipe();

                // Check if players.getValue() and teams.getValue() are not null
                if (players.getValue() != null) {
                    Integer selectedKey = getKeyByValue(PLayerMap, players.getValue());
                    user.setId(selectedKey.intValue());
                }

                if (teams.getValue() != null) {
                    Integer selectedKey2 = getKeyByValue(TeamsMap, teams.getValue());
                    equipe.setId(selectedKey2.intValue());
                }

                //------------------------- test inputs ------------
                boolean PlayerEmpty = players.getValue() == null;
                boolean TeamsEmpty = teams.getValue() == null;
                if (PlayerEmpty || TeamsEmpty) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Input is empty");
                    alert.setContentText("select fields can't be empty");
                    alert.show();
                    return;
                }

                try {
                    serviceEquipe.affecter(equipe, user);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayEquipe.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) add.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Gestion Equipe");
                    stage.show();
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
        });

        BackHome.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                try {

                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/HOME.fxml"));
                    Parent root = loader.load();
                    Stage stage = (Stage) add.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("HOME");
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        });













    }
}
