package ooproject.model;

public record SimulationConfig(int mapWidth, int mapHeight, int initialNumOfPlants, int energyFromPlant,
                               int numOfPlantsGrowingPerDay, int initialNumOfAnimals, int initialEnergy,
                               int optimalEnergyLevel, int energyLossOnBreeding, int minNumOfMutations,
                               int maxNumOfMutations, int genomeLength,
                               int dailyEnergyLoss, // this wasn't in the spec, but I guess it makes sense to have it
                               PlantGrowingVariant plantGrowingVariant, GeneticMutationVariant geneticMutationVariant) {

}