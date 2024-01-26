package ooproject.model;

import ooproject.interfaces.GeneticMutationVariant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class FullRandomMutation implements GeneticMutationVariant {
    @Override
    public List<Integer> mutateGenome(List<Integer> genome, int minNumOfMutations, int maxNumOfMutations) {
        var random = new Random(); // co wywołanie?
        int numOfMutations = random.nextInt(maxNumOfMutations - minNumOfMutations + 1) + minNumOfMutations;

        List<Integer> indices = new ArrayList<>(IntStream.range(0, genome.size()).boxed().toList());
        Collections.shuffle(indices);

        List<Integer> mutatedGenome = new ArrayList<>(genome);

        indices.stream()
                .limit(numOfMutations)
                .forEach(indexToMutate -> {
                    int newValue = random.nextInt(8);
                    mutatedGenome.set(indexToMutate, newValue);
                });

        return mutatedGenome;
    }
}
