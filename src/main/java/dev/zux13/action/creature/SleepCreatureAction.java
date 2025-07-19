package dev.zux13.action.creature;

import dev.zux13.board.Board;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;

public record SleepCreatureAction(Creature creature, Coordinate current) implements CreatureAction {
    @Override
    public Coordinate execute(Board board) {
        return current;
    }
}
