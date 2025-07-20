package dev.zux13.theme;

import com.google.gson.Gson;
import dev.zux13.util.ResourceUtils;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ThemeFactory {

    private static final Map<String, Theme> THEMES = new ConcurrentHashMap<>();

    static {
        loadThemes();
    }

    private static void loadThemes() {
        createAndAddTextTheme();

        Gson gson = new Gson();
        Path themesPath = ResourceUtils.getPath("themes");

        if (Files.isDirectory(themesPath)) {
            try {
                Files.list(themesPath)
                        .filter(p -> p.toString().endsWith(".json"))
                        .forEach(p -> {
                            try (Reader reader = Files.newBufferedReader(p)) {
                                JsonTheme theme = gson.fromJson(reader, JsonTheme.class);
                                theme.init();
                                THEMES.put(theme.getName().toUpperCase(), theme);
                            } catch (Exception e) {
                                System.err.println("Failed to load theme: " + p.getFileName() + " - " + e.getMessage());
                            }
                        });
            } catch (Exception e) {
                System.err.println("Error loading themes from " + themesPath.toAbsolutePath() + " - " + e.getMessage());
            }
        }

        if (THEMES.size() <= 1 && !Files.isDirectory(themesPath)) {
            System.err.println("WARNING: 'themes' directory not found. Only the default text-based theme is available.");
        }
    }

    private static void createAndAddTextTheme() {
        JsonTheme textTheme = new JsonTheme();
        textTheme.setName("Text");

        Map<String, String> sprites = new ConcurrentHashMap<>();
        sprites.put("predator", "Pr");
        sprites.put("herbivore", "Hb");
        sprites.put("grass", "//");
        sprites.put("rock", "▓▓");
        sprites.put("tree", "木");
        sprites.put("default", "__");
        textTheme.setSpriteMap(sprites);

        Map<EmojiType, String> emojis = new ConcurrentHashMap<>();
        emojis.put(EmojiType.HEALTH, "++");
        emojis.put(EmojiType.SPEED, "»»");
        emojis.put(EmojiType.VISION, "◉◉");
        emojis.put(EmojiType.ATTACK, "><");
        emojis.put(EmojiType.HUNGER, "~~");
        emojis.put(EmojiType.DEATH, " †");
        emojis.put(EmojiType.SLEEP, "Zz");
        emojis.put(EmojiType.UP, " ↑");
        emojis.put(EmojiType.DOWN, " ↓");
        textTheme.setEmojiMap(emojis);

        textTheme.init();
        THEMES.put(textTheme.getName().toUpperCase(), textTheme);
    }

    public static Theme getTheme(String name) {
        return THEMES.get(name.toUpperCase());
    }

    public static List<String> getAvailableThemes() {
        return new ArrayList<>(THEMES.keySet());
    }

}
