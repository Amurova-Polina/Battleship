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
        int turn = setTurn();

        boolean b = true;
        while (b) {
            System.out.println("Ход делает игрок " + turn);
            // Подаем в strike нужный набор игровых полей в зависимости от того, чья очередь стрелять
            if (turn == 1) {
                strike(player2Field.getPlayerField(), player1BattleField.getPlayerField(), turn);
            } else {
                strike(player1Field.getPlayerField(), player2BattleField.getPlayerField(), turn);
            }

            // Проверяем, остались ли корабли хотя бы у одного из игроков
            b = isGameContinue(player1Field.getPlayerField()) && isGameContinue(player2Field.getPlayerField());

            // Если хотя бы у одного из игроков не осталось кораблей, завершаем игру
            if (!b) {
                System.out.println("Игра завершена.");
                break;
            } else {
                // Меняем очередь и продолжаем игру
                int t = switchTurn(turn);
                turn = t;
            }
        }

    }

}