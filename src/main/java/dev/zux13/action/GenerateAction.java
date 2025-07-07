package dev.zux13.action;

import dev.zux13.config.SimulationConfig;
import dev.zux13.entity.Entity;
import dev.zux13.entity.Rock;
import dev.zux13.entity.Tree;
import dev.zux13.map.Coordinate;
import dev.zux13.map.WorldMap;

import java.util.Random;

public class GenerateAction implements Action {

    @Override
    public void execute(WorldMap worldMap) {
        int width = worldMap.getWidth();
        int height = worldMap.getHeight();
        int totalTiles = width * height;

        int rockCount = (int) (totalTiles * SimulationConfig.ROCK_DENSITY);
        int treeCount = (int) (totalTiles * SimulationConfig.TREE_DENSITY);

        placeRandomEntities(worldMap, new Rock(), rockCount);
        placeRandomEntities(worldMap, new Tree(), treeCount);
    }

    private void placeRandomEntities(WorldMap map, Entity entityPrototype, int count) {
        Random random = new Random();

        int width = map.getWidth();
        int height = map.getHeight();

        int placed = 0;
        while (placed < count) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            Coordinate coordinate = new Coordinate(x, y);

            if (map.getEntityAt(coordinate) == null) {
                map.setEntityAt(coordinate, entityPrototype);
                placed++;
            }
        }
    }
}
