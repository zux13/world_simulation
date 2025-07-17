package dev.zux13.theme;

import com.google.gson.Gson;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.JarURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarFile;

public class ThemeFactory {

    private static final String THEMES_DIR = "themes";
    private static final Map<String, Theme> THEMES = new ConcurrentHashMap<>();

    static {
        loadThemes();
    }

    private static void loadThemes() {
        Gson gson = new Gson();

        try {
            Enumeration<URL> urls = ThemeFactory.class.getClassLoader().getResources(THEMES_DIR);
            while (urls.hasMoreElements()) {
                URL resource = urls.nextElement();

                if ("jar".equals(resource.getProtocol())) {
                    JarURLConnection jarConnection = (JarURLConnection) resource.openConnection();
                    JarFile jarFile = jarConnection.getJarFile();
                    String entryPrefix = jarConnection.getEntryName() + "/";

                    jarFile.stream()
                            .filter(entry -> entry.getName().startsWith(entryPrefix) && entry.getName().endsWith(".json"))
                            .forEach(entry -> {
                                try (InputStream is = ThemeFactory.class.getResourceAsStream("/" + entry.getName())) {
                                    JsonTheme theme = gson.fromJson(new InputStreamReader(is), JsonTheme.class);
                                    theme.init();
                                    THEMES.put(theme.getName().toUpperCase(), theme);
                                } catch (Exception e) {
                                    System.err.println("Failed to load theme: " + entry.getName());
                                    e.printStackTrace();
                                }
                            });

                } else {
                    Path path = Paths.get(resource.toURI());
                    if (Files.isDirectory(path)) {
                        Files.list(path)
                                .filter(p -> p.toString().endsWith(".json"))
                                .forEach(p -> {
                                    try (Reader reader = Files.newBufferedReader(p)) {
                                        JsonTheme theme = gson.fromJson(reader, JsonTheme.class);
                                        theme.init();
                                        THEMES.put(theme.getName().toUpperCase(), theme);
                                    } catch (Exception e) {
                                        System.err.println("Failed to load theme: " + p.getFileName());
                                        e.printStackTrace();
                                    }
                                });
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Error loading themes.");
            e.printStackTrace();
        }

        if (THEMES.isEmpty()) {
            System.err.println("No theme files found in resources/themes/");
        }
    }

    public static Theme getTheme(String name) {
        return THEMES.get(name.toUpperCase());
    }

    public static List<String> getAvailableThemes() {
        return new ArrayList<>(THEMES.keySet());
    }

}
