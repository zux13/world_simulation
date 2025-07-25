package dev.zux13.task;

import dev.zux13.board.BoardService;
import dev.zux13.entity.EntityType;
import dev.zux13.entity.Grass;
import dev.zux13.event.EventBus;
import dev.zux13.event.events.GrassSpawnedEvent;
import dev.zux13.settings.SimulationSettings;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GrassRegrowthTask implements SimulationTask {

    private final EventBus eventBus;

    @Override
    public void execute(BoardService boardService, SimulationSettings settings) {
        int totalTiles = boardService.getWidth() * boardService.getHeight();
        int currentGrass = boardService.getCountOf(EntityType.GRASS);
        int maxGrass = (int) (totalTiles * settings.grassDensity());

        int toSpawn = Math.max(0, maxGrass - currentGrass);

        if (toSpawn > 0) {
            boardService.placeEntityAtRandom(Grass::new, toSpawn);
            eventBus.publish(new GrassSpawnedEvent(toSpawn));
        }
    }
}
