package ooproject.interfaces;  // czy to nie jest część modelu?

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import ooproject.model.FullRandomMutation;
import ooproject.model.SwapMutation;

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
