package dev.zux13.finder;

import dev.zux13.board.Coordinate;

import java.util.Optional;
import java.util.Set;

public interface TargetLocator {
    Optional<Coordinate> findClosest(Coordinate from, Set<Coordinate> targets);
    Optional<Coordinate> findFarthest(Coordinate from, Set<Coordinate> targets);
}
