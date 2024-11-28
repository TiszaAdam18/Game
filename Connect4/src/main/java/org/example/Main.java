package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Main {
    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) {
        logger.info("Connect 4 játék indítása");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Üdvözöljük a Connect 4 játékban!");
        System.out.print("Kérem, adja meg a nevét: ");
        String playerName = scanner.nextLine();

        Game game = new Game(playerName);
        game.start();

        scanner.close();
    }
}