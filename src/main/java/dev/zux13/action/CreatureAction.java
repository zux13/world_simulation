package dev.zux13.action;

import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;

@FunctionalInterface
public interface CreatureAction {
    Coordinate execute(BoardService boardService);
}
