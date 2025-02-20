module org.example.milestone3mlgui {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens org.example.milestone3mlgui to javafx.fxml;
    exports org.example.milestone3mlgui;
}