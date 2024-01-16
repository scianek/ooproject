package ooproject.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;

public record SimulationConfig(int mapWidth, int mapHeight, int initialNumOfPlants, int energyFromPlant,
                               int numOfPlantsGrowingPerDay, int initialNumOfAnimals, int initialEnergy,
                               int optimalEnergyLevel, int energyLossOnBreeding, int minNumOfMutations,
                               int maxNumOfMutations, int genomeLength,
                               int dailyEnergyLoss, // this wasn't in the spec, but I guess it makes sense to have it
                               PlantGrowingVariant plantGrowingVariant, GeneticMutationVariant geneticMutationVariant) {


    public static SimulationConfig deserialize(String jsonString) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();

        objectMapper.registerSubtypes(
                new NamedType(ForestedEquator.class, "forestedEquator"),
                new NamedType(CrawlingJungle.class, "crawlingJungle"),
                new NamedType(FullRandomMutation.class, "fullRandomMutation"),
                new NamedType(SwapMutation.class, "swapMutation")
        );
        
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance, ObjectMapper.DefaultTyping.NON_FINAL);

        return objectMapper.readValue(jsonString, SimulationConfig.class);
    }

    public String serialize() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(this);
    }

}