package org.example;

import java.util.Random;
import java.util.Scanner;

public class PlayingProcess {

    private boolean isPlayer1Turn;

    // Выясняем, кто ходит первым
    public void setTurn() {
        isPlayer1Turn = new Random().nextBoolean();
        System.out.println("Игра началась! Первый ход делает " + (isPlayer1Turn ? "Игрок 1" : "Игрок 2"));
    }

    // Смена очереди
    private void switchTurn() {
        isPlayer1Turn = !isPlayer1Turn;
    }

    // Парсим координаты для удара в массив интов. Переписать!!!!!!
    public static int[][] parseCoordinates(String input) {
        String[] pairs = input.split(";");
        int[][] coordinates = new int[pairs.length][2];
        try {
            for (int i = 0; i < pairs.length; i++) {
                String[] values = pairs[i].split(",");
                coordinates[i][0] = Integer.parseInt(values[0]);
                coordinates[i][1] = Integer.parseInt(values[1]);
            }

        } catch (NumberFormatException e) {
            System.out.println("Вы ошиблись, повторите попытку");
            getShotCoords();
        }
        return coordinates;
    }

    // Считываем координаты для удара
    public static int[][] getShotCoords() {
        System.out.println("Введите координаты для удара (формат: x1,y1):");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        return parseCoordinates(input);
    }

    // Проверяем, что координаты в границах поля. Репитативный метод!!!!!!!!!
    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    // Делаем выстрелы, пока не промахнемся
    public char[][] strike(char[][] playerField) {
        boolean hit = false; // t если попал, f если мимо

        do {
            var coordinates = getShotCoords();

            for (int[] coord : coordinates) {
                int xShot = coord[0];
                int yShot = coord[1];
                System.out.println(xShot + " " + yShot);


                if (isInBounds(xShot, yShot)) {    // Если координаты верные и по точке еще не стреляли, заходим в ифы
                    if (playerField[xShot][yShot] != '3' && playerField[xShot][yShot] != '4') {
                        switch (playerField[xShot][yShot]) {
                            case '1': // Попадание
                                System.out.println("Попадание!");
                                playerField[xShot][yShot] = '3'; // Добавить запись в playerButtleField
                                hit = true;
                                break;

                            case '0': // Мимо
                            case '2': // Мимо
                                System.out.println("Мимо!");
                                playerField[xShot][yShot] = '4'; // Добавить запись в playerButtleField
                                switchTurn();
                                hit = false;
                                break;

                            default:
                                System.out.println("Произошла непредвиденная ошибка. Попробуйте ещё раз!");
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
            }
        } while (hit);
        return playerField;
    }
}


//      public void play() {
//            }

