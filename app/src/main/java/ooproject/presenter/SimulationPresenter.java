package ooproject.presenter;


import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.util.Duration;
import ooproject.interfaces.WorldElement;
import ooproject.model.*;
import ooproject.utils.StringFormatter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SimulationPresenter implements Initializable {
    private final static int CELL_SIZE = 16;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Text statsDisplay;

    @FXML
    private Text trackedAnimalDisplay;

    private Simulation simulation;

    private Animal trackedAnimal;
    private Timeline timeline;
    private boolean isPaused = false;
    @FXML
    private Button toggleButton;


    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
            simulation.runNextDay();
            SimulationStats stats = simulation.getStats();
            if (stats.numOfAnimals() == 0) {
                timeline.stop();
            }
            statsDisplay.setText(stats.toString());
            printTrackedAnimalInfo();
            drawMap();
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void drawMap() {
        clearGrid();
        Boundary bounds = simulation.getMap().getCurrentBounds();
        int width = bounds.topRight().getX() - bounds.bottomLeft().getX();
        int height = bounds.topRight().getY() - bounds.bottomLeft().getY();

        this.addCell(new Label("y\\x"), 0, 0);
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        for (int i = 0; i <= width; i++) {
            this.addCell(new Label(String.valueOf(bounds.bottomLeft().getX() + i)), i + 1, 0);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        }
        for (int i = 0; i <= height; i++) {
            this.addCell(new Label(String.valueOf(bounds.topRight().getY() - i)), 0, i + 1);
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_SIZE));
            for (int j = 0; j <= width; j++) {
                List<WorldElement> elements = simulation.getMap().objectsAt(new Vector2d(bounds.bottomLeft().getX() + j, bounds.topRight().getY() - i));
                Color color = Color.WHITE;
                if (elements != null && !elements.isEmpty()) {
                    WorldElement first = elements.get(0);
                    if (first instanceof Animal) {
                        color = Color.SADDLEBROWN;
                    } else if (first instanceof Plant) {
                        color = Color.GREEN;
                    }
                }
                var square = new Rectangle(CELL_SIZE, CELL_SIZE, color);
                square.setStroke(Color.GRAY);
                square.setStrokeWidth(1);
                this.addCell(square, j + 1, i + 1);
            }
        }
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_SIZE));
    }

    private void addCell(Node node, int x, int y) {
        Boundary bounds = simulation.getMap().getCurrentBounds();
//        var label = new Label(text);
//        var rectangle = new Rectangle(CELL_SIZE, CELL_SIZE, color);
        node.setOnMouseClicked(event -> {
            List<Animal> animals = simulation.getMap().getAnimals().get(new Vector2d(bounds.bottomLeft().getX() + x - 1, bounds.topRight().getY() - y + 1));
            if (animals != null && !animals.isEmpty()) {
                trackedAnimal = animals.get(0);
            }
            printTrackedAnimalInfo();
        });
        GridPane.setHalignment(node, HPos.CENTER);
        mapGrid.add(node, x, y);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    private void printTrackedAnimalInfo() {
        if (trackedAnimal == null) {
            return;
        }
        String[] data = {
                "Genome:  " + StringFormatter.listToStringWithParentheses(trackedAnimal.getGenome(), trackedAnimal.getNextGene()),
                "Energy: " + trackedAnimal.getEnergy(),
                "Plants eaten: " + trackedAnimal.getNumOfPlantsEaten(),
                "Number of children: " + trackedAnimal.getNumOfChildren(),
                "Number of descendants: " + trackedAnimal.getNumOfDescendants(),
                "How long alive: " + trackedAnimal.getAge() + " days",
        };
        trackedAnimalDisplay.setText(String.join("\n", data));
    }

    public void toggleSimulation() {
        if (isPaused) {
            timeline.play();
            toggleButton.setText("Pause");
            isPaused = false;
        } else {
            timeline.pause();
            toggleButton.setText("Play");
            isPaused = true;
        }
    }
}
