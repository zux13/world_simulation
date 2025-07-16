package dev.zux13.command.menu;

import dev.zux13.command.menu.state.MainMenuState;
import dev.zux13.command.menu.state.MenuState;

public class MenuStateManager {

    private MenuState currentState;
    private MainMenuState mainMenuState;
    private String statusMessage = "";

    public void setMainMenuState(MainMenuState state) {
        this.mainMenuState = state;
    }

    public void returnToMainMenu() {
        setState(mainMenuState);
    }

    public void setState(MenuState newState) {
        this.currentState = newState;
        newState.onEnter();
    }

    public void handleInput(String input) {
        currentState.handleInput(input);
    }

    public void setStatus(String message) {
        this.statusMessage = message;
    }

    public void clearStatus() {
        this.statusMessage = "";
    }

    public String getStatus() {
        return statusMessage;
    }
}
