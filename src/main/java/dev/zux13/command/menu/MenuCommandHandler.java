package dev.zux13.command.menu;

import dev.zux13.command.CommandHandler;
import dev.zux13.command.menu.state.MainMenuState;
import dev.zux13.settings.SimulationSettings;
import dev.zux13.settings.SimulationSettingsFactory;

import java.util.function.Consumer;

public class MenuCommandHandler implements CommandHandler {

    private final MenuStateManager stateManager;

    public MenuCommandHandler(Consumer<SimulationSettings> startSimulation,
                              Runnable exit,
                              SimulationSettingsFactory settingsFactory) {

        this.stateManager = new MenuStateManager();

        MainMenuState mainMenu = new MainMenuState(
                stateManager, startSimulation, exit, settingsFactory
        );
        stateManager.setMainMenuState(mainMenu);
        stateManager.setState(mainMenu);
    }

    @Override
    public void handle(String input) {
        stateManager.handleInput(input);
    }
}

