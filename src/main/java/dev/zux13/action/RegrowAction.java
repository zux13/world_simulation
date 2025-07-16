package dev.zux13.action;

import dev.zux13.board.Board;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.Grass;
import dev.zux13.event.EventBus;
import dev.zux13.event.events.GrassSpawnedEvent;
import dev.zux13.settings.SimulationSettings;

import java.util.Optional;

public class RegrowAction implements Action {

    private final EventBus eventBus;

    public RegrowAction(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void execute(Board board, SimulationSettings settings) {

        int totalTiles = board.getWidth() * board.getHeight();
        int currentGrass = board.getGrassCount();
        int targetGrass = (int) (totalTiles * settings.getGrassDensity());

        int toSpawn = (int) ((targetGrass - currentGrass) * settings.getGrassRespawnRate());
        if (toSpawn <= 0) return;

        spawnGrass(board, toSpawn);
        eventBus.publish(new GrassSpawnedEvent(toSpawn));
    }

    private void spawnGrass(Board board, int count) {
        for (int i = 0; i < count; i++) {
            Optional<Coordinate> optionalCoordinate = board.findRandomEmptyCoordinate();
            if (optionalCoordinate.isEmpty()) {
                break;
            }
            board.setEntityAt(optionalCoordinate.get(), new Grass());
        }
    }
}
