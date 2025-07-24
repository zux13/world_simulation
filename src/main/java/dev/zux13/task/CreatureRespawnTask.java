package dev.zux13.task;

import dev.zux13.board.Board;
import dev.zux13.board.BoardService;
import dev.zux13.decision.DecisionMaker;
import dev.zux13.decision.DecisionMakerFactory;
import dev.zux13.entity.creature.Creature;
import dev.zux13.entity.creature.Herbivore;
import dev.zux13.entity.creature.Predator;
import dev.zux13.event.EventBus;
import dev.zux13.event.events.CreatureSpawnedEvent;
import dev.zux13.settings.SimulationSettings;
import dev.zux13.util.RandomUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreatureRespawnTask implements SimulationTask {

    private final DecisionMakerFactory decisionMakerFactory;
    private final EventBus eventBus;

    @Override
    public void execute(Board board, BoardService boardService, SimulationSettings settings) {
        int totalTiles = board.getWidth() * board.getHeight();

        int herbivoreTarget = (int) (totalTiles * settings.herbivoreDensity());
        int predatorTarget = (int) (totalTiles * settings.predatorDensity());

        int currentHerbivores = boardService.getHerbivoresCount();
        int currentPredators = boardService.getPredatorsCount();

        int herbivoresToSpawn = Math.max(0, herbivoreTarget - currentHerbivores);
        int predatorsToSpawn = Math.max(0, predatorTarget - currentPredators);

        spawnHerbivores(boardService, herbivoresToSpawn, settings);
        spawnPredators(boardService, predatorsToSpawn, settings);
    }

    private void spawnHerbivores(BoardService boardService, int count, SimulationSettings settings) {
        DecisionMaker decisionMaker = decisionMakerFactory.forHerbivore();
        boardService.placeEntityAtRandom(() -> {
            Creature herbivore = new Herbivore.HerbivoreBuilder()
                    .decisionMaker(decisionMaker)
                    .maxHp(RandomUtils.randomInRange(
                            settings.herbivoreMinHp(),
                            settings.herbivoreMaxHp())
                    )
                    .speed(RandomUtils.randomInRange(
                            settings.herbivoreMinSpeed(),
                            settings.herbivoreMaxSpeed())
                    )
                    .vision(RandomUtils.randomInRange(
                            settings.herbivoreVisionMin(),
                            settings.herbivoreVisionMax())
                    )
                    .maxHunger(settings.herbivoreMaxHunger())
                    .hungerDamage(settings.herbivoreHungerDamage())
                    .hungerRestore(settings.herbivoreHungerRestore())
                    .healRestore(settings.herbivoreHealRestore())
                    .build();
            boardService.findRandomEmptyCoordinate().ifPresent(coordinate ->
                    eventBus.publish(new CreatureSpawnedEvent(herbivore, coordinate)));
            return herbivore;
        }, count);
    }

    private void spawnPredators(BoardService boardService, int count, SimulationSettings settings) {
        DecisionMaker decisionMaker = decisionMakerFactory.forPredator();
        boardService.placeEntityAtRandom(() -> {
            Creature predator = new Predator.PredatorBuilder()
                    .decisionMaker(decisionMaker)
                    .maxHp(RandomUtils.randomInRange(
                            settings.predatorMinHp(),
                            settings.predatorMaxHp())
                    )
                    .speed(RandomUtils.randomInRange(
                            settings.predatorMinSpeed(),
                            settings.predatorMaxSpeed())
                    )
                    .vision(RandomUtils.randomInRange(
                            settings.predatorVisionMin(),
                            settings.predatorVisionMax())
                    )
                    .attack(RandomUtils.randomInRange(
                            settings.predatorMinAttack(),
                            settings.predatorMaxAttack())
                    )
                    .maxHunger(settings.predatorMaxHunger())
                    .hungerDamage(settings.predatorHungerDamage())
                    .hungerRestore(settings.predatorHungerRestore())
                    .healRestore(settings.predatorHealRestore())
                    .build();
            boardService.findRandomEmptyCoordinate().ifPresent(coordinate ->
                    eventBus.publish(new CreatureSpawnedEvent(predator, coordinate)));
            return predator;
        }, count);
    }
}
