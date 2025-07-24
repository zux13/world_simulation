package dev.zux13.action;

import dev.zux13.board.Board;
import dev.zux13.board.Coordinate;

@FunctionalInterface
public interface CreatureAction {
    Coordinate execute(Board board);
}
