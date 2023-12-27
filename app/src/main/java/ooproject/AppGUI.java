package ooproject;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class AppGUI extends Application {
    private Stage stage;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("config.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationConfigPresenter configPresenter = loader.getController();
        configPresenter.setMainApp(this);
        this.configureStage(viewRoot);
        stage.show();
    }

    private void configureStage(BorderPane viewRoot) {
        var scene = new Scene(viewRoot);
        stage.setScene(scene);
        stage.setTitle("Simulation app");
        stage.minWidthProperty().bind(viewRoot.minWidthProperty());
        stage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }

    public void showSimulation(SimulationConfig config) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        SimulationPresenter configPresenter = loader.getController();
        configPresenter.setSimulation(new Simulation(config));
        this.configureStage(viewRoot);
        stage.show();
    }
}
