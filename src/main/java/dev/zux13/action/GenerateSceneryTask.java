package dev.zux13.action;

import dev.zux13.board.Board;
import dev.zux13.entity.Rock;
import dev.zux13.entity.Tree;
import dev.zux13.settings.SimulationSettings;

public class GenerateSceneryTask implements SimulationTask {

    @Override
    public void execute(Board board, SimulationSettings settings) {
        int totalTiles = board.getWidth() * board.getHeight();
        int rockCount = (int) (totalTiles * settings.getRockDensity());
        int treeCount = (int) (totalTiles * settings.getTreeDensity());

        board.placeEntityAtRandom(new Rock(), rockCount);
        board.placeEntityAtRandom(new Tree(), treeCount);
    }
}
