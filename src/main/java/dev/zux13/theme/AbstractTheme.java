package dev.zux13.theme;

import dev.zux13.entity.Entity;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractTheme implements Theme {

    private final Map<Class<? extends Entity>, String> spriteMap;
    private final Map<EmojiType, String> emojiMap;

    protected AbstractTheme(Map<Class<? extends Entity>, String> spriteMap,
                            Map<EmojiType, String> emojiMap) {
        this.spriteMap = spriteMap;
        this.emojiMap = emojiMap;
    }

    @Override
    public String getSprite(Entity entity) {
        return spriteMap.getOrDefault(entity == null ? null : entity.getClass(), "?");
    }

    @Override
    public String getSprite(Class<? extends Entity> entityClass) {
        return spriteMap.getOrDefault(entityClass, "?");
    }

    @Override
    public String getSymbol(EmojiType type) {
        return emojiMap.getOrDefault(type,"?");
    }

    protected static Map<EmojiType, String> buildDefaultSymbolMap() {
        Map<EmojiType, String> map = new HashMap<>();
        map.put(EmojiType.HEALTH, "❤️");
        map.put(EmojiType.SPEED, "💨");
        map.put(EmojiType.VISION, "👁️");
        map.put(EmojiType.ATTACK, "⚔️");
        map.put(EmojiType.HUNGER, "🍗");
        map.put(EmojiType.DEATH, "💀");
        map.put(EmojiType.SLEEP, "💤");
        map.put(EmojiType.UP, "⬆️");
        map.put(EmojiType.DOWN, "⬇️");
        return map;
    }
}
