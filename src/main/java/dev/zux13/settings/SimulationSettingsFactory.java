package dev.zux13.settings;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SimulationSettingsFactory {

    private static final String SIMULATION_PROPERTIES_PATH = "external-resources/simulation.properties";

    public SimulationSettings createFromDefaults() {
        return createBuilderFromDefaults().build();
    }

    public SimulationSettingsBuilder createBuilderFromDefaults() {
        Properties properties = loadProperties();
        SimulationSettingsBuilder builder = new SimulationSettingsBuilder();

        builder.minWidth(getInt(properties, "board.size.min.width"));
        builder.maxWidth(getInt(properties, "board.size.max.width"));
        builder.minHeight(getInt(properties, "board.size.min.height"));
        builder.maxHeight(getInt(properties, "board.size.max.height"));
        builder.boardWidth(getInt(properties, "board.size.width"));
        builder.boardHeight(getInt(properties, "board.size.height"));
        builder.rockDensity(getDouble(properties, "board.density.rock"));
        builder.treeDensity(getDouble(properties, "board.density.tree"));
        builder.grassDensity(getDouble(properties, "board.density.grass"));
        builder.predatorDensity(getDouble(properties, "board.density.predator"));
        builder.herbivoreDensity(getDouble(properties, "board.density.herbivore"));
        builder.tickMillis(getInt(properties, "simulation.tick.millis"));
        builder.grassRespawnRate(getDouble(properties, "simulation.grass.respawnRate"));
        builder.themeName(getString(properties, "theme.default"));

        builder.herbivoreMinHp(getInt(properties, "creature.herbivore.hp.min"));
        builder.herbivoreMaxHp(getInt(properties, "creature.herbivore.hp.max"));
        builder.herbivoreHealRestore(getInt(properties, "creature.herbivore.heal.restore"));
        builder.herbivoreHungerRestore(getInt(properties, "creature.herbivore.hunger.restore"));
        builder.herbivoreMaxHunger(getInt(properties, "creature.herbivore.hunger.max"));
        builder.herbivoreHungerDamage(getInt(properties, "creature.herbivore.hunger.damage"));
        builder.herbivoreMinSpeed(getInt(properties, "creature.herbivore.speed.min"));
        builder.herbivoreMaxSpeed(getInt(properties, "creature.herbivore.speed.max"));
        builder.herbivoreVisionMin(getInt(properties, "creature.herbivore.vision.min"));
        builder.herbivoreVisionMax(getInt(properties, "creature.herbivore.vision.max"));

        builder.predatorMinHp(getInt(properties, "creature.predator.hp.min"));
        builder.predatorMaxHp(getInt(properties, "creature.predator.hp.max"));
        builder.predatorHealRestore(getInt(properties, "creature.predator.heal.restore"));
        builder.predatorHungerRestore(getInt(properties, "creature.predator.hunger.restore"));
        builder.predatorMaxHunger(getInt(properties, "creature.predator.hunger.max"));
        builder.predatorHungerDamage(getInt(properties, "creature.predator.hunger.damage"));
        builder.predatorMinSpeed(getInt(properties, "creature.predator.speed.min"));
        builder.predatorMaxSpeed(getInt(properties, "creature.predator.speed.max"));
        builder.predatorVisionMin(getInt(properties, "creature.predator.vision.min"));
        builder.predatorVisionMax(getInt(properties, "creature.predator.vision.max"));
        builder.predatorMinAttack(getInt(properties, "creature.predator.attack.min"));
        builder.predatorMaxAttack(getInt(properties, "creature.predator.attack.max"));

        builder.rendererDividerChar(getString(properties, "renderer.console.divider.char"));
        builder.rendererLogWidth(getInt(properties, "renderer.console.log.width"));

        return builder;
    }

    private Properties loadProperties() {
        Properties properties = new Properties();
        try (InputStream input = new FileInputStream(SIMULATION_PROPERTIES_PATH)) {
            properties.load(input);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load simulation properties from " + SIMULATION_PROPERTIES_PATH, e);
        }
        return properties;
    }

    private String getString(Properties properties, String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '%s' not found in %s".formatted(key, SIMULATION_PROPERTIES_PATH));
        }
        return value;
    }

    private int getInt(Properties properties, String key) {
        return Integer.parseInt(getString(properties, key));
    }

    private double getDouble(Properties properties, String key) {
        return Double.parseDouble(getString(properties, key));
    }
}
