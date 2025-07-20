package dev.zux13.simulation;

import dev.zux13.action.CreatureMovementTask;
import dev.zux13.action.CreatureRespawnTask;
import dev.zux13.action.GenerateSceneryTask;
import dev.zux13.action.GrassRegrowthTask;
import dev.zux13.action.SimulationTask;
import dev.zux13.board.Board;
import dev.zux13.decision.DecisionMakerFactory;
import dev.zux13.decision.RoamingHelper;
import dev.zux13.event.EventBus;
import dev.zux13.finder.AStarPathFinder;
import dev.zux13.finder.ManhattanTargetLocator;
import dev.zux13.logger.ActionLogger;
import dev.zux13.renderer.ConsoleRenderer;
import dev.zux13.settings.SimulationSettings;

public class SimulationFactory {

    public static Simulation create(SimulationSettings settings) {

        TurnCounter turnCounter = new TurnCounter();
        EventBus eventBus = new EventBus();

        ActionLogger logger = new ActionLogger(
                settings.getTheme(),
                settings.getBoardHeight(),
                settings.getRendererLogWidth(),
                settings.getRendererDividerChar(),
                eventBus
        );

        Board board = new Board(settings.getBoardWidth(), settings.getBoardHeight());

        DecisionMakerFactory decisionMakerFactory = new DecisionMakerFactory(
                new AStarPathFinder(),
                new ManhattanTargetLocator(),
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

        new CreatureActionExecutor(board, eventBus);
        new HungerManager(board, eventBus);
        new ConsoleRenderer(settings, turnCounter, board, logger, settings.getTheme(), eventBus);

        return new Simulation(board, turnCounter, settings, initTasks, turnTasks);
    }
}