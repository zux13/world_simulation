package dev.zux13.command.menu.state;

import dev.zux13.command.menu.MenuStateManager;
import dev.zux13.settings.SimulationSettingsBuilder;
import dev.zux13.util.ConsoleUtils;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SettingsMenuState implements MenuState {

    private static final String BACK = "0";
    private static final String SET_WIDTH = "1";
    private static final String SET_HEIGHT = "2";
    private static final String CHOOSE_THEME = "3";

    private final MenuStateManager manager;
    private final SimulationSettingsBuilder builder;

    @Override
    public void onEnter() {
        ConsoleUtils.clearConsole();
        System.out.println("╔══════════════════════════════════════════════╗");
        System.out.println("║                ⚙ Settings                    ║");
        System.out.println("╠══════════════════════════════════════════════╣");
        System.out.printf("║ %s. Set board width                           ║%n", SET_WIDTH);
        System.out.printf("║ %s. Set board height                          ║%n", SET_HEIGHT);
        System.out.printf("║ %s. Choose theme                              ║%n", CHOOSE_THEME);
        System.out.printf("║ %s. Back to Main Menu (and save changes)      ║%n", BACK);
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
            case BACK -> manager.returnToMainMenu(builder);
            case SET_WIDTH -> manager.setState(new SetWidthState(manager, builder));
            case SET_HEIGHT -> manager.setState(new SetHeightState(manager, builder));
            case CHOOSE_THEME -> manager.setState(new ChooseThemeState(manager, builder));
            default -> {
                manager.setStatus("❌ Invalid input.");
                onEnter();
            }
        }
    }
}