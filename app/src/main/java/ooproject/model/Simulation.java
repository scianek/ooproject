package ooproject.model;

import ooproject.util.AnimalComparator;
import ooproject.util.RandomGenerator;

import java.util.*;

public class Simulation {
    private final WorldMap map;
    private final List<Animal> animals = new ArrayList<>();
    private final SimulationConfig config;
    private int currDay = 0;
    private int averageLifespan = 0;
    private int numOfDeadAnimals = 0;


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

    private void breedAnimals() {
        Map<Vector2d, List<Animal>> mapAnimals = map.getAnimals();
        for (Vector2d position : mapAnimals.keySet()) {
            List<Animal> localAnimals = mapAnimals.get(position);
            if (localAnimals.size() < 2) {
                continue;
            }
            localAnimals.sort(new AnimalComparator());
            var parent1 = localAnimals.get(0);
            var parent2 = localAnimals.get(1);
            if (parent1.getEnergy() < config.optimalEnergyLevel() || parent2.getEnergy() < config.optimalEnergyLevel()) {
                continue;
            }
            parent1.subtractEnergy(config.energyLossOnBreeding());
            parent2.subtractEnergy(config.energyLossOnBreeding());
            List<Integer> newBornGenome = RandomGenerator.generateGenome(parent1, parent2, config.geneticMutationVariant(), config.minNumOfMutations(), config.maxNumOfMutations());
            var newbornAnimal = new Animal(newBornGenome, config.energyLossOnBreeding() * 2);
            parent1.addChild(newbornAnimal);
            parent2.addChild(newbornAnimal);
            newbornAnimal.setPosition(position);
            animals.add(newbornAnimal);
            map.placeAnimal(newbornAnimal);
        }
    }

    public void runNextDay() {
        currDay++;
        List<Animal> deadAnimals = new ArrayList<>();
        for (Animal animal : animals) {
            if (animal.getEnergy() <= 0) {
                deadAnimals.add(animal);
                continue;
            }
            Vector2d nextMove = animal.getNextMove();
            animal.subtractEnergy(config.dailyEnergyLoss());
            map.moveAnimal(animal, nextMove);
        }
        for (var animal : deadAnimals) {
            numOfDeadAnimals++;
            averageLifespan = (averageLifespan + animal.getAge()) / numOfDeadAnimals;
            animals.remove(animal);
            map.removeAnimal(animal);
        }
        consumePlants();
        breedAnimals();
        addPlants(config.numOfPlantsGrowingPerDay());
    }

    public WorldMap getMap() {
        return map;
    }

    public SimulationStats getStats() {
        int numOfEmptyFields = 0;
        for (int i = 0; i < config.mapWidth(); i++) {
            for (int j = 0; j < config.mapHeight(); j++) {
                if (map.objectsAt(new Vector2d(i, j)).isEmpty()) {
                    numOfEmptyFields++;
                }
            }
        }
        List<List<Integer>> genomes = new ArrayList<>(animals.stream().map(Animal::getGenome).toList());
        Map<List<Integer>, Integer> frequencyMap = new HashMap<>();
        for (List<Integer> genome : genomes) {
            frequencyMap.put(genome, frequencyMap.getOrDefault(genome, 0) + 1);
        }
        genomes.sort(Comparator.comparingInt(frequencyMap::get));
        return new SimulationStats(
                animals.size(),
                map.getPlants().size(),
                numOfEmptyFields,
                genomes,
                animals.stream().map(Animal::getEnergy).reduce(0, Integer::sum) / animals.size(),
                averageLifespan,
                animals.stream().map(Animal::getNumOfChildren).reduce(0, Integer::sum) / animals.size()
        );
    }
}
