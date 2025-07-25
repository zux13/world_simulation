package dev.zux13.simulation;

import dev.zux13.board.BoardService;
import dev.zux13.decision.DecisionMakerFactory;
import dev.zux13.decision.RoamingHelper;
import dev.zux13.event.EventBus;
import dev.zux13.navigator.AStarNavigator;
import dev.zux13.settings.SimulationSettings;
import dev.zux13.task.*;

public class SimulationFactory {

    public static Simulation create(BoardService boardService,
                                    TurnCounter counter,
                                    SimulationSettings settings,
                                    EventBus eventBus) {

        DecisionMakerFactory decisionMakerFactory = new DecisionMakerFactory(
                new AStarNavigator(),
                new RoamingHelper(),
                eventBus
        );

        GrassRegrowthTask grassRegrowthTask = new GrassRegrowthTask(eventBus);
        CreatureRespawnTask creatureRespawnTask = new CreatureRespawnTask(decisionMakerFactory, eventBus);

        SimulationTask[] initTasks = {
                new GenerateSceneryTask(),
                grassRegrowthTask,
                creatureRespawnTask
        };

        SimulationTask[] turnTasks = {
                new CreatureMovementTask(eventBus),
                grassRegrowthTask,
                creatureRespawnTask
        };

        return new Simulation(boardService, counter, settings, initTasks, turnTasks);
    }
}