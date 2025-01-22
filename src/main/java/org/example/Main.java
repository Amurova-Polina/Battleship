package org.example;

import static org.example.Fields.clearConsole;
import static org.example.Players.printCharsDescription;
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
        printCharsDescription();
        clearConsole();

        printHelloMessage();
        player1Field.fillPlayerField(player2Field.getPlayerField());
        printCharsDescription();
        clearConsole();


        if(setTurn()==1){
            strike(player2Field.getPlayerField(),player1BattleField.getPlayerField(), setTurn());
        } else {
            strike(player1Field.getPlayerField(),player2BattleField.getPlayerField(), setTurn());
        }


        // Это все для проверки того, как методы работают пока что
//        printHelloMessage();
//        Fields playerField1 = new Fields();
//        playerField1.fillPlayerField(playerField1.getPlayerField());
//        printCharsDescription();
//
//
//        PlayingProcess pp = new PlayingProcess();
//        pp.strike(playerField1.getPlayerField());

//        int b = setTurn();
//        System.out.println(b);
//        switchTurn();




    }
    // Выводим поле в консоль
//    public void displayField(char [][] field) {
//        System.out.println("Ваша флотилия:");
//        for (char[] row : field) {
//            for (char cell : row) {
//                String symbol = switch (cell) {
//                    case '1' -> "\uD83D\uDEA4";  // Корабль
//                    case '2' -> "\uD83D\uDFE6";  // Ореол
//                    case '3' -> "\uD83D\uDCA5";  // Попадание
//                    case '4' -> "\uD83D\uDD18";  // Промах
//                    default -> "\uD83D\uDD32";   // Пустая клетка
//                };
//                System.out.print(symbol);
//            }
//            System.out.println();
//        }
//    }
}