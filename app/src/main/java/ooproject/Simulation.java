package ooproject;

import java.util.ArrayList;
import java.util.List;

public class Simulation {
    private final WorldMap map;
    private final List<Animal> animals = new ArrayList<>();
    private int currDay = 0;

    public Simulation(WorldMap map) {
        this.map = map;

        for (Vector2d position : RandomGenerator.generatePositions(10, 10, 5)) {
            var animal = new Animal(RandomGenerator.generateGenes(5));
            animal.setPosition(position);
            animals.add(animal);
            map.placeAnimal(animal);
        }
    }

    public void runNextDay() {
        for (Animal animal : animals) {
            Vector2d nextMove = animal.getNextMove();
            map.moveAnimal(animal, nextMove);
        }
        currDay++;
    }

    public WorldMap getMap() {
        return map;
    }
}
