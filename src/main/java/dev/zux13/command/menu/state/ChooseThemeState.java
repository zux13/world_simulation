package dev.zux13.command.menu.state;

import dev.zux13.command.menu.MenuStateManager;
import dev.zux13.settings.SimulationSettingsBuilder;
import dev.zux13.theme.ThemeType;
import dev.zux13.util.ConsoleUtils;

public class ChooseThemeState implements MenuState {

    private final MenuStateManager manager;
    private final SimulationSettingsBuilder settings;

    public ChooseThemeState(MenuStateManager manager, SimulationSettingsBuilder settings) {
        this.manager = manager;
        this.settings = settings;
    }

    @Override
    public void onEnter() {
        ConsoleUtils.clearConsole();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║              🎨 Choose ThemeType                 ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.println("║ 1. Forest   🌲                               ║");
        System.out.println("║ 2. Desert   🏜️                               ║");
        System.out.println("║ 3. Snow     ❄️                               ║");
        System.out.println("║ 4. Steppe   🌾                               ║");
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
        switch (input) {
            case "0" -> manager.setState(new SettingsMenuState(manager, settings));
            case "1" -> setTheme(ThemeType.FOREST);
            case "2" -> setTheme(ThemeType.DESERT);
            case "3" -> setTheme(ThemeType.SNOW);
            case "4" -> setTheme(ThemeType.STEPPE);
            default -> {
                manager.setStatus("❌ Invalid input.");
                onEnter();
            }
        }
    }

    private void setTheme(ThemeType themeType) {
        settings.setTheme(themeType);
        manager.setStatus("✅ ThemeType set to " + themeType.getDisplayName());
        manager.setState(new SettingsMenuState(manager, settings));
    }
}
