package ooproject.model;

import ooproject.util.AnimalComparator;
import ooproject.util.RandomGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
            var animal = new Animal(RandomGenerator.generateGenes(config.genomeLength()), config.initialEnergy());
            animal.setPosition(position);
            animals.add(animal);
            map.placeAnimal(animal);
        }
    }

    private void addPlants(int numOfPlants) {
        List<Vector2d> newPlantPositions = RandomGenerator.generatePlantPositions(map, numOfPlants, config.plantGrowingVariant());
        newPlantPositions.forEach(position -> map.placePlant(new Plant(position)));
    }

    private void consumePlants() {
        Map<Vector2d, Plant> plants = map.getPlants();
        List<Vector2d> positionsToRemove = new ArrayList<>();
        for (Vector2d position : plants.keySet()) {
            List<WorldElement> elements = map.objectsAt(position);
            List<Animal> animals = new ArrayList<>(elements.stream()
                    .filter(element -> element instanceof Animal)
                    .map(element -> (Animal) element)
                    .toList());
            if (animals.isEmpty()) {
                continue;
            }
            animals.sort(new AnimalComparator());
            Animal dominantAnimal = animals.get(0);
            dominantAnimal.addEnergy(config.energyFromPlant());
            positionsToRemove.add(position);
        }
        positionsToRemove.forEach(map::removePlant);
    }

    public void runNextDay() {
        currDay++;
        for (Animal animal : animals) {
            Vector2d nextMove = animal.getNextMove();
            animal.subtractEnergy(config.dailyEnergyLoss());
            map.moveAnimal(animal, nextMove);
        }
        consumePlants();
        addPlants(config.numOfPlantsGrowingPerDay());
    }

    public WorldMap getMap() {
        return map;
    }
}
