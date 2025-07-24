package dev.zux13.task;

import dev.zux13.board.Board;
import dev.zux13.board.BoardService;
import dev.zux13.settings.SimulationSettings;

public interface SimulationTask {
    void execute(Board board, BoardService boardService, SimulationSettings settings);
}