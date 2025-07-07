package dev.zux13.map;

import dev.zux13.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public class WorldMap {

    private final int width;
    private final int height;

    private final Map<Coordinate, Entity> entityMap = new HashMap<>();

    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public Iterable<Map.Entry<Coordinate, Entity>> getAllEntities() {
        return entityMap.entrySet();
    }

    public Entity getEntityAt(Coordinate coordinate) {
        validate(coordinate);
        return entityMap.get(coordinate);
    }

    public void setEntityAt(Coordinate coordinate, Entity entity) {
        validate(coordinate);
        entityMap.put(coordinate, entity);
    }

    public void removeEntityAt(Coordinate coordinate) {
        validate(coordinate);
        entityMap.remove(coordinate);
    }

    public boolean isCellEmpty(Coordinate coordinate) {
        return isInBounds(coordinate) && getEntityAt(coordinate) == null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void validate(Coordinate coordinate) {
        if (!isInBounds(coordinate)) {
            throw new IllegalArgumentException("Координата вне границ: " + coordinate);
        }
    }

    private boolean isInBounds(Coordinate coordinate) {
        return coordinate.x() >= 0 && coordinate.x() < width &&
                coordinate.y() >= 0 && coordinate.y() < height;
    }
}
