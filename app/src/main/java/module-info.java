module ooproject {
    requires javafx.controls;
    requires com.google.common;
    requires javafx.fxml;
    requires com.fasterxml.jackson.databind;


    opens ooproject to javafx.fxml, com.google.common;
    exports ooproject;
    exports ooproject.model;
    opens ooproject.model to com.google.common, javafx.fxml;
    exports ooproject.presenter;
    opens ooproject.presenter to com.google.common, javafx.fxml;
    exports ooproject.utils;
    opens ooproject.utils to com.google.common, javafx.fxml;
    exports ooproject.interfaces;
    opens ooproject.interfaces to com.google.common, javafx.fxml;
}