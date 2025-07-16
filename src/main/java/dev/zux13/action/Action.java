package dev.zux13.action;

import dev.zux13.board.Board;
import dev.zux13.settings.SimulationSettings;

public interface Action {
    void execute(Board board, SimulationSettings settings);
}