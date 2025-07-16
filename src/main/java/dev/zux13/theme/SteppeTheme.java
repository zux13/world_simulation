package dev.zux13.theme;

import dev.zux13.entity.*;
import dev.zux13.entity.creature.*;

import java.util.HashMap;
import java.util.Map;

public class SteppeTheme extends AbstractTheme {

    public SteppeTheme() {
        super(buildSpriteMap(), buildDefaultSymbolMap());
    }

    private static Map<Class<? extends Entity>, String> buildSpriteMap() {
        Map<Class<? extends Entity>, String> map = new HashMap<>();
        map.put(null, "🟨");
        map.put(Herbivore.class, "🦙");
        map.put(Predator.class, "🦁");
        map.put(Grass.class, "🌾");
        map.put(Rock.class, "🪨");
        map.put(Tree.class, "🌳");
        return map;
    }
}
