package dev.zux13.simulation;

import dev.zux13.board.Board;
import dev.zux13.event.EventBus;
import dev.zux13.event.Priority;
import dev.zux13.event.events.CreatureActionExecutedEvent;
import dev.zux13.event.events.CreatureDiedOfHungerEvent;
import dev.zux13.event.events.CreatureIsStarvingEvent;

public class HungerManager {

    private final Board board;
    private final EventBus eventBus;

    public HungerManager(Board board, EventBus eventBus) {
        this.board = board;
        this.eventBus = eventBus;
        this.eventBus.subscribe(CreatureActionExecutedEvent.class, this::onCreatureActionExecuted, Priority.HIGH);
    }

    private void onCreatureActionExecuted(CreatureActionExecutedEvent event) {
        var creature = event.creature();
        var coordinate = event.coordinate();

        creature.tickHunger();

        if (!creature.isAlive()) {
            board.removeEntityAt(coordinate);
            eventBus.publish(new CreatureDiedOfHungerEvent(creature, coordinate));
        } else if (creature.isStarving()) {
            eventBus.publish(new CreatureIsStarvingEvent(creature, coordinate));
        }
    }
}
