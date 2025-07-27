package dev.zux13.command.menu;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

@NoArgsConstructor
public class MenuManager {

    public final static String EXIT_COMMAND = "0";

    private final Stack<MenuScreen> screenStack = new Stack<>();
    private final MenuInputHandler inputHandler = new MenuInputHandler(this);
    private final Deque<String> messages = new LinkedList<>();

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

    public void addMessage(String message) {
        messages.add(message);
    }

    public List<String> getMessages() {
        return List.copyOf(messages);
    }

    public void clearMessages() {
        messages.clear();
    }
}
