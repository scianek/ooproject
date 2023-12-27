package ooproject.model;

public record SimulationConfig(int mapWidth, int mapHeight, int initialNumOfPlants, int energyFromPlant,
                               int numOfPlantsGrowingPerDay, int initialNumOfAnimals, int initialEnergy,
                               int optimalEnergyLevel, int energyForBreeding, int minNumOfMutations,
                               int maxNumOfMutations, int genomeLength, PlantGrowingVariant plantGrowingVariant,
                               GeneticMutationVariant geneticMutationVariant) {

}