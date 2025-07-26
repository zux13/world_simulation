package dev.zux13.command.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Stack;

@NoArgsConstructor
public class MenuManager {

    private final Stack<MenuScreen> screenStack = new Stack<>();
    private final MenuInputHandler inputHandler = new MenuInputHandler(this);

    @Getter
    private boolean running = true;

    public void push(MenuScreen screen) {
        if (screen != null) {
            screenStack.push(screen);
        }
    }

    public void pop() {
        if (screenStack.size() > 1) {
            screenStack.pop();
        }
    }

    public void stop() {
        this.running = false;
    }

    public MenuScreen getCurrentScreen() {
        return screenStack.peek();
    }

    public void handleInput(String input) {
        if (!screenStack.isEmpty() && running) {
            inputHandler.handleInput(input);
        }
    }
}
