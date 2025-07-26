package dev.zux13.util;

public final class ConsoleUtils {

    private ConsoleUtils() {
    }

    /**
     * Clears the console screen completely.
     */
    public static void clearConsole() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }

    public static int getVisualLength(String str) {
        if (str == null || str.isEmpty()) {
            return 0;
        }

        int length = 0;
        for (int i = 0; i < str.length(); ) {
            int codePoint = str.codePointAt(i);

            if (!isZeroWidthMark(codePoint) && isWideCharacter(codePoint)) {
                length += 2;
            } else if (!isZeroWidthMark(codePoint)) {
                length += 1;
            }

            i += Character.charCount(codePoint);
        }

        return length;
    }

    public static String center(String text, int width) {
        if (text == null) return null;

        int visualLength = getVisualLength(text);
        if (visualLength >= width) {
            return text;
        }

        int leftPadding = (width - visualLength) / 2;
        int rightPadding = width - visualLength - leftPadding;

        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }

    public static boolean isZeroWidthMark(int codePoint) {
        int type = Character.getType(codePoint);
        return type == Character.NON_SPACING_MARK ||
                type == Character.COMBINING_SPACING_MARK ||
                type == Character.ENCLOSING_MARK;
    }

    public static boolean isWideCharacter(int codePoint) {
        return Character.getType(codePoint) == Character.OTHER_SYMBOL ||
                (codePoint >= 0x2E80 && codePoint <= 0xD7AF) ||   // CJK Radicals
                (codePoint >= 0xF900 && codePoint <= 0xFAFF) ||   // CJK Compatibility Ideographs
                (codePoint >= 0xFE30 && codePoint <= 0xFE4F) ||   // CJK Compatibility Forms
                (codePoint >= 0xFF00 && codePoint <= 0xFFEF);     // Full-width forms
    }

}
