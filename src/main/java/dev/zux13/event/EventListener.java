package dev.zux13.event;

import java.util.function.Consumer;

record EventListener<T extends Event>(Consumer<T> listener, Priority priority)
        implements Comparable<EventListener<T>> {

    @Override
    public int compareTo(EventListener<T> other) {
        return other.priority.compareTo(this.priority);
    }
}