package dev.zux13.action.creature;

import dev.zux13.board.Board;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;

public record MoveCreatureAction(
        Creature creature,
        Coordinate from,
        Coordinate to,
        MoveType moveType) implements CreatureAction {

    @Override
    public Coordinate execute(Board board) {
        board.moveEntity(from, to);
        return from;
    }
}
