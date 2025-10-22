module com.example.javafx {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires eu.hansolo.tilesfx;
    requires java.desktop;

    opens com.example.javafx to javafx.fxml;
    exports com.example.javafx;
    exports Controller;
    opens Controller to javafx.fxml;
}