package ooproject.model;


//public record SimulationConfig(int mapWidth, int mapHeight, int initialNumOfPlants, int energyFromPlant,
//                               int numOfPlantsGrowingPerDay, int initialNumOfAnimals, int initialEnergy,
//                               int optimalEnergyLevel, int energyForBreeding, int minNumOfMutations,
//                               int maxNumOfMutations, int genomeLength, PlantGrowingMethod plantGrowingMethod,
//                               GeneticMutationVariant geneticMutationVariant) {
//
//}
public record SimulationConfig(int mapWidth, int mapHeight, int initialNumOfPlants, int energyFromPlant,
                               int numOfPlantsGrowingPerDay, int initialNumOfAnimals, int initialEnergy,
                               int optimalEnergyLevel, int energyForBreeding, int minNumOfMutations,
                               int maxNumOfMutations, int genomeLength) {

}