package dev.zux13.action;

import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;

public record MoveCreatureAction(
        Creature creature,
        Coordinate from,
        Coordinate to,
        MoveType moveType) implements CreatureAction {

    @Override
    public Coordinate execute(BoardService boardService) {
        boardService.getEntityAt(from).ifPresent(entity -> {
            boardService.removeEntityAt(from);
            boardService.setEntityAt(to, entity);
        });
        return to;
    }
}
