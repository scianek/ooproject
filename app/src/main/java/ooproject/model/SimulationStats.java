package ooproject.model;

import java.util.List;

public record SimulationStats(int numOfAnimals, int numOfPlants, int numOfEmptyFields,
                              List<List<Integer>> mostCommonGenomes, int averageEnergyLevel, double averageLifespan,
                              int averageNumOfChildren) {
    @Override
    public String toString() {
        String[] data = {
                "Current number of animals: " + numOfAnimals,
                "Current number of plants: " + numOfPlants,
                "Current number of empty fields: " + numOfEmptyFields,
                "Most common genome: " + (mostCommonGenomes.isEmpty() ? "-" : mostCommonGenomes.get(0).toString()),
                "Average energy level: " + averageEnergyLevel,
                String.format("Average lifespan: %.2f", averageLifespan),
                "Average number of children: " + averageNumOfChildren
        };
        return String.join("\n", data);
    }
}
