package dev.zux13.action;

import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;

public record SleepCreatureAction(Creature creature, Coordinate current) implements CreatureAction {
    @Override
    public Coordinate execute(BoardService boardService) {
        return current;
    }
}
