package dev.zux13.command.menu;

import dev.zux13.command.CommandHandler;

public class MenuCommandHandler implements CommandHandler {

    private final MenuManager menuManager;
    private final MenuRenderer menuRenderer;

    public MenuCommandHandler(MenuManager menuManager) {
        this.menuManager = menuManager;
        this.menuRenderer = new MenuRenderer();
        menuRenderer.render(menuManager.getCurrentScreen());
    }

    @Override
    public void handle(String command) {
        menuManager.handleInput(command);
        if (menuManager.isRunning()) {
            menuRenderer.render(menuManager.getCurrentScreen());
        }
    }
}
