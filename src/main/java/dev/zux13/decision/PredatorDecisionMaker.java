package dev.zux13.decision;

import dev.zux13.action.creature.*;
import dev.zux13.board.Board;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;
import dev.zux13.entity.creature.Predator;
import dev.zux13.event.EventBus;
import dev.zux13.event.events.CreatureActionDecidedEvent;
import dev.zux13.finder.PathFinder;
import dev.zux13.finder.TargetLocator;
import dev.zux13.util.CoordinateUtils;

import java.util.Optional;
import java.util.Set;

public class PredatorDecisionMaker implements DecisionMaker {

    private final PathFinder pathFinder;
    private final TargetLocator targetLocator;
    private final EventBus eventBus;

    public PredatorDecisionMaker(PathFinder pathFinder, TargetLocator targetLocator, EventBus eventBus) {
        this.pathFinder = pathFinder;
        this.targetLocator = targetLocator;
        this.eventBus = eventBus;
    }

    @Override
    public void decide(Board board, Coordinate current, Creature creature) {

        Optional<CreatureAction> action = tryAttackPrey(board, current, (Predator) creature)
                .or(() -> tryChasePrey(board, current, creature))
                .or(() -> tryRoam(board, current, creature));

        action.ifPresent(creatureAction -> eventBus.publish(new CreatureActionDecidedEvent(creatureAction)));
    }

    private Optional<CreatureAction> tryAttackPrey(Board board, Coordinate current, Predator predator) {
        return board.findNeighborHerbivore(current)
                .flatMap(coordinate -> board.getEntityAt(coordinate)
                        .filter(entity -> entity instanceof Creature)
                        .map(entity -> {
                            Creature prey = (Creature) entity;
                            if (predator.getAttack() >= prey.getCurrentHp()) {
                                return new EatCreatureAction(predator, prey, coordinate);
                            } else {
                                return new AttackCreatureAction(predator, prey, coordinate);
                            }
                        }));
    }

    private Optional<CreatureAction> tryChasePrey(Board board, Coordinate current, Creature creature) {
        Set<Coordinate> visiblePrey = board.getVisibleHerbivores(current, creature.getVision());
        return targetLocator.findClosest(current, visiblePrey)
                .flatMap(target -> pathFinder.nextStep(board, current, target))
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.CHASE));
    }

    private Optional<CreatureAction> tryRoam(Board board, Coordinate current, Creature creature) {
        Coordinate roamTarget = CoordinateUtils.getRoamTarget(
                current, board.getWidth(), board.getHeight(), creature.hashCode()
        );
        return pathFinder.nextStep(board, current, roamTarget)
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.ROAM));
    }
}