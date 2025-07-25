package dev.zux13.decision;

import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;

public interface DecisionMaker {
    void decide(BoardService boardService, Coordinate current, Creature creature);
}
