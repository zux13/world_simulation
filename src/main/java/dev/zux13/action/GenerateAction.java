package dev.zux13.action;

import dev.zux13.entity.Entity;
import dev.zux13.entity.Rock;
import dev.zux13.entity.Tree;
import dev.zux13.board.Board;
import dev.zux13.board.Coordinate;
import dev.zux13.settings.SimulationSettings;

import java.util.Optional;

public class GenerateAction implements Action {

    @Override
    public void execute(Board board, SimulationSettings settings) {

        int totalTiles = board.getWidth() * board.getHeight();
        int rockCount = (int) (totalTiles * settings.getRockDensity());
        int treeCount = (int) (totalTiles * settings.getTreeDensity());

        placeEntities(board, new Rock(), rockCount);
        placeEntities(board, new Tree(), treeCount);
    }

    private void placeEntities(Board board, Entity entityPrototype, int count) {

        for (int i = 0; i < count; i++) {
            Optional<Coordinate> optionalCoordinate = board.findRandomEmptyCoordinate();
            if (optionalCoordinate.isEmpty()) {
                break;
            }
            board.setEntityAt(optionalCoordinate.get(), entityPrototype);
        }
    }
}
