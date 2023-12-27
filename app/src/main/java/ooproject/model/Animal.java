package ooproject.model;

import java.util.List;

public class Animal implements WorldElement {
    private final List<Integer> genes;
    private MapOrientation orientation = MapOrientation.N;
    private int energy;
    private int nextGeneIdx = 0;
    private Vector2d position;

    public Animal(List<Integer> genes) {
        this.genes = genes;
    }

    public Vector2d getNextMove() {
        int nextGene = genes.get(nextGeneIdx);
        orientation = orientation.turn(nextGene);
        nextGeneIdx = (nextGeneIdx + 1) % genes.size();
        return orientation.getMove();
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return Integer.toString(orientation.turn(genes.get(nextGeneIdx)).ordinal());
    }

    public void turnAround() {
        orientation.turn(4);
    }

    public void addEnergy(int energy) {
        this.energy += energy;
    }
}
