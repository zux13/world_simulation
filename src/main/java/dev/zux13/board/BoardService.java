package dev.zux13.board;

import dev.zux13.entity.Entity;
import dev.zux13.entity.Grass;
import dev.zux13.entity.creature.Creature;
import dev.zux13.entity.creature.Herbivore;
import dev.zux13.entity.creature.Predator;
import dev.zux13.util.CoordinateUtils;
import dev.zux13.util.RandomUtils;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@RequiredArgsConstructor
public class BoardService {

    private final Board board;

    public Optional<Coordinate> findRandomEmptyCoordinate() {
        List<Coordinate> empty = CoordinateUtils.generateGrid(board.getWidth(), board.getHeight()).stream()
                .filter(board::isTileEmpty)
                .toList();

        if (empty.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(empty.get(RandomUtils.nextInt(empty.size())));
    }

    public void placeEntityAtRandom(Entity entity, int count) {
        for (int i = 0; i < count; i++) {
            findRandomEmptyCoordinate().ifPresent(coordinate -> board.setEntityAt(coordinate, entity));
        }
    }

    public void placeEntityAtRandom(Supplier<Entity> entitySupplier, int count) {
        for (int i = 0; i < count; i++) {
            findRandomEmptyCoordinate().ifPresent(coordinate -> board.setEntityAt(coordinate, entitySupplier.get()));
        }
    }

    public List<Coordinate> findEmptyNeighbors(Coordinate from) {
        return getNeighbors(from).stream()
                .filter(board::isTileEmpty)
                .toList();
    }

    public List<Creature> getCreatures() {
        return board.getEntities(Creature.class);
    }

    public List<Coordinate> getCreatureCoordinates() {
        return board.getCoordinates(Creature.class);
    }

    public int getGrassCount() {
        return board.getEntities(Grass.class).size();
    }

    public int getHerbivoresCount() {
        return board.getEntities(Herbivore.class).size();
    }

    public int getPredatorsCount() {
        return board.getEntities(Predator.class).size();
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
        return CoordinateUtils.getNeighborsInBounds(from, board.getWidth(), board.getHeight());
    }

    private Optional<Coordinate> findNeighborWith(Class<? extends Entity> type, Coordinate from) {
        return getNeighbors(from).stream()
                .filter(c -> board.getEntityAt(c).filter(type::isInstance).isPresent())
                .findFirst();
    }

    private List<Coordinate> getVisibleEntitiesOfType(Class<? extends Entity> type, Coordinate from, int radius) {
        List<Coordinate> result = new ArrayList<>();
        int fromX = from.x();
        int fromY = from.y();

        for (int x = fromX - radius; x <= fromX + radius; x++) {
            for (int y = fromY - radius; y <= fromY + radius; y++) {
                if (x < 0 || x >= board.getWidth() || y < 0 || y >= board.getHeight()) {
                    continue;
                }

                Coordinate current = new Coordinate(x, y);
                if (!CoordinateUtils.isWithinRadius(from, current, radius)) {
                    continue;
                }

                board.getEntityAt(current)
                        .filter(type::isInstance)
                        .ifPresent(entity -> result.add(current));
            }
        }
        return result;
    }
}
