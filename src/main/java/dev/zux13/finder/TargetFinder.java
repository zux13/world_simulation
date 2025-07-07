package dev.zux13.finder;

import dev.zux13.map.Coordinate;
import dev.zux13.map.WorldMap;

public interface TargetFinder {
    Coordinate findTarget(WorldMap worldMap, Coordinate from);
}
