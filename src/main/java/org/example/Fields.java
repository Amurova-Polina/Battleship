package org.example;

import lombok.Getter;

import java.util.*;

import static org.example.Players.printCharsDescription;

@Getter
public class Fields {

    // Игровое поле
    private char[][] playerField;

    //мапа, хранит количество кораблей разного типа: ключ - кол-во палуб, значение - кол-во кораблей
    private final Map<Integer, Integer> shipsAmount = new LinkedHashMap<>();

    public Fields() {
        playerField = new char[10][10];
        for (int k = 0; k < 10; k++) {
            for (int n = 0; n < 10; n++) {
                playerField[k][n] = '0';
            }
        }
    }

    {
        shipsAmount.put(4, 1);
        shipsAmount.put(3, 2);
        shipsAmount.put(2, 3);
        shipsAmount.put(1, 4);
    }

    // Считываем координаты в массив стрингов
    public String[] getCoords() {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        String[] coordinates = input.split(";");
        return coordinates;
    }

    //Преобразуем координаты в массив интов
    public int[][] getCoordsAsIntArray(String[] coordinates) {
        int[][] intCoordinates = new int[coordinates.length][2];

        try {
            for (int i = 0; i < coordinates.length; i++) {
                String[] point = coordinates[i].split(",");
                int x = Integer.parseInt(point[0].trim());
                int y = Integer.parseInt(point[1].trim());
                intCoordinates[i][0] = x;
                intCoordinates[i][1] = y;
            }
        } catch (NumberFormatException e) {
            System.out.println("Вы ошиблись, повторите попытку");
            getCoords();
        }
        return intCoordinates;
    }

    //Возвращает true если корабль горизонтальный
    public boolean isHorizontal(int[][] intCoordinates) {
        int count = 0;
        for (int i = 0; i < intCoordinates.length; i++) {
            if (i + 1 <= intCoordinates.length - 1 && intCoordinates[i][0] == intCoordinates[i + 1][0]) {
                count++;
            }
        }
        return count == intCoordinates.length - 1;
    }

    // Возвращает true если координаты последовательны (без разрывов)
    public boolean isSequential(int[][] intCoordinates) {
        if (intCoordinates.length <= 1) {
            return true; // Однопалубные корабли всегда последовательны
        }
        boolean isHorizontal = isHorizontal(intCoordinates);
        if (isHorizontal) {
            int yStart = intCoordinates[0][1];  // Начало по y
            for (int i = 1; i < intCoordinates.length; i++) {
                if (intCoordinates[i][1] != yStart + i) {
                    return false; // Проблема по y
                }
            }
        } else {
            int xStart = intCoordinates[0][0];  // Начало по x
            for (int i = 1; i < intCoordinates.length; i++) {
                if (intCoordinates[i][0] != xStart + i) {
                    return false; // Проблема по y
                }
            }
        }
        return true;
    }

    // Проверяет, что координаты находятся в пределах игрового поля
    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < 10 && y >= 0 && y < 10;
    }

    // Проставляет корабль и ореол на поле
    public void setShip(int[][] intCoordinates, char[][] playerField) {
        // Проставляем сам корабль значением '1'
        for (int[] coordinate : intCoordinates) {
            int x = coordinate[0];
            int y = coordinate[1];
            playerField[x][y] = '1';
        }
        // Проставляем ореол вокруг корабля значением '2'
        for (int[] coordinate : intCoordinates) {
            int x = coordinate[0];
            int y = coordinate[1];

            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    int newX = x + i;
                    int newY = y + j;

                    if (isInBounds(newX, newY) && playerField[newX][newY] == '0') {
                        playerField[newX][newY] = '2';
                    }
                }
            }
        }
    }

    // Выводим поле в консоль
    public static void displayField(char [][] field) {
        for (char[] row : field) {
            for (char cell : row) {
                String symbol = switch (cell) {
                    case '1' -> "\uD83D\uDEA4";  // Корабль
                    case '2' -> "\uD83D\uDFE6";  // Ореол
                    case '3' -> "\uD83D\uDCA5";  // Попадание
                    case '4' -> "\uD83D\uDD18";  // Промах
                    default -> "\uD83D\uDD32";   // Пустая клетка
                };
                System.out.print(symbol);
            }
            System.out.println();
        }
    }

    // цикл выводит сообщения для ввода координат корабля с разным количеством палуб нужное количество раз
    public char[][] fillPlayerField(char[][] playerField) {

        for (int decks = 4; decks >= 1; decks--) {
            int shipsCount = shipsAmount.get(decks);  // Получаем кол-во кораблей с указанным количеством палуб из мапы
            for (int shipIndex = 0; shipIndex < shipsCount; shipIndex++) {
                boolean isShipPlaced = false;
                while (!isShipPlaced) {
                    System.out.println("Введи координаты корабля с " + decks + " палубами (формат: x1,y1;x2,y2;...):");

                    // Получаем координаты
                    String[] coordinates = getCoords();

                    //Дописать!!!!!!!!!!!!
//                    if(!isInBounds()){
//
//                    }

                    // Проверяем количество палуб
                    if (coordinates.length != decks) {
                        System.out.println("Ошибка: Неверное количество или формат координат. Попробуйте снова.");
                        continue;
                    }

                    // Проверяем, что координаты последовательны
                    int[][] intCoordinates = getCoordsAsIntArray(coordinates);
                    if (!isSequential(intCoordinates)) {
                        System.out.println("Ошибка: Координаты должны идти последовательно либо не соблюден формат. Попробуйте снова.");
                        continue;
                    }

                    // Проверяем, что корабли не пересекаются и не касаются друг друга
                    boolean isValidPlacement = true;
                    for (int[] coord : intCoordinates) {
                        int x = coord[0];
                        int y = coord[1];
                        if (!isInBounds(x, y) || playerField[x][y] != '0') {
                            isValidPlacement = false;
                            break;
                        }
                    }
                    if (!isValidPlacement) {
                        System.out.println("Ошибка: Корабль пересекается или касается другого корабля. Попробуйте снова.");
                        continue;
                    }

                    // Ставим корабль на поле
                    setShip(intCoordinates, playerField);
                    isShipPlaced = true;
                }
            }
        }
        System.out.println("Ваша флотилия:");
        displayField(playerField);
        printCharsDescription();
        clearConsole();
        return playerField;
    }

    public static void clearConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Нажмите Enter, чтобы соперник не увидел ваши корабли");

        String previousInput = "";
        while (true) {
            String input = scanner.nextLine();
            if (input.isEmpty() && previousInput.isEmpty()) {
                printLineBreaks();
                break;
            }
            previousInput = input;
        }

    }

    public static void printLineBreaks() {
        for (int i = 0; i < 25; i++) {
            System.out.println();
        }
    }
}