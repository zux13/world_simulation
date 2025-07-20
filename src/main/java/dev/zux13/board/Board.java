package dev.zux13.board;

import dev.zux13.entity.Entity;
import dev.zux13.entity.Grass;
import dev.zux13.entity.creature.Creature;
import dev.zux13.entity.creature.Herbivore;
import dev.zux13.entity.creature.Predator;
import dev.zux13.util.CoordinateUtils;
import dev.zux13.util.RandomUtils;

import java.util.*;
import java.util.function.Supplier;

public class Board {

    private final int width;
    private final int height;

    private final Map<Coordinate, Entity> entityMap = new HashMap<>();

    public Board(int width, int height) {
        this.width = width;
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getGrassCount() {
        return getCountOf(Grass.class);
    }

    public int getHerbivoresCount() {
        return getCountOf(Herbivore.class);
    }

    public int getPredatorsCount() {
        return getCountOf(Predator.class);
    }

    public Optional<Coordinate> findRandomEmptyCoordinate() {
        List<Coordinate> empty = CoordinateUtils.generateGrid(width, height).stream()
                .filter(this::isTileEmpty)
                .toList();

        if (empty.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(empty.get(RandomUtils.nextInt(empty.size())));
    }

    public void placeEntityAtRandom(Entity entity, int count) {
        for (int i = 0; i < count; i++) {
            findRandomEmptyCoordinate().ifPresent(coordinate -> setEntityAt(coordinate, entity));
        }
    }

    public void placeEntityAtRandom(Supplier<Entity> entitySupplier, int count) {
        for (int i = 0; i < count; i++) {
            findRandomEmptyCoordinate().ifPresent(coordinate -> setEntityAt(coordinate, entitySupplier.get()));
        }
    }

    public List<Coordinate> findEmptyNeighbors(Coordinate from) {
        return getNeighbors(from).stream()
                .filter(this::isTileEmpty)
                .toList();
    }

    public List<Coordinate> getCreatureCoordinates() {
        return entityMap.entrySet().stream()
                .filter(e -> e.getValue() instanceof Creature)
                .map(Map.Entry::getKey)
                .toList();
    }

    public List<Creature> getCreatures() {
        return entityMap.values().stream()
                .filter(entity -> entity instanceof Creature)
                .map(entity -> (Creature) entity)
                .toList();
    }

    public Optional<Coordinate> findNeighborHerbivore(Coordinate from) {
        return findNeighborWith(Herbivore.class, from);
    }

    public Optional<Coordinate> findNeighborGrass(Coordinate from) {
        return findNeighborWith(Grass.class, from);
    }

    public List<Coordinate> getVisibleGrass(Coordinate from, int radius) {
        return getVisibleEntitiesOfType(Grass.class, from, radius);
    }

    public List<Coordinate> getVisibleHerbivores(Coordinate from, int radius) {
        return getVisibleEntitiesOfType(Herbivore.class, from, radius);
    }

    public List<Coordinate> getVisiblePredators(Coordinate from, int radius) {
        return getVisibleEntitiesOfType(Predator.class, from, radius);
    }

    public List<Coordinate> getNeighbors(Coordinate from) {
        return CoordinateUtils.getNeighborsInBounds(from, width, height);
    }

    public Optional<Entity> getEntityAt(Coordinate coordinate) {
        validate(coordinate);
        return Optional.ofNullable(entityMap.get(coordinate));
    }

    public void moveEntity(Coordinate from, Coordinate to) {
        getEntityAt(from)
                .ifPresent( entity -> {
                    setEntityAt(to, entity);
                    removeEntityAt(from);
                });
    }

    public void setEntityAt(Coordinate coordinate, Entity entity) {
        validate(coordinate);
        entityMap.put(coordinate, entity);
    }

    public void removeEntityAt(Coordinate coordinate) {
        validate(coordinate);
        entityMap.remove(coordinate);
    }

    public boolean isTileEmpty(Coordinate coordinate) {
        return isInBounds(coordinate) && getEntityAt(coordinate).isEmpty();
    }

    private int getCountOf(Class<? extends Entity> type) {
        return (int) entityMap.entrySet().stream()
                .filter(e -> type.isAssignableFrom(e.getValue().getClass()))
                .count();
    }

    private Optional<Coordinate> findNeighborWith(Class<? extends Entity> type, Coordinate from) {
        return getNeighbors(from).stream()
                .filter(c -> {
                    Optional<Entity> entityOptional = getEntityAt(c);
                    return entityOptional.isPresent()
                            && type.isAssignableFrom(entityOptional.get().getClass());
                })
                .findFirst();
    }

    private List<Coordinate> getVisibleEntitiesOfType(Class<? extends Entity> type, Coordinate from, int radius) {
        List<Coordinate> result = new ArrayList<>();
        int fromX = from.x();
        int fromY = from.y();

        for (int x = fromX - radius; x <= fromX + radius; x++) {
            for (int y = fromY - radius; y <= fromY + radius; y++) {
                if (x < 0 || x >= width || y < 0 || y >= height) {
                    continue;
                }

                Coordinate current = Coordinate.of(x, y);
                if (!CoordinateUtils.isWithinRadius(from, current, radius)) {
                    continue;
                }

                getEntityAt(current)
                        .filter(type::isInstance)
                        .ifPresent(entity -> result.add(current));
            }
        }
        return result;
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
