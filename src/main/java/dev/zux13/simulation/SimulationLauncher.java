package dev.zux13.simulation;

import dev.zux13.board.Board;
import dev.zux13.command.CommandRouter;
import dev.zux13.command.menu.MenuCommandHandler;
import dev.zux13.command.SimulationCommandHandler;
import dev.zux13.event.EventBus;
import dev.zux13.input.ConsoleInputHandler;
import dev.zux13.input.InputHandler;
import dev.zux13.settings.SimulationSettingsFactory;

public class SimulationLauncher {

    public void launch() {
        CommandRouter router = new CommandRouter();
        InputHandler inputHandler = new ConsoleInputHandler(router::route);
        SimulationSettingsFactory settingsFactory = new SimulationSettingsFactory();

        router.setHandler(new MenuCommandHandler(
                settings -> {
                    Board board = new Board(settings.boardWidth(), settings.boardHeight());
                    TurnCounter counter = new TurnCounter();
                    EventBus eventBus = new EventBus();
                    Simulation simulation = SimulationFactory.create(board, counter, settings, eventBus);
                    router.setHandler(new SimulationCommandHandler(simulation, inputHandler::stopListening));
                    new Thread(() -> {
                        SimulationBootstrapper.bootstrap(board, counter, settings, eventBus);
                        simulation.startSimulation();
                    }).start();
                },
                () -> {
                    System.out.println("Exiting...");
                    inputHandler.stopListening();
                },
                settingsFactory
        ));

        inputHandler.startListening();
    }
}
