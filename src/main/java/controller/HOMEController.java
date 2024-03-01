package controller;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;
import javafx.util.Duration;
import okhttp3.OkHttpClient;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import java.io.IOException;

/*
*
   Author: @Ghazi saoudi
*
*/


public class HOMEController implements Initializable {

    @FXML
    public MenuBar menuBar;
    @FXML
    public Menu addAvisMenu;
    @FXML
    public MenuItem AddAvisMenuItem;
    @FXML
    public MenuItem AddAvisMenuItem1;
    @FXML
    public MenuItem staticavismenuitem;
    @FXML
    public MenuItem add_equipe_menuitem,add_equipe_menuitem1,Teampalyer_menuitem,menuitemCardTeam,Newsmenuitem;

    @FXML
    public Label time;
    @FXML
    public Label date;
    private boolean stop = false;
    @FXML
    public ToggleButton darkToggleButton;

    @FXML
    Button facebook;
    @FXML
    Button chatgpt;








    //--------------------------------------timeCurrent--------------------------------------
    private  void updateTime() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        if (!stop) {
                            SimpleDateFormat sdf = new SimpleDateFormat("hh:mm:ss");
                            String timeNow = sdf.format(new Date());
                            time.setText(timeNow);
                        }
                    }
                })
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    //----------------------------dateCuurent------------------------------------
    private void displayDate() {
        SimpleDateFormat sdf = new SimpleDateFormat("'Aujourd''hui' EEEE, MMMM dd, yyyy");
        String dateNow = sdf.format(new Date());
        System.out.println(dateNow);
        date.setText(dateNow);
    }

    //---------------------------------------------------------------------------









    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {




        //-----------Calling-Time-------------
        updateTime();
        displayDate();



        //----------------- addAvis-switching-menuitem----------------
        AddAvisMenuItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Players.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) menuBar.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Gestion Avis");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //--------------------showAvis-switching---------------
        AddAvisMenuItem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ShowAvisPLayer.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) menuBar.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Gestion Avis");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        //--------------------static-switching-----------------------

        staticavismenuitem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/StaticAvis.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) menuBar.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Gestion Avis");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        //--------------------switching-add-equipe ----------------------//
        add_equipe_menuitem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AjouterEquipe.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) menuBar.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Gestion Equipe");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });

        //--------------------------switching to display equipe ------------

        add_equipe_menuitem1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayEquipe.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) menuBar.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Gestion Equipe");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        //--------------------------switching to join equipe ------------

        Teampalyer_menuitem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AffecterPlayers.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) menuBar.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Gestion Equipe");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });
        //--------------------------switching to Card equipe ------------

        menuitemCardTeam.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {


                FXMLLoader loader = new FXMLLoader(getClass().getResource("/DisplayTeamCard.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) menuBar.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Gestion Equipe");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }
        });




        //--------------DARK MODE----------------//

        darkToggleButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Scene scene = menuBar.getScene();

                try {
                    File styleDark = new File("/Users/ghazi.saoudi/Desktop/equipe/src/main/resources/Css/style.css");
                    File styleLight = new File("/Users/ghazi.saoudi/Desktop/equipe/src/main/resources/Css/LightMode.css");



                    String cssUrl;

                    if (darkToggleButton.isSelected()) {
                        cssUrl = styleDark.toURI().toURL().toExternalForm();
                    } else {

                        cssUrl ="";
                    }

                    scene.getStylesheets().clear();
                    scene.getStylesheets().add(cssUrl);
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            }
        });
        //-----------join Facebook-----------------

        facebook.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.facebook.com/ghazi.saoudi.3/"));
                } catch (IOException | URISyntaxException ex) {

                    ex.printStackTrace();  // You can log the exception if needed
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Connection error");
                    alert.setHeaderText(null);
                    alert.setContentText("check your connection");
                    alert.showAndWait();

                }
            }
        });
        //---------switch to chatgpt----------
        chatgpt.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/chatgpt.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) date.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Tourney AI");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        //---------switch to News----------
        Newsmenuitem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/News.fxml"));
                try {
                    Parent root = loader.load();
                    Stage stage = (Stage) date.getScene().getWindow();
                    stage.setScene(new Scene(root));
                    stage.setTitle("Tourney AI");
                    stage.show();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });









    }









}
