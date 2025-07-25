package dev.zux13.action;

import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.Entity;
import dev.zux13.entity.creature.Creature;

public record EatCreatureAction(Creature creature,
                                Entity food,
                                Coordinate current,
                                Coordinate target) implements CreatureAction {

    @Override
    public Coordinate execute(BoardService boardService) {
        boardService.removeEntityAt(target);
        creature.eat();
        return current;
    }
}
