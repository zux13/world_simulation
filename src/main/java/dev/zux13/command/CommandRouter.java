package dev.zux13.command;

import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
public class CommandRouter {
    private CommandHandler handler;

    public void route(String input) {
        if (handler != null) {
            handler.handle(input);
        }
    }
}
