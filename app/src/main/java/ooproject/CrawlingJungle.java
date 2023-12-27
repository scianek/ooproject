package ooproject;

import java.util.Arrays;

public class CrawlingJungle implements PlantGrowingVariant {
    @Override
    public boolean isPreferredField(WorldMap map, Vector2d field) {
        return Arrays.stream(MapOrientation.values()).anyMatch((MapOrientation orientation) ->
                map.getPlants().get(field.add(orientation.getMove())) != null);
    }
}
