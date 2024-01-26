package ooproject.presenter;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import ooproject.AppGUI;
import ooproject.model.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class SimulationConfigPresenter implements Initializable {
    private AppGUI mainApp;
    @FXML
    private VBox mainBox;
    @FXML
    private ComboBox<String> plantGrowingVariantComboBox;
    @FXML
    private ComboBox<String> geneMutationVariantComboBox;
    @FXML
    private TextField saveConfigName;
    @FXML
    private VBox savedConfigsBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        List<Path> configPaths;
        try {
            configPaths = Files.list(Path.of("."))
                    .filter(Files::isRegularFile)
                    .filter(path -> path.toString().endsWith(".json")).toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        for (var path : configPaths) {
            var button = new Button();
            button.setText(path.toString().substring(2, path.toString().length() - 5));
            button.setOnMouseClicked(event -> {
                try {
                    loadConfig(SimulationConfig.deserialize(Files.readString(path)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });
            savedConfigsBox.getChildren().add(button);
        }
    }

    public void setMainApp(AppGUI mainApp) {
        this.mainApp = mainApp;
    }

    public SimulationConfig getConfig() throws IOException {
        List<TextField> textFields = findAllTextFields(mainBox);
        List<String> invalidFields = getInvalidFields(textFields);
        if (!invalidFields.isEmpty()) {
            showAlert("The following input fields have invalid, non-numeric values:\n" + String.join("\n", invalidFields));
            return null;
        }

        // mfw no spread operator
        return new SimulationConfig(
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
                Integer.parseInt(textFields.get(11).getText()),
                Integer.parseInt(textFields.get(12).getText()),
                plantGrowingVariantComboBox.getValue().equals("Zalesione równiki") ? new ForestedEquator() : new CrawlingJungle(),
                geneMutationVariantComboBox.getValue().equals("Pełna losowość") ? new FullRandomMutation() : new SwapMutation()
        );
    }

    private void loadConfig(SimulationConfig config) {
        List<TextField> textFields = findAllTextFields(mainBox);
        textFields.get(0).setText(Integer.toString(config.mapWidth()));  // czy mamy pewność, że kolejność w textFields jest odpowiednia?
        textFields.get(1).setText(Integer.toString(config.mapWidth()));
        textFields.get(2).setText(Integer.toString(config.initialNumOfPlants()));
        textFields.get(3).setText(Integer.toString(config.energyFromPlant()));
        textFields.get(4).setText(Integer.toString(config.numOfPlantsGrowingPerDay()));
        textFields.get(5).setText(Integer.toString(config.initialNumOfAnimals()));
        textFields.get(6).setText(Integer.toString(config.initialEnergy()));
        textFields.get(7).setText(Integer.toString(config.optimalEnergyLevel()));
        textFields.get(8).setText(Integer.toString(config.energyLossOnBreeding()));
        textFields.get(9).setText(Integer.toString(config.minNumOfMutations()));
        textFields.get(10).setText(Integer.toString(config.maxNumOfMutations()));
        textFields.get(11).setText(Integer.toString(config.genomeLength()));
        textFields.get(12).setText(Integer.toString(config.dailyEnergyLoss()));
    }

    public void handleStart() throws IOException {
        SimulationConfig config = this.getConfig();
        if (config != null) {
            mainApp.showSimulation(config);
        }
    }

    public void handleSave() throws IOException {
        SimulationConfig config = this.getConfig();
        if (config != null) {
            String configName = saveConfigName.getText().trim();
            if (configName.isEmpty()) {
                showAlert("You need to name the config");
                return;
            }
            var file = new File(configName + ".json");
            var writer = new FileWriter(configName + ".json");
            boolean result = file.createNewFile();
            writer.write(config.serialize());
            writer.close();
        }
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
