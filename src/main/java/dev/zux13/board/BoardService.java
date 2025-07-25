package dev.zux13.board;

import dev.zux13.entity.Entity;
import dev.zux13.entity.EntityType;
import dev.zux13.entity.creature.Creature;
import dev.zux13.util.CoordinateUtils;
import dev.zux13.util.RandomUtils;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class BoardService {

    private final Board board;

    public int getWidth() {
        return board.getWidth();
    }

    public int getHeight() {
        return board.getHeight();
    }

    public Optional<Entity> getEntityAt(Coordinate coordinate) {
        return board.getEntityAt(coordinate);
    }

    public void setEntityAt(Coordinate coordinate, Entity entity) {
        board.setEntityAt(coordinate, entity);
    }

    public void removeEntityAt(Coordinate coordinate) {
        board.removeEntityAt(coordinate);
    }

    public boolean isTileEmpty(Coordinate coordinate) {
        return board.isTileEmpty(coordinate);
    }

    public Optional<Coordinate> findRandomEmptyCoordinate() {
        List<Coordinate> empty = CoordinateUtils.generateGrid(board.getWidth(), board.getHeight()).stream()
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

    public List<Creature> getCreatures() {
        return board.getEntities(Creature.class);
    }

    public List<Coordinate> getCreatureCoordinates() {
        return board.getCoordinates(Creature.class);
    }

    public int getCountOf(EntityType entityType) {
        return board.getEntities(entityType.type).size();
    }

    public Optional<Coordinate> findNeighbor(EntityType entityType, Coordinate from) {
        return findNeighborWith(entityType.type, from);
    }

    public List<Coordinate> getVisible(EntityType entityType, Coordinate from, int radius) {
        return getVisibleEntitiesOfType(entityType.type, from, radius);
    }

    public List<Coordinate> getNeighbors(Coordinate from) {
        return CoordinateUtils.getNeighborsInBounds(from, board.getWidth(), board.getHeight());
    }

    private Optional<Coordinate> findNeighborWith(Class<? extends Entity> type, Coordinate from) {
        return getNeighbors(from).stream()
                .filter(c -> getEntityAt(c).filter(type::isInstance).isPresent())
                .findFirst();
    }

    private List<Coordinate> getVisibleEntitiesOfType(Class<? extends Entity> type, Coordinate from, int radius) {
        return CoordinateUtils.getCoordinatesWithinRadius(from, radius, board.getWidth(), board.getHeight()).stream()
                .filter(coordinate -> getEntityAt(coordinate).filter(type::isInstance).isPresent())
                .toList();
    }
}
