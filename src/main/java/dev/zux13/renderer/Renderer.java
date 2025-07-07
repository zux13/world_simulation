package dev.zux13.renderer;

import dev.zux13.map.WorldMap;

public interface Renderer {
    void render(WorldMap worldMap, int turnCount);
}
