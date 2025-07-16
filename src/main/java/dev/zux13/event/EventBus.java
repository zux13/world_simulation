package dev.zux13.event;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;

public class EventBus {

    private final Map<Class<? extends Event>, List<EventListener<?>>> listeners = new ConcurrentHashMap<>();

    public <T extends Event> void subscribe(Class<T> eventType, Consumer<T> listener, Priority priority) {
        List<EventListener<?>> eventListeners = listeners.computeIfAbsent(eventType, k -> new ArrayList<>());
        
        synchronized (eventListeners) {
            eventListeners.add(new EventListener<>(listener, priority));
            eventListeners.sort(Comparator.comparing((EventListener<?> l) -> l.priority()).reversed());
        }
    }

    @SuppressWarnings("unchecked")
    public void publish(Event event) {
        List<EventListener<?>> eventListeners = listeners.get(event.getClass());
        if (eventListeners != null) {
            List<EventListener<?>> snapshot;
            synchronized (eventListeners) {
                snapshot = new ArrayList<>(eventListeners);
            }
            
            for (EventListener listenerWrapper : snapshot) {
                try {
                    listenerWrapper.listener().accept(event);
                } catch (Exception e) {
                    throw new RuntimeException("Exception in event listener for %s: "
                            .formatted(event.getClass().getSimpleName()), e);
                }
            }
        }
    }
}