package dev.zux13.action;

import dev.zux13.entity.Entity;
import dev.zux13.entity.creature.Creature;
import dev.zux13.board.Coordinate;
import dev.zux13.board.Board;
import dev.zux13.event.EventBus;
import dev.zux13.event.events.SimulationMoveExecutedEvent;
import dev.zux13.settings.SimulationSettings;
import dev.zux13.util.ThreadUtils;

import java.util.Optional;

public class MoveAction implements Action {

    private final EventBus eventBus;

    public MoveAction(EventBus eventBus) {
        this.eventBus = eventBus;
    }

    @Override
    public void execute(Board board, SimulationSettings settings) {

        resetCreaturesMovements(board);

        boolean isMoved;
        do {
            isMoved = false;
            for (Coordinate coordinate : board.getCreatureCoordinates()) {

                Optional<Entity> entityOptional = board.getEntityAt(coordinate);

                if (entityOptional.isEmpty()) {
                    continue;
                }

                Creature creature = (Creature) entityOptional.get();

                if (creature.canMove()) {
                    creature.makeMove(board, coordinate);
                    creature.stepUsed();
                    isMoved = true;
                }
            }

            if (isMoved) {
                if (ThreadUtils.sleepSilently(settings.getTickMillis())) {
                    break;
                }
                eventBus.publish(new SimulationMoveExecutedEvent());
            }

        } while (isMoved);
    }

    private void resetCreaturesMovements(Board board) {
        for (Creature creature : board.getCreatures()) {
            creature.resetMovement();
        }
    }
}
