package ooproject.model;


public class ForestedEquator implements PlantGrowingVariant {
    @Override
    public boolean isPreferredField(WorldMap map, Vector2d field) {
        int height = map.getCurrentBounds().topRight().getY();
        if (height % 2 == 1) {
            return field.getY() == (height - 1) / 2;
        }
        return field.getY() == height / 2 || field.getY() == (height / 2) - 1;
    }
}
