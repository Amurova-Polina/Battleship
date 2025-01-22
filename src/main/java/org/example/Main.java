package org.example;

import static org.example.Players.printCharsDescription;
import static org.example.Players.printHelloMessage;

public class Main {

    public static void main(String[] args) {

        // Это все для проверки того, как методы работают пока что
        printHelloMessage();
        Fields playerField1 = new Fields();
        playerField1.fillPlayerField(playerField1.getPlayerField());
        printCharsDescription();


        PlayingProcess pp = new PlayingProcess();
        pp.strike(playerField1.getPlayerField());




    }
}