package org.example;

import java.util.Scanner;

public class PlayingProcess extends Fields {

    static boolean isPlayer1Turn;

    // Возвращает 1 если первым ходит Игрок 1 и 2 если первый Игрок 2
    public static int setTurn() {
        int turn;
        int firstPlayer = (int) (Math.random() * 2) + 1;
        boolean isPlayer1Turn = (firstPlayer == 1);
        if (isPlayer1Turn) {
            turn = 1;
        } else {
            turn = 2;
        }
        System.out.println("Игра началась!");
        return turn;
    }

    // Смена очереди
    public static int switchTurn(int turn) {
        return (turn == 1) ? 2 : 1;
    }

    // Парсим координаты для удара в массив интов. Переписать!!!!!!
//    public static int[][] parseCoordinates(String input) {
//        String[] pairs = input.split(";");
//        int[][] coordinates = new int[pairs.length][2];
//        try {
//            for (int i = 0; i < pairs.length; i++) {
//                String[] values = pairs[i].split(",");
//                coordinates[i][0] = Integer.parseInt(values[0]);
//                coordinates[i][1] = Integer.parseInt(values[1]);
//            }
//
//        } catch (NumberFormatException e) {
//            System.out.println("Вы ошиблись, повторите попытку");
//            getShotCoords();
//        }
//        return coordinates;
//    }

    // Считываем координаты для удара
    public static int[] getShotCoords() {
        System.out.println("Введите координаты для удара (формат: x1,y1):");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return parseCoordinates(input);
    }

    public static int[] parseCoordinates(String input) {
        int[] coordinates = new int[2];
        try {
            String[] values = input.split(","); // Разделяем строку по запятой
            if (values.length != 2) {
                throw new IllegalArgumentException();
            }
            coordinates[0] = Integer.parseInt(values[0]);  // x-координата
            coordinates[1] = Integer.parseInt(values[1]);  // y-координата
        } catch (RuntimeException e) {
            System.out.println("Введенные координаты некорректны. Сверьтесь с форматом и повторите попытку.");
            getShotCoords();
        }
        return coordinates;
    }

    // Проверяем, что координаты в границах поля. Репитативный метод!!!!!!!!!
    private static boolean isInBounds(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    // Возвращает true если утопил
    private static boolean isShipSunk(char[][] field, int x, int y) {
        field[x][y] = '3';

        boolean isSunk = true;

        if (x - 1 >= 0 && field[x - 1][y] == '1') { // Вверх
            isSunk = false;
        }
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
    public static void strike(char[][] playerField, char[][] playerBattleField, int turn) {
        boolean hit = false; // t если попал, f если мимо

        do {
            var coordinates = getShotCoords();

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
                            playerField[xShot][yShot] = '3'; // Добавить запись в playerButtleField
                            playerBattleField[xShot][yShot] = '3';
                            displayField(playerField);
                            displayField(playerBattleField);
                            hit = true;
                            break;

                        case '0': // Мимо
                        case '2': // Мимо
                            System.out.println("Мимо!");
                            playerField[xShot][yShot] = '4'; // Добавить запись в playerButtleField
                            playerBattleField[xShot][yShot] = '4';
                            displayField(playerField);
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
                    break;
                }
            } else {
                System.out.println("Координаты должны быть в диапазоне от 0 до 9.");
                hit = true;
                break;
            }
            // Проверка, остались ли ещё корабли на поле
            if (!isGameContinue(playerField)) {
                System.out.println("Все корабли потоплены! Игрок " + turn + " победил!");
                hit = false;
                break;  // Выход из цикла, так как игра завершена
            }
//            if (isGameContinue(playerField)) {
//                System.out.println("Корабли остались, продолжаем игру.");
//            } else {
//                System.out.println("Все корабли потоплены! Игрок " + turn + " победил!");
//                hit = false;
//                break;  // Выход из цикла, так как игра завершена
//            }
        } while (hit);
    }

    // Возвращает true если на поле кого-то из игроков остались корабли (игра продолжается)
    public static boolean isGameContinue(char[][] field) {
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


//      public void play() {
//            }

