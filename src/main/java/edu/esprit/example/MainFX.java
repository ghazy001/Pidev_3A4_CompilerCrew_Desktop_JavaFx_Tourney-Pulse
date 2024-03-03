package edu.esprit.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class MainFX extends Application {
    public MainFX() {
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/DisplayReclamation.fxml"));
        Parent root = (Parent)loader.load();
        Scene scene = new Scene(root);
        //stage.setScene(scene);
        stage.setScene(scene);
      stage.setHeight(656);
      stage.setWidth(969);
        stage.setTitle("Gestion mesagerie");
        stage.show();
    }
}

