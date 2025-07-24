package dev.zux13.command.menu.state;

import dev.zux13.command.menu.MenuStateManager;
import dev.zux13.settings.SimulationSettingsBuilder;
import dev.zux13.util.ConsoleUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SetHeightState implements MenuState {

    private static final String BACK = "0";

    private final MenuStateManager manager;
    private final SimulationSettingsBuilder builder;

    @Override
    public void onEnter() {
        ConsoleUtils.clearConsole();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║           📏 Set Board Height                ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        String text = String.format("Enter a number (min: %d, max: %d)",
                builder.getMinHeight(),
                builder.getMaxHeight());
        String padding = " ".repeat(44 - text.length());
        System.out.printf("║ %s%s ║%n", text, padding);
        System.out.printf("║ %s. Back                                      ║%n", BACK);
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
        if (input.equals(BACK)) {
            manager.setState(new SettingsMenuState(manager, builder));
            return;
        }

        try {
            int height = Integer.parseInt(input);
            builder.boardHeight(height);
            manager.setStatus("✅ Height set to " + height);
            manager.setState(new SettingsMenuState(manager, builder));
        } catch (NumberFormatException e) {
            manager.setStatus("❌ Invalid number.");
            onEnter();
        } catch (IllegalArgumentException e) {
            manager.setStatus("❌ " + e.getMessage());
            onEnter();
        }
    }
}
