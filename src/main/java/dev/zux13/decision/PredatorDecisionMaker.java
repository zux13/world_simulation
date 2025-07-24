package dev.zux13.decision;

import dev.zux13.action.*;
import dev.zux13.board.Board;
import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;
import dev.zux13.entity.creature.Predator;
import dev.zux13.event.EventBus;
import dev.zux13.event.events.CreatureActionDecidedEvent;
import dev.zux13.finder.PathFinder;
import dev.zux13.finder.TargetLocator;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class PredatorDecisionMaker implements DecisionMaker {

    private final PathFinder pathFinder;
    private final TargetLocator targetLocator;
    private final RoamingHelper roamingHelper;
    private final EventBus eventBus;

    @Override
    public void decide(Board board, BoardService boardService, Coordinate current, Creature creature) {

        CreatureAction action = tryAttackPrey(board, boardService, current, (Predator) creature)
                .or(() -> tryChasePrey(board, boardService, current, creature))
                .or(() -> tryRoam(board, current, creature))
                .orElseGet(() -> new SleepCreatureAction(creature, current));

        eventBus.publish(new CreatureActionDecidedEvent(action, creature));
    }

    private Optional<CreatureAction> tryAttackPrey(Board board, BoardService boardService, Coordinate current, Predator predator) {
        return boardService.findNeighborHerbivore(current)
                .flatMap(coordinate -> board.getEntityAt(coordinate)
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

    private Optional<CreatureAction> tryChasePrey(Board board, BoardService boardService, Coordinate current, Creature creature) {
        List<Coordinate> visiblePrey = boardService.getVisibleHerbivores(current, creature.getVision());
        return targetLocator.findClosest(current, visiblePrey)
                .flatMap(target -> pathFinder.nextStep(board, boardService, current, target))
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.CHASE));
    }

    private Optional<CreatureAction> tryRoam(Board board, Coordinate current, Creature creature) {
        return roamingHelper.findPersistentRoamTarget(board, current, creature)
                .map(target -> new MoveCreatureAction(creature, current, target, MoveType.ROAM));
    }
}