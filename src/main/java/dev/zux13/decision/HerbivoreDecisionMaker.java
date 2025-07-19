package dev.zux13.decision;

import dev.zux13.action.creature.CreatureAction;
import dev.zux13.action.creature.EatCreatureAction;
import dev.zux13.action.creature.MoveCreatureAction;
import dev.zux13.action.creature.MoveType;
import dev.zux13.action.creature.SleepCreatureAction;
import dev.zux13.board.Board;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;
import dev.zux13.event.EventBus;
import dev.zux13.event.events.CreatureActionDecidedEvent;
import dev.zux13.finder.PathFinder;
import dev.zux13.finder.TargetLocator;

import java.util.List;
import java.util.Optional;

public class HerbivoreDecisionMaker implements DecisionMaker {

    private final PathFinder pathFinder;
    private final TargetLocator targetLocator;
    private final RoamingHelper roamingHelper;
    private final EventBus eventBus;

    public HerbivoreDecisionMaker(PathFinder pathFinder,
                                  TargetLocator targetLocator,
                                  RoamingHelper roamingHelper,
                                  EventBus eventBus) {
        this.pathFinder = pathFinder;
        this.targetLocator = targetLocator;
        this.roamingHelper = roamingHelper;
        this.eventBus = eventBus;
    }

   @Override
    public void decide(Board board, Coordinate current, Creature creature) {
        CreatureAction action = tryEscapePredator(board, current, creature)
                .or(() -> tryEatNeighborGrass(board, current, creature))
                .or(() -> tryMoveToVisibleGrass(board, current, creature))
                .or(() -> tryRoam(board, current, creature))
                .orElseGet(() -> new SleepCreatureAction(creature, current));

        eventBus.publish(new CreatureActionDecidedEvent(action, creature));
    }

    private Optional<CreatureAction> tryEscapePredator(Board board, Coordinate current, Creature creature) {
        List<Coordinate> visiblePredators = board.getVisiblePredators(current, creature.getVision());
        return targetLocator.findClosest(current, visiblePredators)
                .flatMap(predator -> {
                    List<Coordinate> escapeOptions = board.findEmptyNeighbors(current);
                    return targetLocator.findFarthest(predator, escapeOptions);
                })
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.FLEE));
    }

    private Optional<CreatureAction> tryEatNeighborGrass(Board board, Coordinate current, Creature creature) {
        return board.findNeighborGrass(current)
                .flatMap(grass -> board.getEntityAt(grass)
                        .map(entity -> new EatCreatureAction(creature, entity, current, grass)));
    }

    private Optional<CreatureAction> tryMoveToVisibleGrass(Board board, Coordinate current, Creature creature) {
        List<Coordinate> visibleGrass = board.getVisibleGrass(current, creature.getVision());
        return targetLocator.findClosest(current, visibleGrass)
                .flatMap(target -> pathFinder.nextStep(board, current, target))
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.MOVE));
    }

    private Optional<CreatureAction> tryRoam(Board board, Coordinate current, Creature creature) {
        return roamingHelper.findPersistentRoamTarget(board, current, creature)
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.ROAM));
    }
}