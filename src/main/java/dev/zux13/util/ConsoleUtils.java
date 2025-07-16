package dev.zux13.util;

public class ConsoleUtils {

    /**
     * Полная очистка экрана (если нужно полностью очистить консоль).
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
