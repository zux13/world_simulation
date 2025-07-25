package dev.zux13.entity.creature;

import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;
import lombok.Getter;

@Getter
public class Predator extends Creature {

    private final int attack;

    private Predator(PredatorBuilder builder) {
        super(builder);
        this.attack = builder.attack;
    }

    @Override
    public void makeMove(BoardService boardService, Coordinate current) {
        getDecisionMaker().decide(boardService, current, this);
    }

    public static class PredatorBuilder extends CreatureBuilder<PredatorBuilder> {
        private int attack;

        public PredatorBuilder attack(int attack) {
            this.attack = attack;
            return this;
        }

        @Override
        protected PredatorBuilder self() {
            return this;
        }

        @Override
        public Predator build() {
            return new Predator(this);
        }
    }
}
