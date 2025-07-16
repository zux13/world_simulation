package dev.zux13.finder;

import dev.zux13.board.Coordinate;
import dev.zux13.board.Board;

import java.util.Optional;

public interface PathFinder {
    Optional<Coordinate> nextStep(Board map, Coordinate from, Coordinate to);
}
