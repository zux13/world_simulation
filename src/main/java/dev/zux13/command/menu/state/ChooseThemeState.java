package dev.zux13.command.menu.state;

import dev.zux13.command.menu.MenuStateManager;
import dev.zux13.settings.SimulationSettingsBuilder;
import dev.zux13.theme.ThemeFactory;
import dev.zux13.util.ConsoleUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.IntStream;

@RequiredArgsConstructor
public class ChooseThemeState implements MenuState {

    private static final String BACK = "0";

    private final MenuStateManager manager;
    private final SimulationSettingsBuilder builder;
    private final List<String> availableThemes = ThemeFactory.getAvailableThemes();

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
        if (BACK.equals(input)) {
            manager.setState(new SettingsMenuState(manager, builder));
            return;
        }

        try {
            int choice = Integer.parseInt(input);
            if (choice > 0 && choice <= availableThemes.size()) {
                String themeName = availableThemes.get(choice - 1);
                builder.themeName(themeName);
                manager.setStatus("✅ Theme set to " + themeName);
                manager.setState(new SettingsMenuState(manager, builder));
            } else {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            manager.setStatus("❌ Invalid input. Please enter a number from the list.");
            onEnter();
        }
    }
}