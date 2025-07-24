package dev.zux13.input;

import lombok.RequiredArgsConstructor;

import java.util.Scanner;
import java.util.function.Consumer;

@RequiredArgsConstructor
public class ConsoleInputHandler implements InputHandler {

    private final Consumer<String> commandConsumer;
    private final Scanner scanner = new Scanner(System.in);
    private volatile boolean running = true;

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
