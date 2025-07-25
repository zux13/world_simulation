package dev.zux13.task;

import dev.zux13.board.BoardService;
import dev.zux13.settings.SimulationSettings;

public interface SimulationTask {
    void execute(BoardService boardService, SimulationSettings settings);
}