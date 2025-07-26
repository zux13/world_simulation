package dev.zux13.command.menu;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class FunctionalMenuItem implements MenuItem {

    private final String label;
    private final Runnable action;

    @Override
    public String getLabel() {
        return label;
    }

    @Override
    public void execute() {
        action.run();
    }
}
