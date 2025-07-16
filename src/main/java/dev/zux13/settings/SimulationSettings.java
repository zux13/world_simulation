package dev.zux13.settings;

import dev.zux13.theme.Theme;

public class SimulationSettings {
    private final int boardWidth;
    private final int boardHeight;
    private final Theme theme;

    private final double rockDensity;
    private final double treeDensity;
    private final double grassDensity;
    private final double predatorDensity;
    private final double herbivoreDensity;

    private final int tickMillis;
    private final double grassRespawnRate;

    private final int herbivoreMinHp;
    private final int herbivoreMaxHp;
    private final int herbivoreMinSpeed;
    private final int herbivoreMaxSpeed;
    private final int herbivoreVisionMin;
    private final int herbivoreVisionMax;
    private final int herbivoreHealAmount;
    private final int herbivoreHungerRestore;
    private final int herbivoreMaxHunger;
    private final int herbivoreHungerDamage;

    private final int predatorMinHp;
    private final int predatorMaxHp;
    private final int predatorMinSpeed;
    private final int predatorMaxSpeed;
    private final int predatorVisionMin;
    private final int predatorVisionMax;
    private final int predatorHealAmount;
    private final int predatorMinAttack;
    private final int predatorMaxAttack;
    private final int predatorHungerRestore;
    private final int predatorMaxHunger;
    private final int predatorHungerDamage;

    private final String rendererDividerChar;
    private final int rendererLogWidth;

    public SimulationSettings(
            int boardWidth,
            int boardHeight,
            Theme theme,
            SimulationProperties properties
    ) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        this.theme = theme;

        this.rockDensity = properties.getRockDensity();
        this.treeDensity = properties.getTreeDensity();
        this.grassDensity = properties.getGrassDensity();
        this.predatorDensity = properties.getPredatorDensity();
        this.herbivoreDensity = properties.getHerbivoreDensity();

        this.tickMillis = properties.getTickMillis();
        this.grassRespawnRate = properties.getGrassRespawnRate();

        this.herbivoreMinHp = properties.getHerbivoreMinHp();
        this.herbivoreMaxHp = properties.getHerbivoreMaxHp();
        this.herbivoreMinSpeed = properties.getHerbivoreMinSpeed();
        this.herbivoreMaxSpeed = properties.getHerbivoreMaxSpeed();
        this.herbivoreVisionMin = properties.getHerbivoreVisionMin();
        this.herbivoreVisionMax = properties.getHerbivoreVisionMax();
        this.herbivoreHealAmount = properties.getHerbivoreHealAmount();
        this.herbivoreHungerRestore = properties.getHerbivoreHungerRestore();
        this.herbivoreMaxHunger = properties.getHerbivoreMaxHunger();
        this.herbivoreHungerDamage = properties.getHerbivoreHungerDamage();

        this.predatorMinHp = properties.getPredatorMinHp();
        this.predatorMaxHp = properties.getPredatorMaxHp();
        this.predatorMinSpeed = properties.getPredatorMinSpeed();
        this.predatorMaxSpeed = properties.getPredatorMaxSpeed();
        this.predatorVisionMin = properties.getPredatorVisionMin();
        this.predatorVisionMax = properties.getPredatorVisionMax();
        this.predatorHealAmount = properties.getPredatorHealAmount();
        this.predatorMinAttack = properties.getPredatorMinAttack();
        this.predatorMaxAttack = properties.getPredatorMaxAttack();
        this.predatorHungerRestore = properties.getPredatorHungerRestore();
        this.predatorMaxHunger = properties.getPredatorMaxHunger();
        this.predatorHungerDamage = properties.getPredatorHungerDamage();

        this.rendererDividerChar = properties.getRendererDividerChar();
        this.rendererLogWidth = properties.getRendererLogWidth();
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public Theme getTheme() {
        return theme;
    }

    public double getRockDensity() {
        return rockDensity;
    }

    public double getTreeDensity() {
        return treeDensity;
    }

    public double getGrassDensity() {
        return grassDensity;
    }

    public double getPredatorDensity() {
        return predatorDensity;
    }

    public double getHerbivoreDensity() {
        return herbivoreDensity;
    }

    public int getTickMillis() {
        return tickMillis;
    }

    public double getGrassRespawnRate() {
        return grassRespawnRate;
    }

    public int getHerbivoreMinHp() {
        return herbivoreMinHp;
    }

    public int getHerbivoreMaxHp() {
        return herbivoreMaxHp;
    }

    public int getHerbivoreMinSpeed() {
        return herbivoreMinSpeed;
    }

    public int getHerbivoreMaxSpeed() {
        return herbivoreMaxSpeed;
    }

    public int getHerbivoreVisionMin() {
        return herbivoreVisionMin;
    }

    public int getHerbivoreVisionMax() {
        return herbivoreVisionMax;
    }

    public int getHerbivoreHealAmount() {
        return herbivoreHealAmount;
    }

    public int getHerbivoreHungerRestore() {
        return herbivoreHungerRestore;
    }

    public int getHerbivoreMaxHunger() {
        return herbivoreMaxHunger;
    }

    public int getHerbivoreHungerDamage() {
        return herbivoreHungerDamage;
    }

    public int getPredatorMinHp() {
        return predatorMinHp;
    }

    public int getPredatorMaxHp() {
        return predatorMaxHp;
    }

    public int getPredatorMinSpeed() {
        return predatorMinSpeed;
    }

    public int getPredatorMaxSpeed() {
        return predatorMaxSpeed;
    }

    public int getPredatorVisionMin() {
        return predatorVisionMin;
    }

    public int getPredatorVisionMax() {
        return predatorVisionMax;
    }

    public int getPredatorHealAmount() {
        return predatorHealAmount;
    }

    public int getPredatorMinAttack() {
        return predatorMinAttack;
    }

    public int getPredatorMaxAttack() {
        return predatorMaxAttack;
    }

    public int getPredatorHungerRestore() {
        return predatorHungerRestore;
    }

    public int getPredatorMaxHunger() {
        return predatorMaxHunger;
    }

    public int getPredatorHungerDamage() {
        return predatorHungerDamage;
    }

    public String getRendererDividerChar() {
        return rendererDividerChar;
    }

    public int getRendererLogWidth() {
        return  rendererLogWidth;
    }
}
