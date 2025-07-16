package dev.zux13.entity.creature;

import dev.zux13.board.Coordinate;
import dev.zux13.board.Board;

public class Herbivore extends Creature {

    private Herbivore(HerbivoreBuilder builder) {
        super(builder);
    }

    @Override
    public void makeMove(Board board, Coordinate current) {
        getDecisionMaker().decide(board, current, this);
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
