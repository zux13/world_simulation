package dev.zux13.logger;

import dev.zux13.action.creature.*;
import dev.zux13.board.Coordinate;
import dev.zux13.entity.Entity;
import dev.zux13.entity.Grass;
import dev.zux13.entity.creature.Creature;
import dev.zux13.entity.creature.Predator;
import dev.zux13.event.EventBus;
import dev.zux13.event.Priority;
import dev.zux13.event.events.CreatureActionDecidedEvent;
import dev.zux13.event.events.CreatureSpawnedEvent;
import dev.zux13.event.events.GrassSpawnedEvent;
import dev.zux13.theme.EmojiType;
import dev.zux13.theme.Theme;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ActionLogger implements LogProvider {

    private final LinkedList<String> logs = new LinkedList<>();
    private final Theme theme;
    private final int maxSize;

    public ActionLogger(int maxSize, Theme theme, EventBus eventBus) {
        this.maxSize = maxSize;
        this.theme = theme;
        subscribeToEvents(eventBus);
    }

    private void subscribeToEvents(EventBus eventBus) {
        eventBus.subscribe(GrassSpawnedEvent.class, this::onGrassSpawned, Priority.NORMAL);
        eventBus.subscribe(CreatureSpawnedEvent.class, this::onCreatureSpawned, Priority.NORMAL);
        eventBus.subscribe(CreatureActionDecidedEvent.class, this::onCreatureActionDecided, Priority.NORMAL);
    }

    private void onCreatureActionDecided(CreatureActionDecidedEvent event) {
        String logMessage = formatActionMessage(event.action());
        if (logMessage != null) {
            log(logMessage);
        }
    }

    private String formatActionMessage(CreatureAction action) {

        if (action instanceof MoveCreatureAction(Creature creature, Coordinate from, Coordinate to, MoveType moveType)) {
            String sprite = theme.getSprite(creature);
            return String.format("%s %s %s —> %s", sprite, moveType.getDescription(), from, to);
        }

        if (action instanceof EatCreatureAction(Creature creature, Entity target, Coordinate coordinate)) {
            String creatureSprite = theme.getSprite(creature);
            String targetSprite = theme.getSprite(target);
            return String.format("%s eats %s —> %s", creatureSprite, targetSprite, coordinate);
        }

        if (action instanceof AttackCreatureAction(Predator predator, Creature target, Coordinate coordinate)) {
            String predatorSprite = theme.getSprite(predator);
            String targetSprite = theme.getSprite(target);
            return String.format("%s %s %s —> %s",
                    predatorSprite,
                    theme.getSymbol(EmojiType.ATTACK),
                    targetSprite,
                    coordinate
            );
        }
        return null;
    }

    private void onGrassSpawned(GrassSpawnedEvent event) {
        if (event.count() > 0) {
            String grassSprite = theme.getSprite(Grass.class);
            log(String.format("%s x %d grows", grassSprite, event.count()));
        }
    }

    private void onCreatureSpawned(CreatureSpawnedEvent event) {
        log(String.format("%s —> %s",
                formatCreatureStats(event.creature()),
                event.coordinate()));
    }

    public void log(String message) {
        if (logs.size() >= maxSize) {
            logs.removeFirst();
        }
        logs.addLast(message);
    }

    private String formatCreatureStats(Creature creature) {
        StringBuilder sb = new StringBuilder(
                "%s %s:%d %s:%d %s:%d".formatted(
                        theme.getSprite(creature),
                        theme.getSymbol(EmojiType.HEALTH), creature.getMaxHP(),
                        theme.getSymbol(EmojiType.SPEED), creature.getSpeed(),
                        theme.getSymbol(EmojiType.VISION), creature.getVision()
                )
        );
        if (creature instanceof Predator) {
            sb.append(" %s:%d".formatted(
                    theme.getSymbol(EmojiType.ATTACK),
                    ((Predator) creature).getAttack())
            );
        }
        return sb.toString();
    }

    @Override
    public List<String> getLogSnapshot() {
        return new ArrayList<>(logs);
    }
}
