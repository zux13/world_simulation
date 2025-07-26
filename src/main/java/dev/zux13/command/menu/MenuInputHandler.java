package dev.zux13.command.menu;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MenuInputHandler {

    private final static String EXIT_COMMAND = "0";
    private final MenuManager menuManager;

    public void handleInput(String input) {
        MenuScreen currentScreen = menuManager.getCurrentScreen();
        if (currentScreen == null) {
            return;
        }

        String trimmedInput = input.trim();

        if (EXIT_COMMAND.equals(trimmedInput) && currentScreen.getExitItem() != null) {
            currentScreen.getExitItem().execute();
            return;
        }

        if (currentScreen instanceof InputScreen inputScreen) {
            handleInputScreen(inputScreen, trimmedInput);
        } else {
            handleMenuScreen(currentScreen, trimmedInput);
        }
    }

    private void handleInputScreen(InputScreen screen, String input) {
        try {
            screen.getInputConsumer().accept(input);
            menuManager.pop();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void handleMenuScreen(MenuScreen screen, String input) {
        try {
            int choice = Integer.parseInt(input);
            if (choice > 0 && choice <= screen.getItems().size()) {
                screen.getItems().get(choice - 1).execute();
            }
        } catch (NumberFormatException ignored) {
        }
    }
}
