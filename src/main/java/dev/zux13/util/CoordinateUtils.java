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
     * Вычисляет псевдослучайную "точку блуждания" для существа на основе текущего времени и хэша существа.
     * Угол и радиус варьируются, чтобы избежать синхронного движения.
     *
     * @param current       текущая позиция
     * @param width         ширина карты
     * @param height        высота карты
     * @param creatureHash  уникальный хэш существа
     * @return координата цели блуждания в пределах карты
     */
    public static Coordinate getRoamTarget(Coordinate current, int width, int height, int creatureHash) {
        long periodMillis = 50;
        long now = System.currentTimeMillis();
        long seed = now / periodMillis + creatureHash;

        double baseAngle = (seed % 360) * (Math.PI / 180);
        double noise = ((creatureHash >> 3) % 30 - 15) * (Math.PI / 180);
        double angle = baseAngle + noise;

        int radius = 3 + Math.abs(creatureHash % 5);

        int dx = (int) Math.round(Math.cos(angle) * radius);
        int dy = (int) Math.round(Math.sin(angle) * radius);

        int targetX = clamp(current.x() + dx, 0, width - 1);
        int targetY = clamp(current.y() + dy, 0, height - 1);

        return Coordinate.of(targetX, targetY);
    }

    /**
     * Ограничивает значение в пределах [min, max].
     *
     * @param val значение
     * @param min нижняя граница
     * @param max верхняя граница
     * @return значение, ограниченное заданным диапазоном
     */
    private static int clamp(int val, int min, int max) {
        return Math.max(min, Math.min(max, val));
    }
}
