package dev.zux13.decision;

import dev.zux13.board.Board;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;
import dev.zux13.util.CoordinateUtils;

import java.util.Optional;

public class RoamingHelper {

    private static final long PERIOD_MILLIS = 5000;

    public Optional<Coordinate> findPersistentRoamTarget(Board board, Coordinate current, Creature creature) {
        int width = board.getWidth();
        int height = board.getHeight();
        int creatureHash = creature.hashCode();
        long now = System.currentTimeMillis();

        long seed = now / PERIOD_MILLIS + creatureHash;

        double baseAngle = (seed % 360) * Math.PI / 180;
        double noise = ((creatureHash >> 3) % 60 - 30) * Math.PI / 180;
        double angle = baseAngle + noise;

        if (isNearEdge(current, width, height, creature.getVision())) {
            angle = steerAwayFromEdge(current, width, height);
        }

        for (int i = 0; i < 8; i++) {
            int dx = (int) Math.round(Math.cos(angle));
            int dy = (int) Math.round(Math.sin(angle));

            int newX = current.x() + dx;
            int newY = current.y() + dy;

            if (CoordinateUtils.isWithinBounds(newX, newY, width, height)) {
                Coordinate target = new Coordinate(newX, newY);
                if (board.isTileEmpty(target)) {
                    return Optional.of(target);
                }
            }

            angle += Math.PI / 4;
        }

        return Optional.empty();
    }

    private boolean isNearEdge(Coordinate current, int width, int height, int vision) {
        int x = current.x();
        int y = current.y();
        return x - vision < 0 || x + vision >= width || y - vision < 0 || y + vision >= height;
    }

    protected double steerAwayFromEdge(Coordinate current, int width, int height) {
        int x = current.x();
        int y = current.y();

        boolean nearLeft = x <= 1;
        boolean nearRight = x >= width - 2;
        boolean nearTop = y <= 1;
        boolean nearBottom = y >= height - 2;

        if (nearLeft && nearTop) return Math.toRadians(45);        // ↘
        if (nearLeft && nearBottom) return Math.toRadians(315);    // ↗
        if (nearRight && nearTop) return Math.toRadians(135);      // ↙
        if (nearRight && nearBottom) return Math.toRadians(225);   // ↖
        if (nearLeft) return Math.toRadians(0);                    // →
        if (nearRight) return Math.toRadians(180);                 // ←
        if (nearTop) return Math.toRadians(90);                    // ↓
        if (nearBottom) return Math.toRadians(270);                // ↑

        return Math.random() * 2 * Math.PI;
    }
}
