package dev.zux13.settings;

import dev.zux13.theme.Theme;
import dev.zux13.theme.ThemeFactory;

public class SimulationSettingsBuilder {
    private final SimulationProperties properties;
    private final int minWidth;
    private final int maxWidth;
    private final int minHeight;
    private final int maxHeight;

    private Integer boardWidth;
    private Integer boardHeight;
    private String themeName;

    public SimulationSettingsBuilder(SimulationProperties properties) {
        this.properties = properties;
        this.minWidth = properties.getMinBoardWidth();
        this.maxWidth = properties.getMaxBoardWidth();
        this.minHeight = properties.getMinBoardHeight();
        this.maxHeight = properties.getMaxBoardHeight();
        this.boardWidth = properties.getBoardWidth();
        this.boardHeight = properties.getBoardHeight();
        this.themeName = ThemeFactory.getTheme(properties.getDefaultTheme()).getName();
    }

    public SimulationSettingsBuilder setBoardWidth(int width) {
        if (width < minWidth || width > maxWidth) {
            throw new IllegalArgumentException("Width must be in range [%d;%d]".formatted(minWidth, maxWidth));
        }
        this.boardWidth = width;
        return this;
    }

    public SimulationSettingsBuilder setBoardHeight(int height) {
        if (height < minHeight || height > maxHeight) {
            throw new IllegalArgumentException("Height must be in range [%d;%d]".formatted(minHeight, maxHeight));
        }
        this.boardHeight = height;
        return this;
    }

    public SimulationSettingsBuilder setTheme(String themeName) {
        this.themeName = themeName;
        return this;
    }

    public SimulationSettings build() {
        Theme theme = ThemeFactory.getTheme(themeName);
        return new SimulationSettings(boardWidth, boardHeight, theme, properties);
    }

}
