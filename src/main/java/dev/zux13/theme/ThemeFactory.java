package dev.zux13.theme;

public class ThemeFactory {

    public static Theme create(ThemeType themeType) {
        return switch (themeType) {
            case FOREST -> new ForestTheme();
            case DESERT -> new DesertTheme();
            case SNOW   -> new SnowTheme();
            case STEPPE -> new SteppeTheme();
        };
    }
}
