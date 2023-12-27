package ooproject;


import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;
import java.util.stream.Collectors;

public class SimulationPresenter {
    private final static int CELL_SIZE = 30;

    @FXML
    private GridPane mapGrid;

    @FXML
    private TextField movesTextField;

    private Simulation simulation;

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
        var label = new Label(text);
        GridPane.setHalignment(label, HPos.CENTER);
        mapGrid.add(label, x, y);
    }

    private void clearGrid() {
        mapGrid.getChildren().retainAll(mapGrid.getChildren().get(0)); // hack to retain visible grid lines
        mapGrid.getColumnConstraints().clear();
        mapGrid.getRowConstraints().clear();
    }

    public void handleNext() {
        simulation.runNextDay();
        this.drawMap();
    }
}
