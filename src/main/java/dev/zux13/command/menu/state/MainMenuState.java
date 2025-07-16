package dev.zux13.command.menu.state;

import dev.zux13.command.menu.MenuStateManager;
import dev.zux13.settings.SimulationSettings;
import dev.zux13.settings.SimulationSettingsBuilder;
import dev.zux13.util.ConsoleUtils;

import java.util.function.Consumer;

public class MainMenuState implements MenuState {

    private final MenuStateManager manager;
    private final Consumer<SimulationSettings> startSimulation;
    private final Runnable exit;
    private final SimulationSettingsBuilder settingsBuilder;

    public MainMenuState(MenuStateManager manager,
                         Consumer<SimulationSettings> startSimulation,
                         Runnable exit,
                         SimulationSettingsBuilder settingsBuilder) {
        this.manager = manager;
        this.startSimulation = startSimulation;
        this.exit = exit;
        this.settingsBuilder = settingsBuilder;
    }

    @Override
    public void onEnter() {
        ConsoleUtils.clearConsole();
        System.out.print("""
        ╔══════════════════════════════════════════════╗
        ║            🌍 World Simulation               ║
        ╠══════════════════════════════════════════════╣
        ║ Press [Enter] anytime to pause simulation.   ║
        ╠══════════════════════════════════════════════╣
        ║ 1. Start Simulation                          ║
        ║ 2. Settings                                  ║
        ║ 0. Exit                                      ║
        ╚══════════════════════════════════════════════╝
        """);
        System.out.print("> ");
    }

    @Override
    public void handleInput(String input) {
        switch (input.trim()) {
            case "1" -> startSimulation.accept(settingsBuilder.build());
            case "2" -> manager.setState(new SettingsMenuState(manager, settingsBuilder));
            case "0" -> exit.run();
            default -> {
                manager.setStatus("❌ Invalid input.");
                onEnter();
            }
        }
    }
}
