package ooproject;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimulationConfigPresenter {
    private AppGUI mainApp;
    @FXML
    private VBox mainBox;
    @FXML
    private ComboBox<String> plantGrowingMethodComboBox;

    public void setMainApp(AppGUI mainApp) {
        this.mainApp = mainApp;
    }

    public void handleStart() throws IOException {
        List<TextField> textFields = findAllTextFields(mainBox);
        List<String> invalidFields = getInvalidFields(textFields);
        if (!invalidFields.isEmpty()) {
            showAlert("The following input fields have invalid, non-numeric values:\n" + String.join("\n", invalidFields));
            return;
        }


        // mfw no spread operator
        var config = new SimulationConfig(
                Integer.parseInt(textFields.get(0).getText()),
                Integer.parseInt(textFields.get(1).getText()),
                Integer.parseInt(textFields.get(2).getText()),
                Integer.parseInt(textFields.get(3).getText()),
                Integer.parseInt(textFields.get(4).getText()),
                Integer.parseInt(textFields.get(5).getText()),
                Integer.parseInt(textFields.get(6).getText()),
                Integer.parseInt(textFields.get(7).getText()),
                Integer.parseInt(textFields.get(8).getText()),
                Integer.parseInt(textFields.get(9).getText()),
                Integer.parseInt(textFields.get(10).getText()),
                Integer.parseInt(textFields.get(11).getText())
        );
        mainApp.showSimulation(config);
    }

    private List<TextField> findAllTextFields(VBox vbox) {
        List<TextField> textFields = new ArrayList<>();

        for (Node childNode : vbox.getChildren()) {
            if (((VBox) childNode).getChildren().toArray()[1] instanceof TextField textField) {
                textFields.add(textField);
            }
        }

        return textFields;
    }

    private List<String> getInvalidFields(List<TextField> textFields) {
        List<String> invalidFields = new ArrayList<>();
        for (var textField : textFields) {
            try {
                Integer.parseInt(textField.getText());
            } catch (NumberFormatException e) {
                String fieldName = ((Label) ((VBox) (textField.getParent())).getChildren().toArray()[0]).getText();
                invalidFields.add(fieldName);
            }
        }
        return invalidFields;
    }

    private void showAlert(String message) {
        var alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Invalid input");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
