package dev.zux13.command.menu;

import dev.zux13.settings.SimulationSettingsBuilder;
import dev.zux13.theme.ThemeFactory;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class MenuFactory {

    private final MenuManager menuManager = new MenuManager();
    private final SimulationSettingsBuilder settingsBuilder;
    private final Runnable onStartSimulation;
    private final Runnable onExit;

    private record SettingOption(String name, String icon, Supplier<MenuScreen> screenSupplier) {}

    public MenuManager createMenu() {
        MenuScreen mainMenu = createMainMenu();
        menuManager.push(mainMenu);
        return menuManager;
    }

    private MenuScreen createMainMenu() {
        Runnable stopAndStart = () -> {
            onStartSimulation.run();
            menuManager.stop();
        };

        Runnable exit = () -> {
            onExit.run();
            menuManager.stop();
        };

        List<MenuItem> items = List.of(
                new FunctionalMenuItem("Start Simulation", stopAndStart),
                new SubMenuItem("Settings", menuManager, this::createSettingsMenu)
        );
        MenuItem exitItem = new FunctionalMenuItem("Exit", exit);

        return new MenuScreen("World simulation", "🗺️", items, exitItem);
    }

    private MenuScreen createSettingsMenu() {
        List<SettingOption> settingOptions = List.of(
                new SettingOption("Set Board Width", "📐", this::createSetWidthMenu),
                new SettingOption("Set Board Height", "📏", this::createSetHeightMenu),
                new SettingOption("Choose Theme", "🎨", this::createThemeSelectionMenu)
        );

        List<MenuItem> items = settingOptions.stream()
                .map(option -> new SubMenuItem(option.name(), menuManager, option.screenSupplier()))
                .collect(Collectors.toList());

        return new MenuScreen("Settings", "🔧", items, new BackMenuItem(menuManager));
    }

    private MenuScreen createNumericInputScreen(String title, String icon, String rangeInfo, Consumer<Integer> setter) {
        return new InputScreen(input -> {
            try {
                int value = Integer.parseInt(input);
                setter.accept(value);
                System.out.println(title + " set to " + value);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
            },title, icon, new BackMenuItem(menuManager), rangeInfo);
    }

    private MenuScreen createSetWidthMenu() {
        return createNumericInputScreen(
                "Enter Board Width",
                "📐",
                formatRange(settingsBuilder.getMinWidth(), settingsBuilder.getMaxWidth()),
                settingsBuilder::boardWidth
        );
    }

    private MenuScreen createSetHeightMenu() {
        return createNumericInputScreen(
                "Enter Board Height",
                "📏",
                formatRange(settingsBuilder.getMinHeight(), settingsBuilder.getMaxHeight()),
                settingsBuilder::boardHeight
        );
    }

    private MenuScreen createThemeSelectionMenu() {
        List<MenuItem> themeItems = ThemeFactory.getAvailableThemes().stream()
                .map(themeName -> new FunctionalMenuItem(themeName, () -> {
                    settingsBuilder.themeName(themeName);
                    System.out.println("Theme set to " + themeName);
                    menuManager.pop();
                }))
                .collect(Collectors.toList());

        return new MenuScreen("Choose Theme", "🎨", themeItems, new BackMenuItem(menuManager));
    }

    private String formatRange(int min, int max) {
        return "Min: %d, Max: %d".formatted(min, max);
    }
}
