package dev.zux13.decision;

import dev.zux13.action.CreatureAction;
import dev.zux13.action.EatCreatureAction;
import dev.zux13.action.MoveCreatureAction;
import dev.zux13.action.MoveType;
import dev.zux13.action.SleepCreatureAction;
import dev.zux13.board.Board;
import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;
import dev.zux13.event.EventBus;
import dev.zux13.event.events.CreatureActionDecidedEvent;
import dev.zux13.finder.PathFinder;
import dev.zux13.finder.TargetLocator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HerbivoreDecisionMaker implements DecisionMaker {

    private final PathFinder pathFinder;
    private final TargetLocator targetLocator;
    private final RoamingHelper roamingHelper;
    private final EventBus eventBus;

   @Override
    public void decide(Board board, BoardService boardService, Coordinate current, Creature creature) {
        CreatureAction action = tryEscapePredator(boardService, current, creature)
                .or(() -> tryEatNeighborGrass(board, boardService, current, creature))
                .or(() -> tryMoveToVisibleGrass(board, boardService, current, creature))
                .or(() -> tryRoam(board, current, creature))
                .orElseGet(() -> new SleepCreatureAction(creature, current));

        eventBus.publish(new CreatureActionDecidedEvent(action, creature));
    }

    private Optional<CreatureAction> tryEscapePredator(BoardService boardService, Coordinate current, Creature creature) {
        List<Coordinate> visiblePredators = boardService.getVisiblePredators(current, creature.getVision());
        return targetLocator.findClosest(current, visiblePredators)
                .flatMap(predator -> {
                    List<Coordinate> escapeOptions = boardService.findEmptyNeighbors(current);
                    return targetLocator.findFarthest(predator, escapeOptions);
                })
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.FLEE));
    }

    private Optional<CreatureAction> tryEatNeighborGrass(Board board, BoardService boardService, Coordinate current, Creature creature) {
        return boardService.findNeighborGrass(current)
                .flatMap(grass -> board.getEntityAt(grass)
                        .map(entity -> new EatCreatureAction(creature, entity, current, grass)));
    }

    private Optional<CreatureAction> tryMoveToVisibleGrass(Board board, BoardService boardService, Coordinate current, Creature creature) {
        List<Coordinate> visibleGrass = boardService.getVisibleGrass(current, creature.getVision());
        return targetLocator.findClosest(current, visibleGrass)
                .flatMap(target -> pathFinder.nextStep(board, boardService, current, target))
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.MOVE));
    }

    private Optional<CreatureAction> tryRoam(Board board, Coordinate current, Creature creature) {
        return roamingHelper.findPersistentRoamTarget(board, current, creature)
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.ROAM));
    }
}