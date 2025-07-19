package dev.zux13.util;

import dev.zux13.board.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Утилитные методы для работы с координатами на игровом поле.
 */
public class CoordinateUtils {

    /**
     * Проверяет, находится ли точка {@code to} в пределах радиуса {@code radius} от точки {@code from}.
     * Расчёт ведётся по евклидовой дистанции в квадрате (без извлечения корня).
     *
     * @param from   начальная точка
     * @param to     целевая точка
     * @param radius радиус видимости
     * @return {@code true}, если {@code to} находится в пределах {@code radius}, иначе {@code false}
     */
    public static boolean isWithinRadius(Coordinate from, Coordinate to, int radius) {
        int dx = from.x() - to.x();
        int dy = from.y() - to.y();
        return dx * dx + dy * dy <= radius * radius;
    }

    /**
     * Возвращает список координат всех соседей (по 8 направлениям), находящихся в пределах границ.
     *
     * @param center центр, вокруг которого ищутся соседи
     * @param width  ширина карты
     * @param height высота карты
     * @return список координат соседних клеток (максимум 8)
     */
    public static List<Coordinate> getNeighborsInBounds(Coordinate center, int width, int height) {
        List<Coordinate> neighbors = new ArrayList<>(8);

        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;

                int nx = center.x() + dx;
                int ny = center.y() + dy;

                if (nx >= 0 && nx < width && ny >= 0 && ny < height) {
                    neighbors.add(Coordinate.of(nx, ny));
                }
            }
        }

        return neighbors;
    }

    /**
     * Генерирует полный список координат сетки заданной ширины и высоты.
     * Список заполняется в порядке сверху вниз, слева направо.
     *
     * @param width  ширина карты
     * @param height высота карты
     * @return список всех координат на поле
     */
    public static List<Coordinate> generateGrid(int width, int height) {
        return IntStream.range(0, height)
                .boxed()
                .flatMap(y -> IntStream.range(0, width)
                        .mapToObj(x -> Coordinate.of(x, y)))
                .toList();
    }

    /**
     * Вычисляет манхэттенское расстояние между двумя координатами.
     * Используется в A* и других простых эвристиках.
     *
     * @param a первая координата
     * @param b вторая координата
     * @return расстояние |x1 - x2| + |y1 - y2|
     */
    public static int manhattanDistance(Coordinate a, Coordinate b) {
        return Math.abs(a.x() - b.x()) + Math.abs(a.y() - b.y());
    }

    /**
     * Вычисляет диагональное расстояние между двумя координатами.
     * Используется в A* для 8-направленного движения.
     *
     * @param a первая координата
     * @param b вторая координата
     * @return диагональное расстояние
     */
    public static int diagonalDistance(Coordinate a, Coordinate b) {
        int dx = Math.abs(a.x() - b.x());
        int dy = Math.abs(a.y() - b.y());
        // D = 10 (стоимость прямого хода), D2 = 14 (стоимость диагонального)
        return 10 * (dx + dy) + (14 - 2 * 10) * Math.min(dx, dy);
    }

    /**
     * Возвращает стоимость движения между двумя соседними клетками.
     *
     * @param a первая координата
     * @param b вторая координата
     * @return 10 для прямого хода, 14 для диагонального
     */
    public static int getMovementCost(Coordinate a, Coordinate b) {
        int dx = Math.abs(a.x() - b.x());
        int dy = Math.abs(a.y() - b.y());
        return (dx == 1 && dy == 1) ? 14 : 10;
    }

    public static boolean isWithinBounds(int x, int y, int width, int height) {
        return x >= 0 && x < width && y >= 0 && y < height;
    }
}
