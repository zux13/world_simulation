package dev.zux13.simulation;

import dev.zux13.command.CommandRouter;
import dev.zux13.command.menu.MenuCommandHandler;
import dev.zux13.command.SimulationCommandHandler;
import dev.zux13.input.ConsoleInputHandler;
import dev.zux13.input.InputHandler;
import dev.zux13.settings.SimulationProperties;

public class SimulationLauncher {

    public void launch() {
        CommandRouter router = new CommandRouter();
        InputHandler inputHandler = new ConsoleInputHandler(router::route);
        SimulationProperties properties = new SimulationProperties();

        router.setHandler(new MenuCommandHandler(
                settings -> {
                    Simulation simulation = SimulationFactory.create(settings);
                    router.setHandler(new SimulationCommandHandler(simulation, inputHandler::stopListening));
                    new Thread(simulation::startSimulation).start();
                },
                () -> {
                    System.out.println("Bye bye!");
                    inputHandler.stopListening();
                },
                properties
        ));

        inputHandler.startListening();
    }
}
