package dev.zux13.finder;

import dev.zux13.map.Coordinate;
import dev.zux13.map.WorldMap;

import java.util.List;

public interface PathFinder {
    List<Coordinate> findPath(WorldMap worldMap, Coordinate start, Coordinate target);
}
