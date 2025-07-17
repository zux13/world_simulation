package dev.zux13.action.creature;

import dev.zux13.board.Board;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;
import dev.zux13.entity.creature.Predator;

public record AttackCreatureAction(Predator predator,
                                   Creature target,
                                   Coordinate current) implements CreatureAction {

    @Override
    public Coordinate execute(Board board) {
        target.takeDamage(predator.getAttack());
        return current;
    }
}
