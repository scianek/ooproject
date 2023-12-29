package ooproject.model;

import java.util.List;

public record SimulationStats(int numOfAnimals, int numOfPlants, int numOfEmptyFields,
                              List<List<Integer>> mostCommonGenomes, int averageEnergyLevel, int averageLifespan,
                              int averageNumOfChildren) {
    @Override
    public String toString() {
        String[] data = {
                "Current number of animals: " + numOfAnimals,
                "Current number of plants: " + numOfPlants,
                "Current number of empty fields: " + numOfEmptyFields,
                "Most common genome: " + mostCommonGenomes.get(0).toString(),
                "Average energy level: " + averageEnergyLevel,
                "Average lifespan: " + averageLifespan,
                "Average number of children: " + averageNumOfChildren
        };
        return String.join("\n", data);
    }
}
