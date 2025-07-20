package dev.zux13.util;

import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ResourceUtils {

    private static final Path BASE_PATH = findBasePath();

    private static Path findBasePath() {
        try {
            URL location = ResourceUtils.class.getProtectionDomain().getCodeSource().getLocation();
            Path path = Paths.get(location.toURI());

            if (path.toString().endsWith(".jar")) {
                return path.getParent();
            } else {
                return Paths.get("external-resources");
            }
        } catch (Exception e) {
            throw new IllegalStateException("Could not determine application base path.", e);
        }
    }

    public static Path getPath(String... pathElements) {
        if (pathElements.length == 0) {
            return BASE_PATH;
        }
        return Paths.get(BASE_PATH.toString(), pathElements);
    }
}
