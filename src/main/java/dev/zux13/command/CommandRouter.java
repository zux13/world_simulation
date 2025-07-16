package dev.zux13.command;

public class CommandRouter {
    private CommandHandler currentHandler;

    public void setHandler(CommandHandler handler) {
        this.currentHandler = handler;
    }

    public void route(String input) {
        if (currentHandler != null) {
            currentHandler.handle(input);
        }
    }
}
