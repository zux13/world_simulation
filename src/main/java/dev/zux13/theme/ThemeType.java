package dev.zux13.theme;

public enum ThemeType {
    FOREST("Forest"),
    DESERT("Desert"),
    SNOW("Snow"),
    STEPPE("Steppe");

    private final String displayName;

    ThemeType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
