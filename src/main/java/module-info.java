module com.esprit {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;
    opens tn.Esprit.controllers;
    opens tn.Esprit.models;
    opens tn.Esprit to javafx.fxml;
    exports tn.Esprit;
}