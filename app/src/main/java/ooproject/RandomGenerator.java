package ooproject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
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
}
