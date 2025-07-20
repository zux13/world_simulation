package dev.zux13.util;

import dev.zux13.board.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Utility methods for working with coordinates on the game board.
 */
public class CoordinateUtils {

    public static final int MOVE_COST_STRAIGHT = 10;
    public static final int MOVE_COST_DIAGONAL = 14;

    /**
     * Checks if the point {@code to} is within the {@code radius} of the point {@code from}.
     * The calculation is based on the squared Euclidean distance (without taking the root).
     *
     * @param from   the starting point
     * @param to     the target point
     * @param radius the visibility radius
     * @return {@code true} if {@code to} is within {@code radius}, otherwise {@code false}
     */
    public static boolean isWithinRadius(Coordinate from, Coordinate to, int radius) {
        int dx = from.x() - to.x();
        int dy = from.y() - to.y();
        return dx * dx + dy * dy <= radius * radius;
    }

    /**
     * Returns a list of coordinates of all neighbors (in 8 directions) that are within the boundaries.
     *
     * @param center the center around which neighbors are sought
     * @param width  the width of the map
     * @param height the height of the map
     * @return a list of coordinates of neighboring cells (maximum 8)
     */
    public static List<Coordinate> getNeighborsInBounds(Coordinate center, int width, int height) {
        List<Coordinate> neighbors = new ArrayList<>(8);

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                int nx = center.x() + dx;
                int ny = center.y() + dy;

                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    neighbors.add(Coordinate.of(nx, ny));
                }
            }
        }

        return neighbors;
    }

    /**
     * Generates a complete list of grid coordinates for a given width and height.
     * The list is filled from top to bottom, left to right.
     *
     * @param width  the width of the map
     * @param height the height of the map
     * @return a list of all coordinates on the board
     */
    public static List<Coordinate> generateGrid(int width, int height) {
        return IntStream.range(0, height)
                .boxed()
                .flatMap(y -> IntStream.range(0, width)
                        .mapToObj(x -> Coordinate.of(x, y)))
                .toList();
    }

    /**
     * Calculates the Manhattan distance between two coordinates.
     * Used in A* and other simple heuristics.
     *
     * @param a the first coordinate
     * @param b the second coordinate
     * @return the distance |x1 - x2| + |y1 - y2|
     */
    public static int manhattanDistance(Coordinate a, Coordinate b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    /**
     * Calculates the diagonal distance between two coordinates.
     * Used in A* for 8-directional movement.
     *
     * @param a the first coordinate
     * @param b the second coordinate
     * @return the diagonal distance
     */
    public static int diagonalDistance(Coordinate a, Coordinate b) {
        int dx = Math.abs(a.x() - b.x());
        int dy = Math.abs(a.y() - b.y());
        return MOVE_COST_STRAIGHT * (dx + dy) + (MOVE_COST_DIAGONAL - 2 * MOVE_COST_STRAIGHT) * Math.min(dx, dy);
    }

    /**
     * Returns the cost of movement between two adjacent cells.
     *
     * @param a the first coordinate
     * @param b the second coordinate
     * @return 10 for a straight move, 14 for a diagonal move
     */
    public static int getMovementCost(Coordinate a, Coordinate b) {
        int dx = Math.abs(a.x() - b.x());
        int dy = Math.abs(a.y() - b.y());
        return (dx == 1 && dy == 1) ? MOVE_COST_DIAGONAL : MOVE_COST_STRAIGHT;
    }

    public static boolean isWithinBounds(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
