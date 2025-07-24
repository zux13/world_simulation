package dev.zux13.util;

public final class ThreadUtils {

    private ThreadUtils() {
    }

    /**
 * Pauses for a specified number of milliseconds with safe handling of InterruptedException.
 * If the thread is interrupted, the interrupt flag is restored.
 *
 * @param millis the duration of the sleep in milliseconds
 * @return true if the sleep was interrupted
 */
public static boolean sleepSilently(long millis) {
        try {
            Thread.sleep(millis);
            return false;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return true;
        }
    }
}
