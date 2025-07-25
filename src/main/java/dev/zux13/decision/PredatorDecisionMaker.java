package dev.zux13.decision;

import dev.zux13.action.*;
import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.EntityType;
import dev.zux13.entity.creature.Creature;
import dev.zux13.entity.creature.Predator;
import dev.zux13.event.EventBus;
import dev.zux13.event.events.CreatureActionDecidedEvent;
import dev.zux13.navigator.Navigator;
import dev.zux13.util.CoordinateUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PredatorDecisionMaker implements DecisionMaker {

    private final Navigator navigator;
    private final RoamingHelper roamingHelper;
    private final EventBus eventBus;

    @Override
    public void decide(BoardService boardService, Coordinate current, Creature creature) {

        CreatureAction action = tryAttackPrey(boardService, current, (Predator) creature)
                .or(() -> tryChasePrey(boardService, current, creature))
                .or(() -> tryRoam(boardService, current, creature))
                .orElseGet(() -> new SleepCreatureAction(creature, current));

        eventBus.publish(new CreatureActionDecidedEvent(action, creature));
    }

    private Optional<CreatureAction> tryAttackPrey(BoardService boardService, Coordinate current, Predator predator) {
        return boardService.findNeighbor(EntityType.HERBIVORE, current)
                .flatMap(coordinate -> boardService.getEntityAt(coordinate)
                        .filter(entity -> entity instanceof Creature)
                        .map(entity -> {
                            Creature prey = (Creature) entity;
                            if (predator.getAttack() >= prey.getCurrentHp()) {
                                return new EatCreatureAction(predator, prey, current, coordinate);
                            } else {
                                return new AttackCreatureAction(predator, prey, coordinate);
                            }
                        }));
    }

    private Optional<CreatureAction> tryChasePrey(BoardService boardService, Coordinate current, Creature creature) {
        List<Coordinate> visiblePrey = boardService.getVisible(EntityType.HERBIVORE, current, creature.getVision());
        return CoordinateUtils.findClosest(current, visiblePrey)
                .flatMap(target -> navigator.nextStep(boardService, current, target))
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.CHASE));
    }

    private Optional<CreatureAction> tryRoam(BoardService boardService, Coordinate current, Creature creature) {
        return roamingHelper.findPersistentRoamTarget(boardService, current, creature)
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.ROAM));
    }
}