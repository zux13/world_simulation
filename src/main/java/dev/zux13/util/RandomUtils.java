package dev.zux13.util;

import java.util.Random;

public final class RandomUtils {

    private static final Random RANDOM = new Random();

    private RandomUtils() {
    }

    public static int randomInRange(int min, int max) {
        return min + RANDOM.nextInt(max - min + 1);
    }

    public static int nextInt(int bound) {
        return RANDOM.nextInt(bound);
    }

}
