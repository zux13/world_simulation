package dev.zux13.simulation;

import dev.zux13.board.Board;
import dev.zux13.board.BoardService;
import dev.zux13.command.CommandRouter;
import dev.zux13.command.menu.MenuFactory;
import dev.zux13.command.menu.MenuManager;
import dev.zux13.command.menu.MenuCommandHandler;
import dev.zux13.command.SimulationCommandHandler;
import dev.zux13.event.EventBus;
import dev.zux13.input.ConsoleInputHandler;
import dev.zux13.input.InputHandler;
import dev.zux13.settings.SimulationSettings;
import dev.zux13.settings.SimulationSettingsBuilder;
import dev.zux13.settings.SimulationSettingsFactory;

public class SimulationLauncher {

    public void launch() {
        CommandRouter router = new CommandRouter();
        InputHandler inputHandler = new ConsoleInputHandler(router::route);
        SimulationSettingsBuilder settingsBuilder = new SimulationSettingsFactory().createBuilderFromDefaults();

        Runnable onStartSimulation = () -> {
            SimulationSettings settings = settingsBuilder.build();
            Board board = new Board(settings.boardWidth(), settings.boardHeight());
            BoardService boardService = new BoardService(board);
            TurnCounter counter = new TurnCounter();
            EventBus eventBus = new EventBus();
            Simulation simulation = SimulationFactory.create(boardService, counter, settings, eventBus);
            router.setHandler(new SimulationCommandHandler(simulation, inputHandler::stopListening));
            new Thread(() -> {
                SimulationBootstrapper.bootstrap(boardService, counter, settings, eventBus);
                simulation.startSimulation();
            }).start();
        };

        Runnable onExit = () -> {
            System.out.println("Exiting...");
            inputHandler.stopListening();
        };

        MenuFactory menuFactory = new MenuFactory(settingsBuilder, onStartSimulation, onExit);
        MenuManager menuManager = menuFactory.createMenu();
        router.setHandler(new MenuCommandHandler(menuManager));

        inputHandler.startListening();
    }
}
