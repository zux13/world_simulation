package dev.zux13.input;

import java.util.Scanner;
import java.util.function.Consumer;

public class ConsoleInputHandler implements InputHandler {

    private final Scanner scanner = new Scanner(System.in);
    private final Consumer<String> commandConsumer;
    private volatile boolean running = true;

    public ConsoleInputHandler(Consumer<String> commandConsumer) {
        this.commandConsumer = commandConsumer;
    }

    @Override
    public void startListening() {
        new Thread(() -> {
            while (running) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine().trim();
                    commandConsumer.accept(line);
                }
            }
        }, "InputListenerThread").start();
    }

    @Override
    public void stopListening() {
        running = false;
    }
}
