package dev.zux13.renderer;

import dev.zux13.entity.Entity;
import dev.zux13.entity.Grass;
import dev.zux13.entity.Rock;
import dev.zux13.entity.Tree;
import dev.zux13.entity.creature.Herbivore;
import dev.zux13.entity.creature.Predator;
import dev.zux13.map.Coordinate;
import dev.zux13.map.WorldMap;

public class ConsoleRenderer implements Renderer {

    @Override
    public void render(WorldMap worldMap, int turnCount) {
        System.out.print("\033[H\033[2J");
        System.out.flush();

        System.out.println("=== Ход: " + turnCount + " ===");

        for (int y = 0; y < worldMap.getHeight(); y++) {
            for (int x = 0; x < worldMap.getWidth(); x++) {
                Coordinate coordinate = new Coordinate(x, y);
                Entity entity = worldMap.getEntityAt(coordinate);
                System.out.print(emojiBy(entity));
            }
            System.out.println();
        }

        System.out.println("-".repeat(30));
    }

    private String emojiBy(Entity entity) {
        return switch (entity) {
            case null -> "🟫";
            case Herbivore herbivore -> "🐇";
            case Predator predator -> "🐺";
            case Grass grass -> "🌿";
            case Rock rock -> "⛰️";
            case Tree tree -> "🌲";
            default -> "?";
        };
    }
}
