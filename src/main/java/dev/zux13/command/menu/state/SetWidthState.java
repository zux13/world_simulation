package dev.zux13.command.menu.state;

import dev.zux13.command.menu.MenuStateManager;
import dev.zux13.settings.SimulationSettingsBuilder;
import dev.zux13.util.ConsoleUtils;

public class SetWidthState implements MenuState {

    private final MenuStateManager manager;
    private final SimulationSettingsBuilder settings;

    public SetWidthState(MenuStateManager manager, SimulationSettingsBuilder settings) {
        this.manager = manager;
        this.settings = settings;
    }

    @Override
    public void onEnter() {
        ConsoleUtils.clearConsole();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║            📏 Set Board Width                ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        String text = String.format("Enter a number (min: %d)", settings.getMinWidth());
        String padding = " ".repeat(44 - text.length());
        System.out.printf("║ %s%s ║%n", text, padding);
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
            int width = Integer.parseInt(input);
            settings.setBoardWidth(width);
            manager.setStatus("✅ Width set to " + width);
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
