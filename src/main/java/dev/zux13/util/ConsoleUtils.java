package dev.zux13.util;

public class ConsoleUtils {

    /**
 * Clears the console screen completely.
 */
public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}
