package dev.zux13.command.menu.state;

public interface MenuState {
    void onEnter();
    void handleInput(String input);
}
