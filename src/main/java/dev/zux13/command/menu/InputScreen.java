package dev.zux13.command.menu;

import lombok.Getter;

import java.util.function.Consumer;

@Getter
public class InputScreen extends MenuScreen {

    private final Consumer<String> inputConsumer;

    public InputScreen(Consumer<String> inputConsumer, String title, String icon, MenuItem backItem, String infoText) {
        super(title, icon, backItem, infoText);
        this.inputConsumer = inputConsumer;
    }
}
