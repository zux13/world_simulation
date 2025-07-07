package dev.zux13;

import dev.zux13.action.*;
import dev.zux13.renderer.Renderer;
import dev.zux13.map.WorldMap;

public class Simulation {
    private final WorldMap worldMap;
    private final Renderer renderer;
    private final Action[] initActions;
    private final Action[] turnActions;

    private volatile boolean running = true;
    private volatile boolean paused = false;
    private int turnCount = 0;

    public Simulation(WorldMap worldMap, Renderer renderer, Action[] initActions, Action[] turnActions) {
        this.worldMap = worldMap;
        this.renderer = renderer;
        this.initActions = initActions;
        this.turnActions = turnActions;
    }

    public void startSimulation() {
        for (Action action : initActions) {
            action.execute(worldMap);
        }

        new Thread(() -> {
            while (running) {
                if (!paused) {
                    nextTurn();
                }
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        }).start();
    }

    public void nextTurn() {
        turnCount++;

        for (Action action : turnActions) {
            action.execute(worldMap);
        }

        renderer.render(worldMap, turnCount);
    }

    public synchronized void pauseSimulation() {
        paused = true;
    }

    public synchronized void resumeSimulation() {
        paused = false;
    }

    public synchronized void stopSimulation() {
        running = false;
    }

    public int getTurnCount() {
        return turnCount;
    }
}
