package dev.zux13.entity.creature;

import dev.zux13.board.Board;
import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;

public class Herbivore extends Creature {

    private Herbivore(HerbivoreBuilder builder) {
        super(builder);
    }

    @Override
    public void makeMove(Board board, BoardService boardService, Coordinate current) {
        getDecisionMaker().decide(board, boardService, current, this);
    }

    public static class HerbivoreBuilder extends CreatureBuilder<HerbivoreBuilder> {
        @Override
        protected HerbivoreBuilder self() {
            return this;
        }

        @Override
        public Herbivore build() {
            return new Herbivore(this);
        }
    }
}
