package dev.zux13.finder;

import dev.zux13.board.Coordinate;
import dev.zux13.util.CoordinateUtils;

import java.util.Comparator;
import java.util.Optional;
import java.util.Set;

public class ManhattanTargetLocator implements TargetLocator {

    @Override
    public Optional<Coordinate> findClosest(Coordinate from, Set<Coordinate> targets) {
        return targets.stream()
                .min(Comparator.comparingInt(c -> CoordinateUtils.manhattanDistance(from, c)));
    }

    @Override
    public Optional<Coordinate> findFarthest(Coordinate from, Set<Coordinate> targets) {
        return targets.stream()
                .max(Comparator.comparingInt(c -> CoordinateUtils.manhattanDistance(from, c)));
    }

}
