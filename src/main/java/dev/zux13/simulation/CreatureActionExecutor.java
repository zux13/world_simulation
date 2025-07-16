package dev.zux13.simulation;

import dev.zux13.board.Board;
import dev.zux13.event.EventBus;
import dev.zux13.event.Priority;
import dev.zux13.event.events.CreatureActionDecidedEvent;
import dev.zux13.settings.SimulationSettings;

public class CreatureActionExecutor {

    private final SimulationSettings settings;
    private final Board board;

    public CreatureActionExecutor(Board board, SimulationSettings settings, EventBus eventBus) {
        this.settings = settings;
        this.board = board;
        eventBus.subscribe(CreatureActionDecidedEvent.class, this::executeCreatureAction, Priority.HIGH);
    }

    private void executeCreatureAction(CreatureActionDecidedEvent event) {
        event.action().execute(board, settings);
    }
}
