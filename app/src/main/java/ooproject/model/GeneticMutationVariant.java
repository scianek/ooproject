package ooproject.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = FullRandomMutation.class, name = "fullRandomMutation"),
        @JsonSubTypes.Type(value = SwapMutation.class, name = "swapMutation")
})
public interface GeneticMutationVariant {
    List<Integer> mutateGenome(List<Integer> genome, int minNumOfMutations, int maxNumOfMutations);
}
