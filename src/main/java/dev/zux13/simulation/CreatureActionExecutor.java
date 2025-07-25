package dev.zux13.simulation;

import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;
import dev.zux13.event.EventBus;
import dev.zux13.event.EventSubscriber;
import dev.zux13.event.Priority;
import dev.zux13.event.events.CreatureActionDecidedEvent;
import dev.zux13.event.events.CreatureActionExecutedEvent;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CreatureActionExecutor implements EventSubscriber {

    private final BoardService boardService;
    private final EventBus eventBus;

    @Override
    public void subscribeToEvents(EventBus eventBus) {
        eventBus.subscribe(CreatureActionDecidedEvent.class, this::executeCreatureAction, Priority.HIGH);
    }

    private void executeCreatureAction(CreatureActionDecidedEvent event) {
        Coordinate actual = event.action().execute(boardService);
        eventBus.publish(new CreatureActionExecutedEvent(event.creature(), actual));
    }
}
