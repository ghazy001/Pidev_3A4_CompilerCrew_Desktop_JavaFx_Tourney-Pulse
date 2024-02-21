module com.example.gestionstade {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.gestionstade to javafx.fxml;
    exports com.example.gestionstade;

    opens entities;
    opens controllers to javafx.fxml;
    exports controllers;
}