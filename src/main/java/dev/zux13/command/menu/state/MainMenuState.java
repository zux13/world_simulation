package dev.zux13.command.menu.state;

import dev.zux13.command.menu.MenuStateManager;
import dev.zux13.settings.SimulationSettings;
import dev.zux13.settings.SimulationSettingsBuilder;
import dev.zux13.settings.SimulationSettingsFactory;
import dev.zux13.util.ConsoleUtils;
import lombok.Setter;

import java.util.function.Consumer;

public class MainMenuState implements MenuState {

    private final static String EXIT = "0";
    private final static String START = "1";
    private final static String SETTINGS = "2";

    private final MenuStateManager manager;
    private final Consumer<SimulationSettings> startSimulation;
    private final Runnable exit;

    @Setter
    private SimulationSettingsBuilder settingsBuilder;

    public MainMenuState(MenuStateManager manager,
                         Consumer<SimulationSettings> startSimulation,
                         Runnable exit,
                         SimulationSettingsFactory settingsFactory) {
        this.manager = manager;
        this.startSimulation = startSimulation;
        this.exit = exit;
        this.settingsBuilder = settingsFactory.createBuilderFromDefaults();
    }

    @Override
    public void onEnter() {
        ConsoleUtils.clearConsole();
        System.out.printf("""
        ╔══════════════════════════════════════════════╗
        ║            🌍 World Simulation               ║
        ╠══════════════════════════════════════════════╣
        ║ Press [Enter] anytime to pause simulation.   ║
        ╠══════════════════════════════════════════════╣
        ║ %s. Start Simulation                          ║
        ║ %s. Settings                                  ║
        ║ %s. Exit                                      ║
        ╚══════════════════════════════════════════════╝
        """, START, SETTINGS, EXIT);
        System.out.print("> ");
    }

    @Override
    public void handleInput(String input) {
        switch (input.trim()) {
            case START -> startSimulation.accept(this.settingsBuilder.build());
            case SETTINGS -> manager.setState(new SettingsMenuState(manager, this.settingsBuilder));
            case EXIT -> exit.run();
            default -> {
                manager.setStatus("❌ Invalid input.");
                onEnter();
            }
        }
    }
}