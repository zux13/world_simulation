package dev.zux13.command.menu;

import dev.zux13.util.ConsoleUtils;

public class MenuRenderer {

    private static final int SCREEN_WIDTH = 44;

    public void render(MenuScreen screen) {
        ConsoleUtils.clearConsole();

        String fullTitle = (screen.getIcon() != null ? screen.getIcon() + " " : "") + screen.getTitle();
        printTopBorder();
        printCenteredLine(fullTitle);
        printSeparator();

        if (screen.getInfoText() != null && !screen.getInfoText().isEmpty()) {
            printLeftAlignedLine(screen.getInfoText());
            printSeparator();
        }

        if (screen.getItems() != null && !screen.getItems().isEmpty()) {
            int index = 1;
            for (MenuItem item : screen.getItems()) {
                printLeftAlignedLine(index++ + ". " + item.getLabel());
            }
        }

        if (screen.getExitItem() != null) {
            printLeftAlignedLine("0. " + screen.getExitItem().getLabel());
        }

        printBottomBorder();
        System.out.print("> ");
        System.out.flush();
    }

    private void printTopBorder() {
        System.out.println("╔" + "═".repeat(SCREEN_WIDTH + 2) + "╗");
    }

    private void printSeparator() {
        System.out.println("╠" + "═".repeat(SCREEN_WIDTH + 2) + "╣");
    }

    private void printBottomBorder() {
        System.out.println("╚" + "═".repeat(SCREEN_WIDTH + 2) + "╝");
    }

    private void printCenteredLine(String text) {
        String centered = ConsoleUtils.center(text, SCREEN_WIDTH);
        System.out.printf("║ %s ║%n", centered);
    }

    private void printLeftAlignedLine(String text) {
        if (ConsoleUtils.getVisualLength(text) > SCREEN_WIDTH) {
            text = truncateVisual(text);
        }
        System.out.printf("║ %-"+ SCREEN_WIDTH +"s ║%n", text);
    }

    private String truncateVisual(String text) {
        StringBuilder result = new StringBuilder();
        int visualLength = 0;
        for (int i = 0; i < text.length(); ) {
            int codePoint = text.codePointAt(i);
            int charWidth = ConsoleUtils.isZeroWidthMark(codePoint) ? 0 :
                    ConsoleUtils.isWideCharacter(codePoint) ? 2 : 1;

            if (visualLength + charWidth > SCREEN_WIDTH) break;

            result.appendCodePoint(codePoint);
            visualLength += charWidth;
            i += Character.charCount(codePoint);
        }
        return result.toString();
    }
}
