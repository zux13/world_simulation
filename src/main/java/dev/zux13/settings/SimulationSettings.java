package dev.zux13.settings;

import dev.zux13.theme.Theme;

public record SimulationSettings(int boardWidth, int boardHeight, Theme theme, double rockDensity, double treeDensity,
                                 double grassDensity, double predatorDensity, double herbivoreDensity, int tickMillis,
                                 double grassRespawnRate, int herbivoreMinHp, int herbivoreMaxHp, int herbivoreMinSpeed,
                                 int herbivoreMaxSpeed, int herbivoreVisionMin, int herbivoreVisionMax,
                                 int herbivoreHealRestore, int herbivoreHungerRestore, int herbivoreMaxHunger,
                                 int herbivoreHungerDamage, int predatorMinHp, int predatorMaxHp, int predatorMinSpeed,
                                 int predatorMaxSpeed, int predatorVisionMin, int predatorVisionMax,
                                 int predatorHealRestore, int predatorMinAttack, int predatorMaxAttack,
                                 int predatorHungerRestore, int predatorMaxHunger, int predatorHungerDamage,
                                 String rendererDividerChar, int rendererLogWidth) {
}
