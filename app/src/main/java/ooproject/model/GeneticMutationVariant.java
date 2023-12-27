package ooproject.model;

import java.util.List;

public interface GeneticMutationVariant {
    List<Integer> mutateGenome(List<Integer> genome, int minNumOfMutations, int maxNumOfMutations);
}
