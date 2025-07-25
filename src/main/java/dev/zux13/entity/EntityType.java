package dev.zux13.entity;

import dev.zux13.entity.creature.Herbivore;
import dev.zux13.entity.creature.Predator;

public enum EntityType {
    GRASS(Grass.class),
    HERBIVORE(Herbivore.class),
    PREDATOR(Predator.class);

    public final Class<? extends Entity> type;
    EntityType(Class<? extends Entity> type) {
        this.type = type;
    }
}
