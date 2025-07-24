package dev.zux13.action;

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
        board.getEntityAt(from).ifPresent(entity -> {
            board.removeEntityAt(from);
            board.setEntityAt(to, entity);
        });
        return to;
    }
}
