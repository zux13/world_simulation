package dev.zux13.action;

import lombok.Getter;

@Getter
public enum MoveType {
    ROAM("roams"),
    CHASE("chases"),
    FLEE("flees"),
    MOVE("moves");

    private final String description;

    MoveType(String description) {
        this.description = description;
    }
}
