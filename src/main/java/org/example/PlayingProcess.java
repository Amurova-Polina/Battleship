package org.example;

import java.util.Random;
import java.util.Scanner;

public class PlayingProcess extends Fields {

    // Возвращает true, если первым ходит Игрок 1
    public static boolean isPlayer1Turn() {
        return new Random().nextBoolean();
    }

    // Считываем координаты для удара
    public static int[] getShotCoordinates() {
        System.out.println("Введите координаты для удара (формат: x1,y1):");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return parseCoordinates(input);
    }

    public static int[] parseCoordinates(String input) {
        try {
            String[] values = input.split(","); // Разделяем строку по запятой
            assert values.length == 2;
            int[] coordinates = new int[2];
            coordinates[0] = Integer.parseInt(values[0]); // x-координата
            coordinates[1] = Integer.parseInt(values[1]); // y-координата
            return coordinates;
        } catch (RuntimeException e) {
            System.out.println("Вы ввели некорректные координаты. Пожалуйста, сверьтесь с форматом и повторите попытку.");
            return getShotCoordinates();
        }
    }

    // Проверяем, что координаты в границах поля
    private static boolean isInBounds(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    // Возвращает true если утопил
    private static boolean isShipSunk(char[][] field, int x, int y) {
        field[x][y] = '3';

        boolean isSunk = x - 1 < 0 || field[x - 1][y] != '1';

        // Вверх
        if (x + 1 < field.length && field[x + 1][y] == '1') { // Вниз
            isSunk = false;
        }
        if (y - 1 >= 0 && field[x][y - 1] == '1') { // Влево
            isSunk = false;
        }
        if (y + 1 < field[x].length && field[x][y + 1] == '1') { // Вправо
            isSunk = false;
        }
        return isSunk;
    }

    // Делаем выстрелы, пока не промахнемся
    public static void strike(char[][] playerField, char[][] playerBattleField) {
        boolean hit; // t если попал, f если мимо

        do {
            var coordinates = getShotCoordinates();

            int xShot = coordinates[0];
            int yShot = coordinates[1];
            System.out.println(xShot + " " + yShot);

            if (isInBounds(xShot, yShot)) {    // Если координаты верные и по точке еще не стреляли, заходим в ифы
                if (playerField[xShot][yShot] != '3' && playerField[xShot][yShot] != '4') {
                    switch (playerField[xShot][yShot]) {
                        case '1': // Попадание
                            if (isShipSunk(playerField, xShot, yShot)) {
                                System.out.println("Утопил!");
                            } else {
                                System.out.println("Попадание!");
                            }
                            playerField[xShot][yShot] = '3'; // Добавить запись в playerBattleField
                            playerBattleField[xShot][yShot] = '3';
                            //displayField(playerField);
                            displayField(playerBattleField);
                            hit = true;
                            break;

                        case '0': // Мимо
                        case '2': // Мимо
                            System.out.println("Мимо!");
                            playerField[xShot][yShot] = '4'; // Добавить запись в playerBattleField
                            playerBattleField[xShot][yShot] = '4';
                            //displayField(playerField);
                            displayField(playerBattleField);
                            hit = false;
                            break;
                        default:
                            System.out.println("Произошла непредвиденная ошибка. Попробуйте ещё раз!");
                            displayField(playerField);
                            displayField(playerBattleField);
                            hit = true;
                            break;
                    }
                } else {
                    System.out.println("Вы уже били по этой точке. Выберите другие координаты.");
                    hit = true;
                    continue;
                }
            } else {
                System.out.println("Координаты должны быть в диапазоне от 0 до 9.");
                hit = true;
                continue;
            }
            // Проверка, остались ли ещё корабли на поле
            if (!hasShips(playerField)) {
                break;  // Выход из цикла, так как игра завершена
            }
        } while (hit);
    }

    // Возвращает true, если на поле остались корабли (игра продолжается)
    public static boolean hasShips(char[][] field) {
        for (char[] row : field) {
            for (char cell : row) {
                if (cell == '1') { // Если есть хотя бы один корабль
                    return true;
                }
            }
        }
        return false;
    }

}