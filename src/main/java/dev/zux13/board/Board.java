package dev.zux13.board;

import dev.zux13.entity.Entity;
import dev.zux13.util.CoordinateUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public class Board {

    private final int width;
    private final int height;

    @Getter(AccessLevel.NONE)
    private final Map<Coordinate, Entity> entities = new HashMap<>();

    public <T extends Entity> List<T> getEntities(Class<T> type) {
        return entities.values().stream()
                .filter(type::isInstance)
                .map(type::cast)
                .collect(Collectors.toList());
    }

    public <T extends Entity> List<Coordinate> getCoordinates(Class<T> type) {
        return entities.entrySet().stream()
                .filter(entry -> type.isInstance(entry.getValue()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    public Optional<Entity> getEntityAt(Coordinate coordinate) {
        validate(coordinate);
        return Optional.ofNullable(entities.get(coordinate));
    }

    public void setEntityAt(Coordinate coordinate, Entity entity) {
        validate(coordinate);
        entities.put(coordinate, entity);
    }

    public void removeEntityAt(Coordinate coordinate) {
        validate(coordinate);
        entities.remove(coordinate);
    }

    public boolean isTileEmpty(Coordinate coordinate) {
        return isInBounds(coordinate) && getEntityAt(coordinate).isEmpty();
    }

    private boolean isInBounds(Coordinate coordinate) {
        return CoordinateUtils.isWithinBounds(coordinate.x(), coordinate.y(), width, height);
    }

    private void validate(Coordinate coordinate) {
        if (!isInBounds(coordinate)) {
            throw new IllegalArgumentException("Coordinate out of range: " + coordinate);
        }
    }
}
