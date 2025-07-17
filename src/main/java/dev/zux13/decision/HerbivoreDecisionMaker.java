package dev.zux13.decision;

import dev.zux13.action.creature.CreatureAction;
import dev.zux13.action.creature.EatCreatureAction;
import dev.zux13.action.creature.MoveCreatureAction;
import dev.zux13.action.creature.MoveType;
import dev.zux13.board.Board;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;
import dev.zux13.event.EventBus;
import dev.zux13.event.events.CreatureActionDecidedEvent;
import dev.zux13.finder.PathFinder;
import dev.zux13.finder.TargetLocator;
import dev.zux13.util.CoordinateUtils;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class HerbivoreDecisionMaker implements DecisionMaker {

    private final PathFinder pathFinder;
    private final TargetLocator targetLocator;
    private final EventBus eventBus;

    public HerbivoreDecisionMaker(PathFinder pathFinder, TargetLocator targetLocator, EventBus eventBus) {
        this.pathFinder = pathFinder;
        this.targetLocator = targetLocator;
        this.eventBus = eventBus;
    }

    @Override
    public void decide(Board board, Coordinate current, Creature creature) {

        Optional<CreatureAction> action = tryEscapePredator(board, current, creature)
                .or(() -> tryEatNeighborGrass(board, current, creature))
                .or(() -> tryMoveToVisibleGrass(board, current, creature))
                .or(() -> tryRoam(board, current, creature));

        action.ifPresent(creatureAction -> eventBus.publish(
                new CreatureActionDecidedEvent(creatureAction, creature))
        );
    }

    private Optional<CreatureAction> tryEscapePredator(Board board, Coordinate current, Creature creature) {
        Set<Coordinate> visiblePredators = board.getVisiblePredators(current, creature.getVision());
        return targetLocator.findClosest(current, visiblePredators)
                .flatMap(predator -> {
                    Set<Coordinate> escapeOptions = board.getNeighbors(current).stream()
                            .filter(board::isTileEmpty)
                            .collect(Collectors.toSet());
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
        Set<Coordinate> visibleGrass = board.getVisibleGrass(current, creature.getVision());
        return targetLocator.findClosest(current, visibleGrass)
                .flatMap(target -> pathFinder.nextStep(board, current, target))
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.MOVE));
    }

    private Optional<CreatureAction> tryRoam(Board board, Coordinate current, Creature creature) {
        Coordinate roamTarget = CoordinateUtils.getRoamTarget(
                current, board.getWidth(), board.getHeight(), creature.hashCode()
        );
        return pathFinder.nextStep(board, current, roamTarget)
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.ROAM));
    }
}