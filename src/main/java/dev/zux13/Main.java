package dev.zux13;

import dev.zux13.action.*;
import dev.zux13.map.WorldMap;
import dev.zux13.renderer.ConsoleRenderer;
import dev.zux13.renderer.Renderer;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        WorldMap worldMap = new WorldMap(20, 20);
        Renderer renderer = new ConsoleRenderer();

        Action[] initActions = {new GenerateAction(), new RegrowAction(), new RespawnAction()};
        Action[] turnActions = {new MoveAction(), new RegrowAction(), new RespawnAction()};

        Simulation simulation = new Simulation(worldMap, renderer, initActions, turnActions);
        simulation.startSimulation();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("Введите команду: Pause, Resume, Stop, Next:");
            String command = scanner.nextLine().trim().toLowerCase();
            switch (command) {
                case "pause" -> simulation.pauseSimulation();
                case "resume" -> simulation.resumeSimulation();
                case "stop" -> {
                    simulation.stopSimulation();
                    scanner.close();
                    return;
                }
                case "next" -> simulation.nextTurn();
                default -> System.out.println("Неизвестная команда: " + command);
            }
        }
    }
}
