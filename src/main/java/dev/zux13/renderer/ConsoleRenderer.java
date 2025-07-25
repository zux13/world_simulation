package dev.zux13.renderer;

import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.Entity;
import dev.zux13.event.EventBus;
import dev.zux13.event.EventSubscriber;
import dev.zux13.event.Priority;
import dev.zux13.event.events.SimulationMoveExecutedEvent;
import dev.zux13.logger.EventLogger;
import dev.zux13.simulation.TurnCounter;
import dev.zux13.settings.SimulationSettings;
import dev.zux13.theme.Theme;
import dev.zux13.util.ConsoleUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ConsoleRenderer implements Renderer, EventSubscriber {

    private static final int CHAR_WIDTH_PER_CELL = 2;
    private static final String SEPARATOR = "  | ";

    private final SimulationSettings settings;
    private final BoardService boardService;
    private final TurnCounter turnCounter;
    private final EventLogger logger;
    private final Theme theme;

    @Override
    public void subscribeToEvents(EventBus eventBus) {
        eventBus.subscribe(SimulationMoveExecutedEvent.class, this::onMoveExecuted, Priority.LOW);
    }

    private void onMoveExecuted(SimulationMoveExecutedEvent event) {
        render();
    }

    @Override
    public void render() {
        ConsoleUtils.clearConsole();

        int boardWidth = boardService.getWidth();
        int boardHeight = boardService.getHeight();
        int charWidth = getEntityRowWidth(boardWidth);
        int logWidth = settings.rendererLogWidth();
        int totalWidth = getTotalWidth(charWidth, logWidth);

        List<String> logs = logger.getLogSnapshot();
        int turn = turnCounter.getCurrentTurn();

        System.out.println(formatTurnTitle(totalWidth, turn));
        System.out.println(getDivider(totalWidth));

        for (int y = 0; y < boardHeight; y++) {
            String entityRow = renderEntityRow(y, boardWidth);
            String log = renderLog(logs, y, logWidth);
            System.out.println(entityRow + SEPARATOR + log);
        }

        System.out.println(getDivider(totalWidth));
        System.out.flush();
    }

    private String renderEntityRow(int y, int width) {
        StringBuilder row = new StringBuilder();
        for (int x = 0; x < width; x++) {
            Coordinate coordinate = new Coordinate(x, y);
            Entity entity = boardService.getEntityAt(coordinate).orElse(null);
            row.append(getEntityChar(entity));
        }
        return row.toString();
    }

    private String renderLog(List<String> logs, int index, int width) {
        if (index >= logs.size()) return " ".repeat(width);
        return padRight(logs.get(index), width);
    }

    private String getEntityChar(Entity entity) {
        return theme.getSprite(entity);
    }

    private int getEntityRowWidth(int boardWidth) {
        return boardWidth * CHAR_WIDTH_PER_CELL;
    }

    private int getTotalWidth(int entityWidth, int logWidth) {
        return entityWidth + SEPARATOR.length() + logWidth;
    }

    private String getDivider(int totalWidth) {
        return settings.rendererDividerChar().repeat(totalWidth);
    }

    private String formatTurnTitle(int totalWidth, int turn) {
        String label = " Turn: " + turn + " ";
        int padding = Math.max(0, (totalWidth - label.length() - 2) / 2);
        return "[" + "=".repeat(padding) + label + "=".repeat(totalWidth - label.length() - padding - 2) + "]";
    }

    private String padRight(String text, int width) {
        if (text.length() >= width) return text.substring(0, width - 1) + "…";
        return text + " ".repeat(width - text.length());
    }
}
