package dev.zux13.theme;

import com.google.gson.annotations.SerializedName;
import dev.zux13.entity.Entity;
import dev.zux13.entity.Grass;
import dev.zux13.entity.Rock;
import dev.zux13.entity.Tree;
import dev.zux13.entity.creature.Herbivore;
import dev.zux13.entity.creature.Predator;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Setter
public class JsonTheme implements Theme {

    private String name;

    @SerializedName("sprites")
    private Map<String, String> spriteMap;

    @SerializedName("symbols")
    private Map<EmojiType, String> emojiMap;

    private transient Map<Class<? extends Entity>, String> mappedSpriteMap;

    public void init() {
        mappedSpriteMap = new HashMap<>();
        spriteMap.forEach((key, value) -> {
            switch (key.toLowerCase()) {
                case "default" -> mappedSpriteMap.put(null, value);
                case "herbivore" -> mappedSpriteMap.put(Herbivore.class, value);
                case "predator" -> mappedSpriteMap.put(Predator.class, value);
                case "grass" -> mappedSpriteMap.put(Grass.class, value);
                case "rock" -> mappedSpriteMap.put(Rock.class, value);
                case "tree" -> mappedSpriteMap.put(Tree.class, value);
            }
        });
    }

    @Override
    public String getSprite(Entity entity) {
        return mappedSpriteMap.getOrDefault(entity == null ? null : entity.getClass(), "?");
    }

    @Override
    public String getSprite(Class<? extends Entity> entityClass) {
        return mappedSpriteMap.getOrDefault(entityClass, "?");
    }

    @Override
    public String getSymbol(EmojiType type) {
        return emojiMap.getOrDefault(type, "?");
    }

    @Override
    public String getName() {
        return name;
    }
}
