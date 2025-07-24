package dev.zux13.entity.creature;

import dev.zux13.board.BoardService;
import dev.zux13.decision.DecisionMaker;
import dev.zux13.entity.Entity;
import dev.zux13.board.Coordinate;
import dev.zux13.board.Board;
import lombok.Getter;

@Getter
public abstract class Creature extends Entity {

    private final DecisionMaker decisionMaker;
    private final int maxHp;
    private final int speed;
    private final int vision;
    private final int maxHunger;
    private final int hungerDamage;
    private final int hungerRestore;
    private final int healRestore;
    private int currentHp;
    private int currentHunger;
    private int remainingSteps;

    protected Creature(CreatureBuilder<?> builder) {
        this.decisionMaker = builder.decisionMaker;
        this.maxHp = builder.maxHp;
        this.speed = builder.speed;
        this.vision = builder.vision;
        this.maxHunger = builder.maxHunger;
        this.hungerDamage = builder.hungerDamage;
        this.hungerRestore = builder.hungerRestore;
        this.healRestore = builder.healRestore;
        this.currentHp = builder.maxHp;
        this.currentHunger = builder.maxHunger;
        this.remainingSteps = builder.speed;
    }

    public void eat() {
        this.currentHp = Math.min(this.currentHp + healRestore, maxHp);
        this.currentHunger = Math.min(this.currentHunger + hungerRestore, maxHunger);
    }

    public void takeDamage(int amount) {
        this.currentHp -= amount;
    }

    public boolean isAlive() {
        return currentHp > 0;
    }

    public void resetMovement() {
        remainingSteps = speed;
    }

    public boolean canMove() {
        return remainingSteps > 0;
    }

    public void stepUsed() {
        remainingSteps--;
    }

    public void tickHunger() {
        this.currentHunger = Math.max(currentHunger - 1, 0);
        if (currentHunger == 0) {
            takeDamage(hungerDamage);
        }
    }

    public boolean isStarving() {
        return currentHunger <= 0;
    }

    public abstract void makeMove(Board board, BoardService boardService, Coordinate current);

    public static abstract class CreatureBuilder<T extends CreatureBuilder<T>> {
        private DecisionMaker decisionMaker;
        private int maxHp;
        private int speed;
        private int vision;
        private int maxHunger;
        private int hungerDamage;
        private int hungerRestore;
        private int healRestore;

        public T decisionMaker(DecisionMaker decisionMaker) {
            this.decisionMaker = decisionMaker;
            return self();
        }

        public T maxHp(int maxHp) {
            this.maxHp = maxHp;
            return self();
        }

        public T speed(int speed) {
            this.speed = speed;
            return self();
        }

        public T vision(int vision) {
            this.vision = vision;
            return self();
        }

        public T maxHunger(int maxHunger) {
            this.maxHunger = maxHunger;
            return self();
        }

        public T hungerDamage(int hungerDamage) {
            this.hungerDamage = hungerDamage;
            return self();
        }

        public T hungerRestore(int hungerRestore) {
            this.hungerRestore = hungerRestore;
            return self();
        }

        public T healRestore(int healRestore) {
            this.healRestore = healRestore;
            return self();
        }

        protected abstract T self();
        public abstract Creature build();
    }
}