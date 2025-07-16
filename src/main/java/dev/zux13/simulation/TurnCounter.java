package dev.zux13.simulation;

import java.util.concurrent.atomic.AtomicInteger;

public class TurnCounter {

    private final AtomicInteger turnCounter = new AtomicInteger(0);

    public int nextTurn() {
        return turnCounter.incrementAndGet();
    }

    public int getCurrentTurn() {
        return turnCounter.get();
    }

}
