package dev.zux13.command.menu.state;

import dev.zux13.command.menu.MenuStateManager;
import dev.zux13.settings.SimulationSettingsBuilder;
import dev.zux13.theme.ThemeFactory;
import dev.zux13.util.ConsoleUtils;

import java.util.List;
import java.util.stream.IntStream;

public class ChooseThemeState implements MenuState {

    private final MenuStateManager manager;
    private final SimulationSettingsBuilder settings;
    private final List<String> availableThemes;

    public ChooseThemeState(MenuStateManager manager, SimulationSettingsBuilder settings) {
        this.manager = manager;
        this.settings = settings;
        this.availableThemes = ThemeFactory.getAvailableThemes();
    }

    @Override
    public void onEnter() {
        ConsoleUtils.clearConsole();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║               🎨 Choose Theme                ║");
        System.out.println("╠══════════════════════════════════════════════╣");

        IntStream.range(0, availableThemes.size())
                .forEach(i -> {
                    String themeName = availableThemes.get(i);
                    System.out.printf("║ %d. %-41s ║%n", i + 1, themeName);
                });

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
        if ("0".equals(input)) {
            manager.setState(new SettingsMenuState(manager, settings));
            return;
        }

        try {
            int choice = Integer.parseInt(input);
            if (choice > 0 && choice <= availableThemes.size()) {
                setTheme(availableThemes.get(choice - 1));
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            manager.setStatus("❌ Invalid input. Please enter a number from the list.");
            onEnter();
        }
    }

    private void setTheme(String themeName) {
        settings.setTheme(themeName);
        manager.setStatus("✅ Theme set to " + themeName);
        manager.setState(new SettingsMenuState(manager, settings));
    }
}
