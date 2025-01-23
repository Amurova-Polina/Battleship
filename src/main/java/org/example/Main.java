package org.example;

import static org.example.Players.printHelloMessage;
import static org.example.PlayingProcess.*;

public class Main {
    public static void main(String[] args) {
        Fields player1Field = new Fields();
        Fields player2Field = new Fields();
        Fields player1BattleField = new Fields();
        Fields player2BattleField = new Fields();

        printHelloMessage();
        player1Field.fillPlayerField(player1Field.getPlayerField());

        printHelloMessage();
        player1Field.fillPlayerField(player2Field.getPlayerField());

        System.out.println("Игра началась!");

        boolean isPlayer1Turn = isPlayer1Turn();
        while (true) {
            System.out.printf("\n\nХод делает игрок %s%n", isPlayer1Turn ? "1" : "2");

            // Подаем в strike нужный набор игровых полей в зависимости от того, чья очередь стрелять
            if (isPlayer1Turn)
                strike(player2Field.getPlayerField(), player1BattleField.getPlayerField());
            else
                strike(player1Field.getPlayerField(), player2BattleField.getPlayerField());

            // Проверяем, остались ли корабли хотя бы у одного из игроков
            boolean hasShipsPlayer1 = hasShips(player1Field.getPlayerField());
            boolean hasShipsPlayer2 = hasShips(player2Field.getPlayerField());

            // Если хотя бы у одного из игроков не осталось кораблей, завершаем игру
            if (!hasShipsPlayer1 || !hasShipsPlayer2) {
                System.out.println("Игра завершена.");
                System.out.println(hasShipsPlayer1 ? "Игрок 1 победил!" : "Игрок 2 победил!");
                break;
            } else {
                // Меняем очередь и продолжаем игру
                isPlayer1Turn = !isPlayer1Turn;
            }
        }
    }
}