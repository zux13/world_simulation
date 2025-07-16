package dev.zux13.action.creature;

import dev.zux13.board.Board;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.Entity;
import dev.zux13.entity.creature.Creature;
import dev.zux13.entity.creature.Predator;
import dev.zux13.settings.SimulationSettings;

public record EatCreatureAction(Creature creature, Entity food, Coordinate target)
        implements CreatureAction {

    @Override
    public void execute(Board board, SimulationSettings settings) {
        board.removeEntityAt(target);

        int healAmount = creature instanceof Predator
                ? settings.getPredatorHealAmount()
                : settings.getHerbivoreHealAmount();

        creature.eat(healAmount);
    }
}
