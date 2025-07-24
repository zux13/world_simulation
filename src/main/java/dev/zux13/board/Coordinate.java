package dev.zux13.board;

public record Coordinate(int x, int y) {

    @Override
    public String toString() {
        return "(%02d|%02d)".formatted(x, y);
    }
}
