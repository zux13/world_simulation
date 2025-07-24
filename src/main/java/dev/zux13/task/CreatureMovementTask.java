package dev.zux13.task;

import dev.zux13.board.BoardService;
import dev.zux13.entity.Entity;
import dev.zux13.entity.creature.Creature;
import dev.zux13.board.Coordinate;
import dev.zux13.board.Board;
import dev.zux13.event.EventBus;
import dev.zux13.event.events.SimulationMoveExecutedEvent;
import dev.zux13.settings.SimulationSettings;
import dev.zux13.util.ThreadUtils;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CreatureMovementTask implements SimulationTask {

    private final EventBus eventBus;

    @Override
    public void execute(Board board, BoardService boardService, SimulationSettings settings) {

        resetCreaturesMovements(boardService);

        boolean isMoved;
        do {
            isMoved = false;
            for (Coordinate coordinate : boardService.getCreatureCoordinates()) {

                Optional<Entity> entityOptional = board.getEntityAt(coordinate);

                if (entityOptional.isEmpty()) {
                    continue;
                }

                Creature creature = (Creature) entityOptional.get();

                if (creature.canMove()) {
                    creature.makeMove(board, boardService, coordinate);
                    creature.stepUsed();
                    isMoved = true;
                }
            }

            if (isMoved) {
                if (ThreadUtils.sleepSilently(settings.tickMillis())) {
                    break;
                }
                eventBus.publish(new SimulationMoveExecutedEvent());
            }

        } while (isMoved);
    }

    private void resetCreaturesMovements(BoardService boardService) {
        for (Creature creature : boardService.getCreatures()) {
            creature.resetMovement();
        }
    }
}
