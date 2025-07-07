package dev.zux13.entity.creature;

import dev.zux13.entity.Entity;

public abstract class Creature extends Entity {

    private int hp;
    private int speed;

    abstract void makeMove();
}
