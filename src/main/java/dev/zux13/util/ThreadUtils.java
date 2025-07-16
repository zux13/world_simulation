package dev.zux13.util;

public class ThreadUtils {

    /**
     * Пауза в миллисекундах с безопасной обработкой InterruptedException.
     * Если поток прерывается, флаг прерывания восстанавливается.
     *
     * @param millis длительность сна в миллисекундах
     * @return true, если сон был прерван
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
