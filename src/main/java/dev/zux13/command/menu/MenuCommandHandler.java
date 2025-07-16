package dev.zux13.command.menu;

import dev.zux13.command.CommandHandler;
import dev.zux13.command.menu.state.MainMenuState;
import dev.zux13.settings.SimulationProperties;
import dev.zux13.settings.SimulationSettings;
import dev.zux13.settings.SimulationSettingsBuilder;

import java.util.function.Consumer;

public class MenuCommandHandler implements CommandHandler {

    private final MenuStateManager stateManager;

    public MenuCommandHandler(Consumer<SimulationSettings> startSimulation, Runnable exit, SimulationProperties properties) {
        this.stateManager = new MenuStateManager();

        MainMenuState mainMenu = new MainMenuState(
                stateManager, startSimulation, exit, new SimulationSettingsBuilder(properties)
        );
        stateManager.setMainMenuState(mainMenu);
        stateManager.setState(mainMenu);
    }

    @Override
    public void handle(String input) {
        stateManager.handleInput(input);
    }
}

