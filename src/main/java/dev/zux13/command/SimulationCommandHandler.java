package dev.zux13.command;

import dev.zux13.simulation.Simulation;
import dev.zux13.util.ThreadUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SimulationCommandHandler implements CommandHandler {

    private static final String STOP = "0";
    private static final String NEXT_TURN = "1";
    private static final String RESUME = "2";

    private final Simulation simulation;
    private final Runnable onStop;

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
            case NEXT_TURN -> {
                simulation.nextTurn();
                System.out.println("[✅ Next turn executed]");
                printAvailableCommands();
            }
            case RESUME -> {
                simulation.resumeSimulation();
                System.out.println("[▶ Simulation resumed]");
            }
            case STOP -> {
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
        System.out.printf("""
                        [⏸ Paused] %s: Next turn | %s: Resume | %s: Stop
                        """, NEXT_TURN, RESUME, STOP);
        System.out.print("> ");
    }
}

