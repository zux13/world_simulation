package dev.zux13.command.menu;

import dev.zux13.command.menu.state.MainMenuState;
import dev.zux13.command.menu.state.MenuState;
import dev.zux13.settings.SimulationSettingsBuilder;
import lombok.Setter;

public class MenuStateManager {

    @Setter
    private MainMenuState mainMenuState;
    private MenuState currentState;
    private String statusMessage = "";

    public void returnToMainMenu() {
        setState(mainMenuState);
    }

    public void returnToMainMenu(SimulationSettingsBuilder builder) {
        mainMenuState.setSettingsBuilder(builder);
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
