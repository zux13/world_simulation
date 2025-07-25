package dev.zux13.settings;

import dev.zux13.theme.Theme;
import dev.zux13.theme.ThemeFactory;
import lombok.Getter;

@Getter
public class SimulationSettingsBuilder {

    // Board settings
    private int boardWidth;
    private int boardHeight;
    private int minWidth;
    private int maxWidth;
    private int minHeight;
    private int maxHeight;

    // Theme
    private String themeName;

    // Densities
    private double rockDensity;
    private double treeDensity;
    private double grassDensity;
    private double predatorDensity;
    private double herbivoreDensity;

    // Simulation general
    private int tickMillis;
    private double grassRespawnRate;

    // Herbivore stats
    private int herbivoreMinHp;
    private int herbivoreMaxHp;
    private int herbivoreMinSpeed;
    private int herbivoreMaxSpeed;
    private int herbivoreVisionMin;
    private int herbivoreVisionMax;
    private int herbivoreHealRestore;
    private int herbivoreHungerRestore;
    private int herbivoreMaxHunger;
    private int herbivoreHungerDamage;

    // Predator stats
    private int predatorMinHp;
    private int predatorMaxHp;
    private int predatorMinSpeed;
    private int predatorMaxSpeed;
    private int predatorVisionMin;
    private int predatorVisionMax;
    private int predatorHealRestore;
    private int predatorMinAttack;
    private int predatorMaxAttack;
    private int predatorHungerRestore;
    private int predatorMaxHunger;
    private int predatorHungerDamage;

    // Renderer
    private String rendererDividerChar;
    private int rendererLogWidth;

    public SimulationSettings build() {
        Theme theme = ThemeFactory.getTheme(themeName);
        return new SimulationSettings(
            boardWidth, boardHeight, theme, rockDensity, treeDensity, grassDensity,
            predatorDensity, herbivoreDensity, tickMillis, grassRespawnRate,
            herbivoreMinHp, herbivoreMaxHp, herbivoreMinSpeed, herbivoreMaxSpeed,
            herbivoreVisionMin, herbivoreVisionMax, herbivoreHealRestore,
            herbivoreHungerRestore, herbivoreMaxHunger, herbivoreHungerDamage,
            predatorMinHp, predatorMaxHp, predatorMinSpeed, predatorMaxSpeed,
            predatorVisionMin, predatorVisionMax, predatorHealRestore,
            predatorMinAttack, predatorMaxAttack, predatorHungerRestore,
            predatorMaxHunger, predatorHungerDamage, rendererDividerChar, rendererLogWidth
        );
    }

    public SimulationSettingsBuilder boardWidth(int boardWidth) {
        if (boardWidth < minWidth || boardWidth > maxWidth) {
            throw new IllegalArgumentException("Width must be in range [%d;%d]".formatted(minWidth, maxWidth));
        }
        this.boardWidth = boardWidth;
        return this;
    }

    public SimulationSettingsBuilder boardHeight(int boardHeight) {
        if (boardHeight < minHeight || boardHeight > maxHeight) {
            throw new IllegalArgumentException("Height must be in range [%d;%d]".formatted(minHeight, maxHeight));
        }
        this.boardHeight = boardHeight;
        return this;
    }

    public SimulationSettingsBuilder minWidth(int minWidth) {
        this.minWidth = minWidth;
        return this;
    }

    public SimulationSettingsBuilder maxWidth(int maxWidth) {
        this.maxWidth = maxWidth;
        return this;
    }

    public SimulationSettingsBuilder minHeight(int minHeight) {
        this.minHeight = minHeight;
        return this;
    }

    public SimulationSettingsBuilder maxHeight(int maxHeight) {
        this.maxHeight = maxHeight;
        return this;
    }

    public SimulationSettingsBuilder themeName(String themeName) {
        this.themeName = themeName;
        return this;
    }

    public SimulationSettingsBuilder rockDensity(double rockDensity) {
        this.rockDensity = rockDensity;
        return this;
    }

    public SimulationSettingsBuilder treeDensity(double treeDensity) {
        this.treeDensity = treeDensity;
        return this;
    }

    public SimulationSettingsBuilder grassDensity(double grassDensity) {
        this.grassDensity = grassDensity;
        return this;
    }

    public SimulationSettingsBuilder predatorDensity(double predatorDensity) {
        this.predatorDensity = predatorDensity;
        return this;
    }

    public SimulationSettingsBuilder herbivoreDensity(double herbivoreDensity) {
        this.herbivoreDensity = herbivoreDensity;
        return this;
    }

    public SimulationSettingsBuilder tickMillis(int tickMillis) {
        this.tickMillis = tickMillis;
        return this;
    }

    public SimulationSettingsBuilder grassRespawnRate(double grassRespawnRate) {
        this.grassRespawnRate = grassRespawnRate;
        return this;
    }

    public SimulationSettingsBuilder herbivoreMinHp(int herbivoreMinHp) {
        this.herbivoreMinHp = herbivoreMinHp;
        return this;
    }

    public SimulationSettingsBuilder herbivoreMaxHp(int herbivoreMaxHp) {
        this.herbivoreMaxHp = herbivoreMaxHp;
        return this;
    }

    public SimulationSettingsBuilder herbivoreMinSpeed(int herbivoreMinSpeed) {
        this.herbivoreMinSpeed = herbivoreMinSpeed;
        return this;
    }

    public SimulationSettingsBuilder herbivoreMaxSpeed(int herbivoreMaxSpeed) {
        this.herbivoreMaxSpeed = herbivoreMaxSpeed;
        return this;
    }

    public SimulationSettingsBuilder herbivoreVisionMin(int herbivoreVisionMin) {
        this.herbivoreVisionMin = herbivoreVisionMin;
        return this;
    }

    public SimulationSettingsBuilder herbivoreVisionMax(int herbivoreVisionMax) {
        this.herbivoreVisionMax = herbivoreVisionMax;
        return this;
    }

    public SimulationSettingsBuilder herbivoreHealRestore(int herbivoreHealRestore) {
        this.herbivoreHealRestore = herbivoreHealRestore;
        return this;
    }

    public SimulationSettingsBuilder herbivoreHungerRestore(int herbivoreHungerRestore) {
        this.herbivoreHungerRestore = herbivoreHungerRestore;
        return this;
    }

    public SimulationSettingsBuilder herbivoreMaxHunger(int herbivoreMaxHunger) {
        this.herbivoreMaxHunger = herbivoreMaxHunger;
        return this;
    }

    public SimulationSettingsBuilder herbivoreHungerDamage(int herbivoreHungerDamage) {
        this.herbivoreHungerDamage = herbivoreHungerDamage;
        return this;
    }

    public SimulationSettingsBuilder predatorMinHp(int predatorMinHp) {
        this.predatorMinHp = predatorMinHp;
        return this;
    }

    public SimulationSettingsBuilder predatorMaxHp(int predatorMaxHp) {
        this.predatorMaxHp = predatorMaxHp;
        return this;
    }

    public SimulationSettingsBuilder predatorMinSpeed(int predatorMinSpeed) {
        this.predatorMinSpeed = predatorMinSpeed;
        return this;
    }

    public SimulationSettingsBuilder predatorMaxSpeed(int predatorMaxSpeed) {
        this.predatorMaxSpeed = predatorMaxSpeed;
        return this;
    }

    public SimulationSettingsBuilder predatorVisionMin(int predatorVisionMin) {
        this.predatorVisionMin = predatorVisionMin;
        return this;
    }

    public SimulationSettingsBuilder predatorVisionMax(int predatorVisionMax) {
        this.predatorVisionMax = predatorVisionMax;
        return this;
    }

    public SimulationSettingsBuilder predatorHealRestore(int predatorHealRestore) {
        this.predatorHealRestore = predatorHealRestore;
        return this;
    }

    public SimulationSettingsBuilder predatorMinAttack(int predatorMinAttack) {
        this.predatorMinAttack = predatorMinAttack;
        return this;
    }

    public SimulationSettingsBuilder predatorMaxAttack(int predatorMaxAttack) {
        this.predatorMaxAttack = predatorMaxAttack;
        return this;
    }

    public SimulationSettingsBuilder predatorHungerRestore(int predatorHungerRestore) {
        this.predatorHungerRestore = predatorHungerRestore;
        return this;
    }

    public SimulationSettingsBuilder predatorMaxHunger(int predatorMaxHunger) {
        this.predatorMaxHunger = predatorMaxHunger;
        return this;
    }

    public SimulationSettingsBuilder predatorHungerDamage(int predatorHungerDamage) {
        this.predatorHungerDamage = predatorHungerDamage;
        return this;
    }

    public SimulationSettingsBuilder rendererDividerChar(String rendererDividerChar) {
        this.rendererDividerChar = rendererDividerChar;
        return this;
    }

    public SimulationSettingsBuilder rendererLogWidth(int rendererLogWidth) {
        this.rendererLogWidth = rendererLogWidth;
        return this;
    }
}
