package dev.zux13.action;

import dev.zux13.board.Board;
import dev.zux13.board.Coordinate;
import dev.zux13.decision.DecisionMaker;
import dev.zux13.decision.DecisionMakerFactory;
import dev.zux13.entity.creature.Creature;
import dev.zux13.entity.creature.Herbivore;
import dev.zux13.entity.creature.Predator;
import dev.zux13.event.EventBus;
import dev.zux13.event.events.CreatureSpawnedEvent;
import dev.zux13.settings.SimulationSettings;
import dev.zux13.util.RandomUtils;

import java.util.Optional;

public class RespawnAction implements Action {

    private final DecisionMakerFactory decisionMakerFactory;
    private final EventBus eventBus;

    public RespawnAction(DecisionMakerFactory decisionMakerFactory, EventBus eventBus) {
        this.decisionMakerFactory = decisionMakerFactory;
        this.eventBus = eventBus;
    }

    @Override
    public void execute(Board board, SimulationSettings settings) {
        int totalTiles = board.getWidth() * board.getHeight();

        int herbivoreTarget = (int) (totalTiles * settings.getHerbivoreDensity());
        int predatorTarget = (int) (totalTiles * settings.getPredatorDensity());

        int currentHerbivores = board.getHerbivoresCount();
        int currentPredators = board.getPredatorsCount();

        int herbivoresToSpawn = Math.max(0, herbivoreTarget - currentHerbivores);
        int predatorsToSpawn = Math.max(0, predatorTarget - currentPredators);

        spawnHerbivores(board, herbivoresToSpawn, settings);
        spawnPredators(board, predatorsToSpawn, settings);
    }

    private void spawnHerbivores(Board board, int count, SimulationSettings settings) {
        DecisionMaker decisionMaker = decisionMakerFactory.forHerbivore();

        for (int i = 0; i < count; i++) {
            Optional<Coordinate> optionalCoordinate = board.findRandomEmptyCoordinate();
            if (optionalCoordinate.isEmpty()) {
                break;
            }
            Coordinate coordinate = optionalCoordinate.get();

            Creature herbivore = new Herbivore.HerbivoreBuilder()
                    .decisionMaker(decisionMaker)
                    .maxHp(RandomUtils.randomInRange(
                            settings.getHerbivoreMinHp(),
                            settings.getHerbivoreMaxHp())
                    )
                    .speed(RandomUtils.randomInRange(
                            settings.getHerbivoreMinSpeed(),
                            settings.getHerbivoreMaxSpeed())
                    )
                    .vision(RandomUtils.randomInRange(
                            settings.getHerbivoreVisionMin(),
                            settings.getHerbivoreVisionMax())
                    )
                    .maxHunger(settings.getHerbivoreMaxHunger())
                    .hungerDamage(settings.getHerbivoreHungerDamage())
                    .hungerRestore(settings.getHerbivoreHungerRestore())
                    .build();

            board.setEntityAt(coordinate, herbivore);
            eventBus.publish(new CreatureSpawnedEvent(herbivore, coordinate));
        }
    }

    private void spawnPredators(Board board, int count, SimulationSettings settings) {
        DecisionMaker decisionMaker = decisionMakerFactory.forPredator();

        for (int i = 0; i < count; i++) {
            Optional<Coordinate> optionalCoordinate = board.findRandomEmptyCoordinate();
            if (optionalCoordinate.isEmpty()) {
                break;
            }
            Coordinate coordinate = optionalCoordinate.get();

            Creature predator = new Predator.PredatorBuilder()
                    .decisionMaker(decisionMaker)
                    .maxHp(RandomUtils.randomInRange(
                            settings.getPredatorMinHp(),
                            settings.getPredatorMaxHp())
                    )
                    .speed(RandomUtils.randomInRange(
                            settings.getPredatorMinSpeed(),
                            settings.getPredatorMaxSpeed())
                    )
                    .vision(RandomUtils.randomInRange(
                            settings.getPredatorVisionMin(),
                            settings.getPredatorVisionMax())
                    )
                    .attack(RandomUtils.randomInRange(
                            settings.getPredatorMinAttack(),
                            settings.getPredatorMaxAttack())
                    )
                    .maxHunger(settings.getPredatorMaxHunger())
                    .hungerDamage(settings.getPredatorHungerDamage())
                    .hungerRestore(settings.getPredatorHungerRestore())
                    .build();

            board.setEntityAt(coordinate, predator);
            eventBus.publish(new CreatureSpawnedEvent(predator, coordinate));
        }
    }

}