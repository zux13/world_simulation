package dev.zux13.command.menu;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BackMenuItem implements MenuItem {

    private final MenuManager menuManager;

    @Override
    public String getLabel() {
        return "Back";
    }

    @Override
    public void execute() {
        menuManager.pop();
    }
}
