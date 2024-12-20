package ooproject.model;

import ooproject.interfaces.WorldElement;

public class Plant implements WorldElement {
    private Vector2d position;

    public Plant(Vector2d position) {
        this.position = position;
    }

    public Vector2d getPosition() {
        return position;
    }

    @Override
    public String toString() {
        return "*";
    }
}