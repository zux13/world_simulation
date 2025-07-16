package dev.zux13.command.menu.state;

import dev.zux13.command.menu.MenuStateManager;
import dev.zux13.settings.SimulationSettingsBuilder;
import dev.zux13.util.ConsoleUtils;

public class SetHeightState implements MenuState {

    private final MenuStateManager manager;
    private final SimulationSettingsBuilder settings;

    public SetHeightState(MenuStateManager manager, SimulationSettingsBuilder settings) {
        this.manager = manager;
        this.settings = settings;
    }

    @Override
    public void onEnter() {
        ConsoleUtils.clearConsole();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║           📏 Set Board Height                ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║ Enter a number (min: 10)                     ║");
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
        input = input.trim();
        if (input.equals("0")) {
            manager.setState(new SettingsMenuState(manager, settings));
            return;
        }

        try {
            int height = Integer.parseInt(input);
            settings.setBoardHeight(height);
            manager.setStatus("✅ Height set to " + height);
            manager.setState(new SettingsMenuState(manager, settings));
        } catch (NumberFormatException e) {
            manager.setStatus("❌ Invalid number.");
            onEnter();
        } catch (IllegalArgumentException e) {
            manager.setStatus("❌ " + e.getMessage());
            onEnter();
        }
    }
}
