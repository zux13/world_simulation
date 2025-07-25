package dev.zux13.action;

import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;
import dev.zux13.entity.creature.Predator;

public record AttackCreatureAction(Predator predator,
                                   Creature target,
                                   Coordinate current) implements CreatureAction {

    @Override
    public Coordinate execute(BoardService boardService) {
        target.takeDamage(predator.getAttack());
        return current;
    }
}
