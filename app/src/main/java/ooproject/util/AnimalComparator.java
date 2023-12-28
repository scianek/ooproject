package ooproject.util;

import ooproject.model.Animal;

import java.util.Comparator;
import java.util.Random;

public class AnimalComparator implements Comparator<Animal> {
    @Override
    public int compare(Animal animal1, Animal animal2) {
        if (animal1.getEnergy() != animal2.getEnergy()) {
            return animal2.getEnergy() - animal1.getEnergy();
        }

        if (animal1.getAge() != animal2.getAge()) {
            return animal2.getAge() - animal1.getAge();
        }

        if (animal1.getNumOfChildren() != animal2.getNumOfChildren()) {
            return animal2.getNumOfChildren() - animal1.getNumOfChildren();
        }

        return new Random().nextBoolean() ? 1 : -1;
    }
}
