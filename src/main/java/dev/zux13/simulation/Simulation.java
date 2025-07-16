package dev.zux13.simulation;

import dev.zux13.action.*;
import dev.zux13.board.Board;
import dev.zux13.settings.SimulationSettings;
import dev.zux13.util.ThreadUtils;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Simulation {
    private final Board board;
    private final TurnCounter turnCounter;
    private final SimulationSettings settings;

    private final Action[] initActions;
    private final Action[] turnActions;

    private volatile boolean running = true;
    private volatile boolean paused = false;
    private volatile boolean isWaitingOnPause = false;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition pauseCondition = lock.newCondition();

    public Simulation(Board board,
                      TurnCounter turnCounter,
                      SimulationSettings settings,
                      Action[] initActions,
                      Action[] turnActions) {
        this.board = board;
        this.turnCounter = turnCounter;
        this.settings = settings;
        this.initActions = initActions;
        this.turnActions = turnActions;
    }

    public void startSimulation() {

        for (Action action : initActions) {
            action.execute(board, settings);
        }

        while (running) {
            waitIfPaused();
            if (!running) {
                break;
            }

            nextTurn();
            if(ThreadUtils.sleepSilently(50)) {
                break;
            }
        }
    }

    public void nextTurn() {

        turnCounter.nextTurn();

        for (Action action : turnActions) {
            action.execute(board, settings);
        }
    }

    private void waitIfPaused() {
        lock.lock();
        try {
            while (paused && running) {
                isWaitingOnPause = true;
                pauseCondition.await();
                isWaitingOnPause = false;
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            lock.unlock();
        }
    }

    public void pauseSimulation() {
        lock.lock();
        try {
            paused = true;
        } finally {
            lock.unlock();
        }
    }

    public void resumeSimulation() {
        lock.lock();
        try {
            paused = false;
            pauseCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public void stopSimulation() {
        lock.lock();
        try {
            running = false;
            pauseCondition.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public boolean isPaused() {
        return paused;
    }

    public boolean isActuallyWaitingOnPause() {
        return isWaitingOnPause;
    }
}