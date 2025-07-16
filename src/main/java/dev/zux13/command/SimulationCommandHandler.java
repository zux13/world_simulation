package dev.zux13.command;

import dev.zux13.simulation.Simulation;
import dev.zux13.util.ThreadUtils;

public class SimulationCommandHandler implements CommandHandler {

    private final Simulation simulation;
    private final Runnable onStop;

    public SimulationCommandHandler(Simulation simulation, Runnable onStop) {
        this.simulation = simulation;
        this.onStop = onStop;
    }

    @Override
    public void handle(String input) {
        input = input.trim();
        if (simulation.isPaused()) {
            handleCommand(input);
        } else {
            tryPauseSimulation();
        }
    }

    private void tryPauseSimulation() {
        simulation.pauseSimulation();

        while (!simulation.isActuallyWaitingOnPause()) {
            if (ThreadUtils.sleepSilently(50)) {
                break;
            }
        }

        printAvailableCommands();
    }

    private void handleCommand(String input) {
        switch (input) {
            case "1" -> {
                simulation.nextTurn();
                System.out.println("[✅ Next turn executed]");
                printAvailableCommands();
            }
            case "2" -> {
                simulation.resumeSimulation();
                System.out.println("[▶ Simulation resumed]");
            }
            case "0" -> {
                simulation.stopSimulation();
                System.out.println("[🛑 Simulation stopped]");
                onStop.run();
            }
            default -> {
                System.out.println("[❌ Unknown command]");
                printAvailableCommands();
            }
        }
    }

    private void printAvailableCommands() {
        System.out.print("""
                        [⏸ Paused] 1: Next turn | 2: Resume | 0: Stop
                        """);
        System.out.print("> ");
    }
}

