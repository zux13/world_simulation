package dev.zux13.logger;

import dev.zux13.action.*;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.Entity;
import dev.zux13.entity.Grass;
import dev.zux13.entity.creature.Creature;
import dev.zux13.entity.creature.Predator;
import dev.zux13.event.EventBus;
import dev.zux13.event.EventSubscriber;
import dev.zux13.event.Priority;
import dev.zux13.event.events.*;
import dev.zux13.theme.EmojiType;
import dev.zux13.theme.Theme;
import dev.zux13.util.ConsoleUtils;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
public class EventLogger implements LogProvider, EventSubscriber {

    private final LinkedList<String> logs = new LinkedList<>();
    private final Theme theme;
    private final int maxSize;
    private final int logWidth;
    private final String divider;

    @Override
    public void subscribeToEvents(EventBus eventBus) {
        eventBus.subscribe(GrassSpawnedEvent.class, this::onGrassSpawned, Priority.NORMAL);
        eventBus.subscribe(CreatureSpawnedEvent.class, this::onCreatureSpawned, Priority.NORMAL);
        eventBus.subscribe(CreatureActionDecidedEvent.class, this::onCreatureActionDecided, Priority.NORMAL);
        eventBus.subscribe(CreatureIsStarvingEvent.class, this::onCreatureIsStarving, Priority.NORMAL);
        eventBus.subscribe(CreatureDiedOfHungerEvent.class, this::onCreatureDiedOfHunger, Priority.NORMAL);
    }

    @Override
    public List<String> getLogSnapshot() {
        return new ArrayList<>(logs);
    }

    private void onGrassSpawned(GrassSpawnedEvent event) {
        if (event.count() > 0) {
            String message = "%s x %d %s".formatted(
                    theme.getSprite(Grass.class),
                    event.count(),
                    theme.getSymbol(EmojiType.UP)
            );
            log(alignCenter(message));
        }
    }

    private void onCreatureSpawned(CreatureSpawnedEvent event) {
        String left = formatCreatureStats(event.creature());
        String right = "—> %s".formatted(event.coordinate());
        log(alignRight(left, right));
    }

    private void onCreatureActionDecided(CreatureActionDecidedEvent event) {
        String logMessage = formatActionMessage(event.action());
        if (!logMessage.isEmpty()) {
            log(logMessage);
        }
    }

    private void onCreatureIsStarving(CreatureIsStarvingEvent event) {
        String left = "%s starves %s%s".formatted(
                theme.getSprite(event.creature()),
                theme.getSymbol(EmojiType.HUNGER),
                theme.getSymbol(EmojiType.DOWN)
        );
        String right = "—> %s".formatted(event.coordinate());
        log(alignRight(left, right));
    }

    private void onCreatureDiedOfHunger(CreatureDiedOfHungerEvent event) {
        String left = "%s %s %s%s".formatted(
                theme.getSprite(event.creature()),
                theme.getSymbol(EmojiType.DEATH),
                theme.getSymbol(EmojiType.HUNGER),
                theme.getSymbol(EmojiType.DOWN)
        );
        String right = "—> %s".formatted(event.coordinate());
        log(alignRight(left, right));
    }

    private String formatActionMessage(CreatureAction action) {
        if (action instanceof MoveCreatureAction m) {
            return formatMoveAction(m.creature(), m.from(), m.to(), m.moveType());
        }

        if (action instanceof EatCreatureAction e) {
            return formatEatAction(e.creature(), e.food(), e.current());
        }

        if (action instanceof AttackCreatureAction a) {
            return formatAttackAction(a.predator(), a.target(), a.current());
        }

        if (action instanceof SleepCreatureAction s) {
            return formatSleepAction(s.creature(), s.current());
        }

        return "";
    }

    private String formatMoveAction(Creature creature, Coordinate from, Coordinate to, MoveType type) {
        String left = "%s %s".formatted(theme.getSprite(creature), type.getDescription());
        String right = "%s —> %s".formatted(from, to);
        return alignRight(left, right);
    }

    private String formatEatAction(Creature creature, Entity target, Coordinate coordinate) {
        String left = "%s eats %s | %d %s%s".formatted(
                theme.getSprite(creature),
                theme.getSprite(target),
                creature.getHealRestore(),
                theme.getSymbol(EmojiType.HEALTH),
                theme.getSymbol(EmojiType.UP)
        );
        String right = "—> %s".formatted(coordinate);
        return alignRight(left, right);
    }

    private String formatAttackAction(Predator predator, Creature target, Coordinate coordinate) {
        String left = "%s %s %s | %d %s%s".formatted(
                theme.getSprite(predator),
                theme.getSymbol(EmojiType.ATTACK),
                theme.getSprite(target),
                predator.getAttack(),
                theme.getSymbol(EmojiType.HEALTH),
                theme.getSymbol(EmojiType.DOWN)
        );
        String right = "—> %s".formatted(coordinate);
        return alignRight(left, right);
    }

    private String formatSleepAction(Creature creature, Coordinate coordinate) {
        String left = "%s sleeps %s".formatted(
                theme.getSprite(creature),
                theme.getSymbol(EmojiType.SLEEP)
        );
        String right = "—> %s".formatted(coordinate);
        return alignRight(left, right);
    }

    private String formatCreatureStats(Creature creature) {
        String stats = "%s %s:%d %s:%d %s:%d".formatted(
                theme.getSprite(creature),
                theme.getSymbol(EmojiType.HEALTH), creature.getMaxHp(),
                theme.getSymbol(EmojiType.SPEED), creature.getSpeed(),
                theme.getSymbol(EmojiType.VISION), creature.getVision()
        );

        if (creature instanceof Predator predator) {
            stats += " %s:%d".formatted(
                    theme.getSymbol(EmojiType.ATTACK),
                    predator.getAttack()
            );
        }

        return stats;
    }

    public void log(String message) {
        if (logs.size() >= maxSize) {
            logs.removeFirst();
        }
        logs.addLast(message);
    }

    private String alignRight(String left, String right) {
        int leftLength = ConsoleUtils.getVisualLength(left.trim());
        int rightLength = ConsoleUtils.getVisualLength(right.trim());

        int spaceCount = Math.max(1, logWidth - leftLength - rightLength);
        return left.trim() + " ".repeat(spaceCount) + right.trim();
    }

    private String alignCenter(String text) {
        int totalPadding = logWidth - text.length() - 2;
        if (totalPadding <= 0) {
            return text;
        }

        int paddingLeft = totalPadding / 2;
        int paddingRight = totalPadding - paddingLeft;

        return "%s %s %s".formatted(
                divider.repeat(paddingLeft),
                text,
                divider.repeat(paddingRight)
        );
    }
}
