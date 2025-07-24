package dev.zux13.finder;

import dev.zux13.board.Board;
import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;

import java.util.Optional;

public interface PathFinder {
    Optional<Coordinate> nextStep(Board board, BoardService boardService, Coordinate from, Coordinate to);
}
