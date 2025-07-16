package dev.zux13.settings;

import dev.zux13.theme.Theme;
import dev.zux13.theme.ThemeFactory;
import dev.zux13.theme.ThemeType;

public class SimulationSettingsBuilder {
    private final SimulationProperties properties;
    private final int minWidth;
    private final int minHeight;

    private Integer boardWidth;
    private Integer boardHeight;
    private ThemeType themeType;

    public SimulationSettingsBuilder(SimulationProperties properties) {
        this.properties = properties;
        this.minWidth = properties.getMinBoardWidth();
        this.minHeight = properties.getMinBoardHeight();
        this.boardWidth = properties.getBoardWidth();
        this.boardHeight = properties.getBoardHeight();
        this.themeType = properties.getDefaultTheme();
    }

    public SimulationSettingsBuilder setBoardWidth(int width) {
        if (width < minWidth) {
            throw new IllegalArgumentException("Minimum width is " + minWidth);
        }
        this.boardWidth = width;
        return this;
    }

    public SimulationSettingsBuilder setBoardHeight(int height) {
        if (height < minHeight) {
            throw new IllegalArgumentException("Minimum height is " + minHeight);
        }
        this.boardHeight = height;
        return this;
    }

    public SimulationSettingsBuilder setTheme(ThemeType themeType) {
        this.themeType = themeType;
        return this;
    }

    public SimulationSettings build() {
        Theme theme = ThemeFactory.create(themeType);
        return new SimulationSettings(boardWidth, boardHeight, theme, properties);
    }

}
