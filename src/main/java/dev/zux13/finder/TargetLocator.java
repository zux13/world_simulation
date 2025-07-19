package dev.zux13.finder;

import dev.zux13.board.Coordinate;

import java.util.List;
import java.util.Optional;

public interface TargetLocator {
    Optional<Coordinate> findClosest(Coordinate from, List<Coordinate> targets);
    Optional<Coordinate> findFarthest(Coordinate from, List<Coordinate> targets);
}
