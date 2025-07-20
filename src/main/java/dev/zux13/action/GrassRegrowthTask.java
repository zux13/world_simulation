package dev.zux13.action;

import dev.zux13.board.Board;
import dev.zux13.entity.Grass;
import dev.zux13.event.EventBus;
import dev.zux13.event.events.GrassSpawnedEvent;
import dev.zux13.settings.SimulationSettings;

public class GrassRegrowthTask implements SimulationTask {

    private final EventBus eventBus;

    public GrassRegrowthTask(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void execute(Board board, SimulationSettings settings) {
        int totalTiles = board.getWidth() * board.getHeight();
        int currentGrass = board.getGrassCount();
        int targetGrass = (int) (totalTiles * settings.getGrassDensity());

        int toSpawn = (int) ((targetGrass - currentGrass) * settings.getGrassRespawnRate());
        if (toSpawn <= 0) {
            return;
        }

        board.placeEntityAtRandom(Grass::new, toSpawn);
        eventBus.publish(new GrassSpawnedEvent(toSpawn));
    }
}
