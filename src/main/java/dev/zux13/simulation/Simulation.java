package dev.zux13.simulation;

import dev.zux13.board.BoardService;
import dev.zux13.task.SimulationTask;
import dev.zux13.board.Board;
import dev.zux13.settings.SimulationSettings;
import dev.zux13.util.ThreadUtils;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

@RequiredArgsConstructor
public class Simulation {
    private final Board board;
    private final BoardService boardService;
    private final TurnCounter turnCounter;
    private final SimulationSettings settings;

    private final SimulationTask[] initTasks;
    private final SimulationTask[] turnTasks;

    @Getter
    private volatile boolean paused = false;
    private volatile boolean running = true;
    private volatile boolean isWaitingOnPause = false;

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition pauseCondition = lock.newCondition();

    public void startSimulation() {

        for (SimulationTask task : initTasks) {
            task.execute(board, boardService, settings);
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
        for (SimulationTask task : turnTasks) {
            task.execute(board, boardService, settings);
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

    public boolean isActuallyWaitingOnPause() {
        return isWaitingOnPause;
    }
}