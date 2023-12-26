package ooproject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorldMap {
    private final Map<Vector2d, List<Animal>> animals = new HashMap<>();
    private Vector2d bottomLeft = new Vector2d(0, 0);
    private Vector2d upperRight;

    public WorldMap(int width, int height) {
        upperRight = new Vector2d(width - 1, height - 1);
    }

    public Boundary getCurrentBounds() {
        return new Boundary(bottomLeft, upperRight);
    }

    public List<Animal> objectsAt(Vector2d position) {
        return this.animals.get(position);
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
}
