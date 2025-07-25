package dev.zux13.entity.creature;

import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;

public class Herbivore extends Creature {

    private Herbivore(HerbivoreBuilder builder) {
        super(builder);
    }

    @Override
    public void makeMove(BoardService boardService, Coordinate current) {
        getDecisionMaker().decide(boardService, current, this);
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
