package dev.zux13.decision;

import dev.zux13.action.CreatureAction;
import dev.zux13.action.EatCreatureAction;
import dev.zux13.action.MoveCreatureAction;
import dev.zux13.action.MoveType;
import dev.zux13.action.SleepCreatureAction;
import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.EntityType;
import dev.zux13.entity.creature.Creature;
import dev.zux13.event.EventBus;
import dev.zux13.event.events.CreatureActionDecidedEvent;
import dev.zux13.navigator.Navigator;
import dev.zux13.util.CoordinateUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class HerbivoreDecisionMaker implements DecisionMaker {

    private final Navigator navigator;
    private final RoamingHelper roamingHelper;
    private final EventBus eventBus;

   @Override
    public void decide(BoardService boardService, Coordinate current, Creature creature) {
        CreatureAction action = tryEscapePredator(boardService, current, creature)
                .or(() -> tryEatNeighborGrass(boardService, current, creature))
                .or(() -> tryMoveToVisibleGrass(boardService, current, creature))
                .or(() -> tryRoam(boardService, current, creature))
                .orElseGet(() -> new SleepCreatureAction(creature, current));

        eventBus.publish(new CreatureActionDecidedEvent(action, creature));
    }

    private Optional<CreatureAction> tryEscapePredator(BoardService boardService, Coordinate current, Creature creature) {
        List<Coordinate> visiblePredators = boardService.getVisible(EntityType.PREDATOR, current, creature.getVision());
        return CoordinateUtils.findClosest(current, visiblePredators)
                .flatMap(predator -> {
                    List<Coordinate> escapeOptions = boardService.findEmptyNeighbors(current);
                    return CoordinateUtils.findFarthest(predator, escapeOptions);
                })
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.FLEE));
    }

    private Optional<CreatureAction> tryEatNeighborGrass(BoardService boardService, Coordinate current, Creature creature) {
        return boardService.findNeighbor(EntityType.GRASS, current)
                .flatMap(grass -> boardService.getEntityAt(grass)
                        .map(entity -> new EatCreatureAction(creature, entity, current, grass)));
    }

    private Optional<CreatureAction> tryMoveToVisibleGrass(BoardService boardService, Coordinate current, Creature creature) {
        List<Coordinate> visibleGrass = boardService.getVisible(EntityType.GRASS, current, creature.getVision());
        return CoordinateUtils.findClosest(current, visibleGrass)
                .flatMap(target -> navigator.nextStep(boardService, current, target))
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.MOVE));
    }

    private Optional<CreatureAction> tryRoam(BoardService boardService, Coordinate current, Creature creature) {
        return roamingHelper.findPersistentRoamTarget(boardService, current, creature)
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.ROAM));
    }
}