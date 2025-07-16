package dev.zux13.command.menu.state;

import dev.zux13.command.menu.MenuStateManager;
import dev.zux13.settings.SimulationSettingsBuilder;
import dev.zux13.util.ConsoleUtils;

public class SettingsMenuState implements MenuState {

    private final MenuStateManager manager;
    private final SimulationSettingsBuilder settings;

    public SettingsMenuState(MenuStateManager manager, SimulationSettingsBuilder settings) {
        this.manager = manager;
        this.settings = settings;
    }

    @Override
    public void onEnter() {
        ConsoleUtils.clearConsole();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║                ⚙ Settings                    ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║ 1. Set board width                           ║");
        System.out.println("║ 2. Set board height                          ║");
        System.out.println("║ 3. Choose theme                              ║");
        System.out.println("║ 0. Back                                      ║");
        System.out.println("╚══════════════════════════════════════════════╝");
        if (!manager.getStatus().isBlank()) {
            System.out.println(manager.getStatus());
            manager.clearStatus();
        }
        System.out.print("> ");
    }

    @Override
    public void handleInput(String input) {
        switch (input.trim()) {
            case "0" -> manager.returnToMainMenu();
            case "1" -> manager.setState(new SetWidthState(manager, settings));
            case "2" -> manager.setState(new SetHeightState(manager, settings));
            case "3" -> manager.setState(new ChooseThemeState(manager, settings));
            default -> {
                manager.setStatus("❌ Invalid input.");
                onEnter();
            }
        }
    }
}
