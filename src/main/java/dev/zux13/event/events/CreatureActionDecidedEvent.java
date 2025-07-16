package dev.zux13.event.events;

import dev.zux13.action.creature.CreatureAction;
import dev.zux13.event.Event;

public record CreatureActionDecidedEvent(CreatureAction action) implements Event {
}
