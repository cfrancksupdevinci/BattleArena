module org.example.battlearena {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires com.fasterxml.jackson.annotation;
    requires jakarta.jakartaee.api;
    requires spring.data.commons;

    opens org.example.battlearena to javafx.fxml;
    exports org.example.battlearena;
}