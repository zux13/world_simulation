package dev.zux13.board;

public record Coordinate(int x, int y) {

    public static Coordinate of(int x, int y) {
        return new Coordinate(x, y);
    }

    @Override
    public String toString() {
        return "(%s|%s)".formatted(x, y);
    }
}
