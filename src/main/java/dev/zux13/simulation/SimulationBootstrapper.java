package dev.zux13.simulation;

import dev.zux13.board.Board;
import dev.zux13.event.EventBus;
import dev.zux13.event.EventSubscriber;
import dev.zux13.logger.EventLogger;
import dev.zux13.renderer.ConsoleRenderer;
import dev.zux13.settings.SimulationSettings;

import java.util.List;

public class SimulationBootstrapper {

    public static void bootstrap(Board board, TurnCounter counter, SimulationSettings settings, EventBus eventBus) {

        EventLogger logger = new EventLogger(
                settings.theme(),
                settings.boardHeight(),
                settings.rendererLogWidth(),
                settings.rendererDividerChar()
        );

        List<EventSubscriber> subscribers = List.of(
                logger,
                new CreatureActionExecutor(board, eventBus),
                new HungerManager(board, eventBus),
                new ConsoleRenderer(settings, counter, logger, board, settings.theme())
        );

        subscribers.forEach(subscriber -> subscriber.subscribeToEvents(eventBus));
    }

}
