package ooproject.presenter;


import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.text.Text;
import ooproject.model.*;
import ooproject.util.StringFormatter;

import java.util.List;
import java.util.stream.Collectors;

public class SimulationPresenter {
    private final static int CELL_SIZE = 50;

    @FXML
    private GridPane mapGrid;

    @FXML
    private Text statsDisplay;

    @FXML
    private Text trackedAnimalDisplay;

    private Simulation simulation;

    private Animal trackedAnimal;


    public void setSimulation(Simulation simulation) {
        this.simulation = simulation;
    }

    private void drawMap() {
        clearGrid();
        Boundary bounds = simulation.getMap().getCurrentBounds();
        int width = bounds.topRight().getX() - bounds.bottomLeft().getX();
        int height = bounds.topRight().getY() - bounds.bottomLeft().getY();

        this.addCell("y\\x", 0, 0);
        mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        for (int i = 0; i <= width; i++) {
            this.addCell(String.valueOf(bounds.bottomLeft().getX() + i), i + 1, 0);
            mapGrid.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        }
        for (int i = 0; i <= height; i++) {
            this.addCell(String.valueOf(bounds.topRight().getY() - i), 0, i + 1);
            mapGrid.getRowConstraints().add(new RowConstraints(CELL_SIZE));
            for (int j = 0; j <= width; j++) {
                List<WorldElement> elements = simulation.getMap().objectsAt(new Vector2d(bounds.bottomLeft().getX() + j, bounds.topRight().getY() - i));

                String text = elements == null ? " " : elements.stream()
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
                this.addCell(text, j + 1, i + 1);
            }
        }
        mapGrid.getRowConstraints().add(new RowConstraints(CELL_SIZE));
    }

    private void addCell(String text, int x, int y) {
        Boundary bounds = simulation.getMap().getCurrentBounds();
        var label = new Label(text);
        label.setOnMouseClicked(event -> {
            List<Animal> animals = simulation.getMap().getAnimals().get(new Vector2d(bounds.bottomLeft().getX() + x - 1, bounds.topRight().getY() - y + 1));
            if (animals != null && !animals.isEmpty()) {
                trackedAnimal = animals.get(0);
            }
            printTrackedAnimalInfo();
        });
        GridPane.setHalignment(label, HPos.CENTER);
        mapGrid.add(label, x, y);
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

    public void handleNext() {
        simulation.runNextDay();
        SimulationStats stats = simulation.getStats();
        statsDisplay.setText(stats.toString());
        printTrackedAnimalInfo();
        drawMap();
    }
}
