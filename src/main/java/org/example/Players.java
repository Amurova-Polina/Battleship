package org.example;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;


@Getter
@Setter
public class Players {

    // Выводим приветственное сообщение
    public static void printHelloMessage() {
        System.out.println("Введите свое имя");
        Scanner scanner = new Scanner(System.in);
        String playerName = scanner.nextLine();
        System.out.println(playerName + ",время расставить свою флотилию по боевым точкам!\n" +
                "Правила заполнения игрового поля:\n" +
                "1. Помните, что корабли должны располагаться строго вертикально или строго горизонтально, без разрывов\n" +
                "2. Игровое поле ограничено координатами от 0 до 9\n" +
                "3. Корабли не должны касаться друг друга сторонами или углами.");
    }

    // Выводим расшифровку условных обозначений
    public static void printCharsDescription() {
        System.out.println("""
                Расшифровка условных обозначений на игровом поле:
                \uD83D\uDEA4 - Корабль
                \uD83D\uDFE6 - Ореол корабля
                \uD83D\uDCA5 - Попадание
                \uD83D\uDD18 - Промах
                \uD83D\uDD32 - Пустая клетка""");
    }

}
