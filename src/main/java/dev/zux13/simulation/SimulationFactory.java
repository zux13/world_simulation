package dev.zux13.simulation;

import dev.zux13.action.*;
import dev.zux13.decision.DecisionMakerFactory;
import dev.zux13.event.EventBus;
import dev.zux13.finder.AStarPathFinder;
import dev.zux13.finder.ManhattanTargetLocator;
import dev.zux13.board.Board;
import dev.zux13.logger.ActionLogger;
import dev.zux13.renderer.ConsoleRenderer;
import dev.zux13.settings.SimulationSettings;

public class SimulationFactory {

    public static Simulation create(SimulationSettings settings) {

        EventBus eventBus = new EventBus();
        ActionLogger logger = new ActionLogger(settings.getBoardHeight(), settings.getTheme(), eventBus);
        TurnCounter context = new TurnCounter();

        Board board = new Board(settings.getBoardWidth(), settings.getBoardHeight());

        DecisionMakerFactory decisionMakerFactory = new DecisionMakerFactory(
                new AStarPathFinder(),
                new ManhattanTargetLocator(),
                eventBus
        );

        RegrowAction regrowAction = new RegrowAction(eventBus);
        RespawnAction respawnAction = new RespawnAction(decisionMakerFactory, eventBus);

        Action[] initActions = {
                new GenerateAction(),
                regrowAction,
                respawnAction
        };

        Action[] turnActions = {
                new MoveAction(eventBus),
                regrowAction,
                respawnAction
        };

        new CreatureActionExecutor(board, settings, eventBus);
        new ConsoleRenderer(settings, context, board, logger, settings.getTheme(), eventBus);

        return new Simulation(board, context, settings, initActions, turnActions);
    }
}