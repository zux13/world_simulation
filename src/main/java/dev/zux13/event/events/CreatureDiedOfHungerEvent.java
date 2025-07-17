package dev.zux13.event.events;

import dev.zux13.board.Coordinate;
import dev.zux13.entity.creature.Creature;
import dev.zux13.event.Event;

public record CreatureDiedOfHungerEvent(Creature creature, Coordinate coordinate) implements Event {
}
