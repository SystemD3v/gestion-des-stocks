module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires java.desktop;
    requires javafx.graphics;
    requires java.net.http;
    requires jdk.jsobject;
    requires java.sql;                 // ← HttpClient
    opens com.example.javafx to javafx.fxml;

    exports com.example.javafx;
    opens com.example.javafx.Controller to javafx.fxml;
    exports com.example.javafx.view;
    opens com.example.javafx.view to javafx.fxml;
}