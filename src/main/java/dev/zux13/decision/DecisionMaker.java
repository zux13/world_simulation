package dev.zux13.decision;

import dev.zux13.board.Board;
import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;

public interface DecisionMaker {
    void decide(Board board, BoardService boardService, Coordinate current, Creature creature);
}
