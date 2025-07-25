package dev.zux13.navigator;

import dev.zux13.board.BoardService;
import dev.zux13.board.Coordinate;
import dev.zux13.util.CoordinateUtils;

import java.util.*;

public class AStarNavigator implements Navigator {

    private record Node(Coordinate coordinate, Node parent, int gCost, int hCost) {
        int fCost() {
            return gCost + hCost;
        }
    }

    @Override
    public Optional<Coordinate> nextStep(BoardService boardService, Coordinate from, Coordinate to) {
        if (from.equals(to)) {
            return Optional.empty();
        }

        PriorityQueue<Node> openSet = new PriorityQueue<>(Comparator.comparingInt(Node::fCost));
        Map<Coordinate, Integer> gScores = new HashMap<>();
        Set<Coordinate> closedSet = new HashSet<>();

        Node start = new Node(from, null, 0, CoordinateUtils.diagonalDistance(from, to));
        openSet.add(start);
        gScores.put(from, 0);

        while (!openSet.isEmpty()) {
            Node current = openSet.poll();
            Coordinate currentCoordinate = current.coordinate;

            if (currentCoordinate.equals(to)) {
                return Optional.of(retraceFirstStep(current));
            }

            if (!closedSet.add(currentCoordinate)) {
                continue;
            }

            for (Coordinate neighbor : boardService.getNeighbors(currentCoordinate)) {
                if ((!boardService.isTileEmpty(neighbor) && !neighbor.equals(to)) || closedSet.contains(neighbor)) {
                    continue;
                }

                int tentativeG = current.gCost + CoordinateUtils.getMovementCost(currentCoordinate, neighbor);
                if (tentativeG >= gScores.getOrDefault(neighbor, Integer.MAX_VALUE)) {
                    continue;
                }

                gScores.put(neighbor, tentativeG);
                int h = CoordinateUtils.diagonalDistance(neighbor, to);
                openSet.add(new Node(neighbor, current, tentativeG, h));
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
