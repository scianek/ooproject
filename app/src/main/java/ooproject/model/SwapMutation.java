package ooproject.model;

import java.util.List;
import java.util.Random;

public class SwapMutation implements GeneticMutationVariant {
    @Override
    public List<Integer> mutateGenome(List<Integer> genome, int minNumOfMutations, int maxNumOfMutations) {
        List<Integer> mutatedGenome = new FullRandomMutation().mutateGenome(genome, minNumOfMutations, maxNumOfMutations);
        var random = new Random();
        for (int i = 0; i < mutatedGenome.size() - 1; i++) {
            if (random.nextDouble() < 0.1) {
                int temp = mutatedGenome.get(i);
                mutatedGenome.set(i, mutatedGenome.get(i + 1));
                mutatedGenome.set(i + 1, temp);

            }
        }
        return mutatedGenome;
    }
}
