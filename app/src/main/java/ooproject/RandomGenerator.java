package ooproject;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class RandomGenerator {
    public static List<Vector2d> generatePositions(int maxWidth, int maxHeight, int numOfPositions) {
        List<Vector2d> positions = new ArrayList<>(maxWidth * maxHeight);

        for (int x = 0; x < maxWidth; x++) {
            for (int y = 0; y < maxHeight; y++) {
                positions.add(new Vector2d(x, y));
            }
        }

        Collections.shuffle(positions);
        return positions.subList(0, numOfPositions);
    }

    public static List<Integer> generateGenes(int numOfGenes) {
        var random = new Random();
        return IntStream.range(0, numOfGenes)
                .map(i -> random.nextInt(8))
                .boxed()
                .collect(Collectors.toList());
    }

    public static List<Vector2d> generatePlantPositions(WorldMap map, int numOfPositions, PlantGrowingVariant plantGrowingMethod) {
        var bounds = map.getCurrentBounds();
        int width = bounds.topRight().getX();
        int height = bounds.topRight().getY();

        List<Vector2d> positions = new ArrayList<>();
        Map<Vector2d, Plant> existingPlants = map.getPlants();
        for (int i = 0; i <= width; i++) {
            for (int j = 0; j <= height; j++) {
                Vector2d currPosition = new Vector2d(i, j);
                if (existingPlants.get(currPosition) != null) {
                    continue;
                }

                if (plantGrowingMethod.isPreferredField(map, currPosition)) {
                    // if it's a preferred field, make it 4 times more likely to come up
                    positions.addAll(Collections.nCopies(4, currPosition));
                } else {
                    positions.add(currPosition);
                }
            }
        }
        Collections.shuffle(positions);
        return positions.size() > numOfPositions ? positions.subList(0, numOfPositions) : positions;
    }
}
