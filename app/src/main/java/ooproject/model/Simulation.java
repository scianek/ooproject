package ooproject.model;

import ooproject.util.RandomGenerator;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final WorldMap map;
    private final List<Animal> animals = new ArrayList<>();
    private final SimulationConfig config;
    private int currDay = 0;


    public Simulation(SimulationConfig config) {
        this.config = config;
        this.map = new WorldMap(config.mapWidth(), config.mapHeight());

        addPlants(config.initialNumOfPlants());

        for (Vector2d position : RandomGenerator.generatePositions(config.mapWidth(), config.mapWidth(), config.initialNumOfAnimals())) {
            var animal = new Animal(RandomGenerator.generateGenes(config.genomeLength()));
            animal.setPosition(position);
            animals.add(animal);
            map.placeAnimal(animal);
        }
    }


    private void addPlants(int numOfPlants) {
        List<Vector2d> newPlantPositions = RandomGenerator.generatePlantPositions(map, numOfPlants, new CrawlingJungle());
        newPlantPositions.forEach(position -> map.placePlant(new Plant(position)));
    }

    public void runNextDay() {
        for (Animal animal : animals) {
            Vector2d nextMove = animal.getNextMove();
            map.moveAnimal(animal, nextMove);

            Vector2d newPosition = animal.getPosition();
            if (map.getPlants().get(newPosition) != null) {
                animal.addEnergy(config.energyFromPlant());
                map.removePlant(newPosition);
            }
        }
        currDay++;

        List<Vector2d> newPlantPositions = RandomGenerator.generatePlantPositions(map, 3);
        newPlantPositions.forEach(position -> map.placePlant(new Plant(position)));

        addPlants(config.numOfPlantsGrowingPerDay());
    }

    public WorldMap getMap() {
        return map;
    }
}
