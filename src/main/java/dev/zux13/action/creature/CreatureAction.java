package dev.zux13.action.creature;

import dev.zux13.board.Board;
import dev.zux13.settings.SimulationSettings;

@FunctionalInterface
public interface CreatureAction {
    void execute(Board board, SimulationSettings settings);
}
