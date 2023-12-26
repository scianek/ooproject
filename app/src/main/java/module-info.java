module ooproject {
    requires javafx.controls;
    requires com.google.common;
    requires javafx.fxml;


    opens ooproject to javafx.fxml, com.google.common;
    exports ooproject;
}