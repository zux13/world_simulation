package dev.zux13.task;

import dev.zux13.board.Board;
import dev.zux13.board.BoardService;
import dev.zux13.entity.Rock;
import dev.zux13.entity.Tree;
import dev.zux13.settings.SimulationSettings;

public class GenerateSceneryTask implements SimulationTask {

    @Override
    public void execute(Board board, BoardService boardService, SimulationSettings settings) {
        int totalTiles = board.getWidth() * board.getHeight();
        int rockCount = (int) (totalTiles * settings.rockDensity());
        int treeCount = (int) (totalTiles * settings.treeDensity());

        boardService.placeEntityAtRandom(new Rock(), rockCount);
        boardService.placeEntityAtRandom(new Tree(), treeCount);
    }
}
