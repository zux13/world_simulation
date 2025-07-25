package dev.zux13.navigator;

import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;

import java.util.Optional;

public interface Navigator {
    Optional<Coordinate> nextStep(BoardService boardService, Coordinate from, Coordinate to);
}
