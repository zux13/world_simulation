package dev.zux13.theme;

import dev.zux13.entity.Entity;

public interface Theme {
    String getSprite(Entity entity);
    String getSprite(Class<? extends Entity> entityClass);
    String getSymbol(EmojiType type);
    String getName();
}
