package dev.zux13.action.creature;

import dev.zux13.board.Board;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;
import dev.zux13.settings.SimulationSettings;

public record MoveCreatureAction(
        Creature creature,
        Coordinate from,
        Coordinate to,
        MoveType moveType) implements CreatureAction {

    @Override
    public void execute(Board board, SimulationSettings settings) {
        board.moveEntity(from, to);
    }
}
