package ooproject.model;

import java.util.*;

public class WorldMap {
    private final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private final Map<Vector2d, Plant> plants = new HashMap<>();
    private final Vector2d bottomLeft = new Vector2d(0, 0);
    private final Vector2d upperRight;

    public WorldMap(int width, int height) {
        upperRight = new Vector2d(width - 1, height - 1);
    }

    public Boundary getCurrentBounds() {
        return new Boundary(bottomLeft, upperRight);
    }

    public List<WorldElement> objectsAt(Vector2d position) {
        List<WorldElement> elements = new ArrayList<>(Optional.ofNullable(this.animals.get(position))
                .orElse(List.of()));
        if (plants.get(position) != null) {
            elements.add(plants.get(position));
        }
        return elements;

    }

    public void placeAnimal(Animal animal) {
        Vector2d position = animal.getPosition();
        animals.computeIfAbsent(position, k -> new ArrayList<>());
        animals.get(position).add(animal);
    }

    public void moveAnimal(Animal animal, Vector2d move) {
        Vector2d oldPosition = animal.getPosition();
        animals.get(oldPosition).remove(animal);
        int newX = animal.getPosition().getX() + move.getX();
        int newY = animal.getPosition().getY() + move.getY();

        // Y axis doesn't wrap, get rotated 180deg
        if (newY < bottomLeft.getY() || newY > upperRight.getX()) {
            animal.turnAround();
            placeAnimal(animal);
            return;
        }

        // wrap on X axis if necessary
        if (newX < bottomLeft.getX()) {
            newX = upperRight.getX() - (bottomLeft.getX() - newX) + 1;
        } else if (newX > upperRight.getX()) {
            newX = bottomLeft.getX() + (newX - upperRight.getX()) - 1;
        }
        animal.setPosition(new Vector2d(newX, newY));
        placeAnimal(animal);
    }

    public void placePlant(Plant plant) {
        plants.put(plant.getPosition(), plant);
    }

    public Map<Vector2d, Plant> getPlants() {
        return Collections.unmodifiableMap(plants);
    }

    public void removePlant(Vector2d position) {
        plants.remove(position);
    }

    public void removeAnimal(Animal animal) {
        animals.get(animal.getPosition()).remove(animal);
    }

    public Map<Vector2d, List<Animal>> getAnimals() {
        return animals;
    }
}
