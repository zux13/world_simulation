package dev.zux13.finder;

import dev.zux13.board.Coordinate;
import dev.zux13.board.Board;
import dev.zux13.util.CoordinateUtils;

import java.util.*;

public class AStarPathFinder implements PathFinder {

    private record Node(Coordinate coordinate, Node parent, int gCost, int hCost) {
        int fCost() {
            return gCost + hCost;
        }
    }

    @Override
    public Optional<Coordinate> nextStep(Board board, Coordinate from, Coordinate to) {
        if (from.equals(to)) return Optional.empty();

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::fCost));
        Set<Coordinate> closedSet = new HashSet<>();

        openSet.add(new Node(from, null, 0, CoordinateUtils.manhattanDistance(from, to)));

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            if (current.coordinate.equals(to)) {
                return Optional.of(retraceFirstStep(current));
            }

            closedSet.add(current.coordinate);

            for (Coordinate neighbor : board.getNeighbors(current.coordinate)) {
                if (!board.isTileEmpty(neighbor) && !neighbor.equals(to)) continue;
                if (closedSet.contains(neighbor)) continue;

                int gCost = current.gCost + 1;
                int hCost = CoordinateUtils.manhattanDistance(neighbor, to);

                openSet.add(new Node(neighbor, current, gCost, hCost));
            }
        }

        return Optional.empty();
    }

    private Coordinate retraceFirstStep(Node node) {
        Node current = node;
        while (current.parent != null && current.parent.parent != null) {
            current = current.parent;
        }
        return current.coordinate;
    }
}
