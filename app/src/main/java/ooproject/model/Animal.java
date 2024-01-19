package ooproject.model;

import ooproject.interfaces.WorldElement;

import java.util.ArrayList;
import java.util.List;

public class Animal implements WorldElement {
    private final List<Integer> genome;
    private final List<Animal> children = new ArrayList<>();
    private MapOrientation orientation = MapOrientation.N;
    private int energy;
    private int nextGeneIdx = 0;
    private Vector2d position;
    private int age = 0;
    private int numOfPlantsEaten = 0;

    public Animal(List<Integer> genome, int initialEnergy) {
        this.genome = genome;
        this.energy = initialEnergy;
    }

    public Vector2d getNextMove() {
        int nextGene = genome.get(nextGeneIdx);
        orientation = orientation.turn(nextGene);
        nextGeneIdx = (nextGeneIdx + 1) % genome.size();
        age++;
        return orientation.getMove();
    }

    public Vector2d getPosition() {
        return position;
    }

    public void setPosition(Vector2d position) {
        this.position = position;
    }

    @Override
//    public String toString() {
//        return Integer.toString(orientation.turn(genes.get(nextGeneIdx)).ordinal());
//    }
    public String toString() {
        return Integer.toString(energy);
    }

    public void turnAround() {
        orientation.turn(4);
    }

    public void addEnergy(int energy) {
        numOfPlantsEaten++;
        this.energy += energy;
    }

    public void subtractEnergy(int energy) {
        this.energy -= energy;
    }

    public int getEnergy() {
        return energy;
    }

    public int getAge() {
        return age;
    }

    public int getNumOfChildren() {
        return children.size();
    }

    public int getNumOfDescendants() {
        return children.size() + children.stream().map(Animal::getNumOfDescendants).reduce(0, Integer::sum);
    }

    public List<Integer> getGenome() {
        return genome;
    }

    public void addChild(Animal child) {
        children.add(child);
    }

    public int getNumOfPlantsEaten() {
        return numOfPlantsEaten;
    }

    public int getNextGene() {
        return nextGeneIdx;
    }
}
