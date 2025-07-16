package dev.zux13.settings;

import dev.zux13.theme.ThemeType;

import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

public class SimulationProperties {

    private final static String DEFAULT_PROPERTIES_FILE = "/simulation.properties";
    private final Properties properties;

    public SimulationProperties() {
        this(loadDefaultProperties());
    }

    public SimulationProperties(Properties properties) {
        this.properties = properties;
    }

    private static Properties loadDefaultProperties() {
        Properties props = new Properties();
        try (InputStream in = SimulationProperties.class.getResourceAsStream(DEFAULT_PROPERTIES_FILE)) {
            props.load(in);
        } catch (IOException e) {
            throw new UncheckedIOException("Failed to load simulation properties", e);
        }
        return props;
    }

    public ThemeType getDefaultTheme() {
        String value = properties.getProperty("theme.default", "FOREST").toUpperCase();
        try {
            return ThemeType.valueOf(value);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Unknown theme in properties: " + value, e);
        }
    }

    public int getBoardWidth() {
        return getInt("board.size.width", 20);
    }

    public int getBoardHeight() {
        return getInt("board.size.height", 10);
    }

    public int getMinBoardWidth() {
        return getInt("board.size.min.width", 20);
    }

    public int getMinBoardHeight() {
        return getInt("board.size.min.height", 10);
    }

    public double getRockDensity() {
        return getDouble("board.density.rock", 0.05);
    }

    public double getTreeDensity() {
        return getDouble("board.density.tree", 0.10);
    }

    public double getGrassDensity() {
        return getDouble("board.density.grass", 0.10);
    }

    public double getPredatorDensity() {
        return getDouble("board.density.predator", 0.005);
    }

    public double getHerbivoreDensity() {
        return getDouble("board.density.herbivore", 0.02);
    }

    public int getTickMillis() {
        return getInt("simulation.tick.millis", 500);
    }

    public double getGrassRespawnRate() {
        return getDouble("simulation.grass.respawnRate", 0.25);
    }

    public int getHerbivoreMinHp() {
        return getInt("creature.herbivore.hp.min", 20);
    }

    public int getHerbivoreMaxHp() {
        return getInt("creature.herbivore.hp.max", 40);
    }

    public int getHerbivoreMinSpeed() {
        return getInt("creature.herbivore.speed.min", 1);
    }

    public int getHerbivoreMaxSpeed() {
        return getInt("creature.herbivore.speed.max", 3);
    }

    public int getHerbivoreVisionMin() {
        return getInt("creature.herbivore.vision.min", 3);
    }

    public int getHerbivoreVisionMax() {
        return getInt("creature.herbivore.vision.max", 5);
    }

    public int getHerbivoreHealAmount() {
        return getInt("creature.herbivore.heal.amount", 10);
    }

    public int getHerbivoreHungerRestore() {
        return getInt("creature.herbivore.hunger.restore", 3);
    }

    public int getHerbivoreMaxHunger() {
        return getInt("creature.herbivore.hunger.max", 10);
    }

    public int getHerbivoreHungerDamage() {
        return getInt("creature.herbivore.hunger.damage", 1);
    }

    public int getPredatorMinHp() {
        return getInt("creature.predator.hp.min", 30);
    }

    public int getPredatorMaxHp() {
        return getInt("creature.predator.hp.max", 50);
    }

    public int getPredatorMinSpeed() {
        return getInt("creature.predator.speed.min", 2);
    }

    public int getPredatorMaxSpeed() {
        return getInt("creature.predator.speed.max", 4);
    }

    public int getPredatorVisionMin() {
        return getInt("creature.predator.vision.min", 3);
    }

    public int getPredatorVisionMax() {
        return getInt("creature.predator.vision.max", 6);
    }

    public int getPredatorHealAmount() {
        return getInt("creature.predator.heal.amount", 10);
    }

    public int getPredatorMinAttack() {
        return getInt("creature.predator.attack.min", 10);
    }

    public int getPredatorMaxAttack() {
        return getInt("creature.predator.attack.max", 20);
    }

    public int getPredatorHungerRestore() {
        return getInt("creature.predator.hunger.restore", 5);
    }

    public int getPredatorMaxHunger() {
        return getInt("creature.predator.hunger.max", 10);
    }

    public int getPredatorHungerDamage() {
        return getInt("creature.predator.hunger.damage", 1);
    }

    public String getRendererDividerChar() {
        return properties.getProperty("renderer.console.divider.char", "-");
    }

    public int getRendererLogWidth() {
        return getInt("renderer.console.log.width", 40);
    }

    private int getInt(String key, int defaultValue) {
        return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
    }

    private double getDouble(String key, double defaultValue) {
        return Double.parseDouble(properties.getProperty(key, String.valueOf(defaultValue)));
    }
}
