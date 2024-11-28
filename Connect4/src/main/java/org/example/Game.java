package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;

public class Game {
    private static final Logger logger = LoggerFactory.getLogger(Game.class);

    private Board board;
    private Player humanPlayer;
    private Player computerPlayer;
    private Player currentPlayer;
    private DatabaseManager dbManager;

    public Game(String playerName) {
        this.board = new Board(6, 7);  // Alapértelmezett 6x7-es tábla
        this.humanPlayer = new HumanPlayer(playerName, Disc.YELLOW);
        this.computerPlayer = new ComputerPlayer("Számítógép", Disc.RED);
        this.currentPlayer = humanPlayer;  // A sárga (humán) játékos kezd
        this.dbManager = new DatabaseManager();
    }

    public void start() {
        logger.info("Új játék kezdődik");
        Scanner scanner = new Scanner(System.in);

        while (!isGameOver()) {
            board.display();

            if (currentPlayer instanceof HumanPlayer) {
                makeHumanMove(scanner);
            } else {
                makeComputerMove();
            }

            if (board.checkWin(currentPlayer.getDisc())) {
                board.display();
                System.out.println(currentPlayer.getName() + " nyert!");
                dbManager.saveWin(currentPlayer.getName());
                break;
            }

            if (board.isFull()) {
                board.display();
                System.out.println("A játék döntetlen!");
                break;
            }

            switchPlayer();
        }

        displayHighScores();
        scanner.close();
    }

    private void makeHumanMove(Scanner scanner) {
        boolean validMove = false;
        while (!validMove) {
            System.out.print("Válasszon oszlopot (0-6): ");
            String input = scanner.nextLine().toLowerCase();
            if (input.length() == 1 && input.charAt(0) >= '0' && input.charAt(0) <= '6') {
                int column = input.charAt(0) - '0';
                if (board.isValidMove(column)) {
                    board.makeMove(column, currentPlayer.getDisc());
                    validMove = true;
                } else {
                    System.out.println("Ez az oszlop már tele van. Próbáljon másikat.");
                }
            } else {
                System.out.println("Érvénytelen bemenet. Kérem, adjon meg egy számot 0-tól 6-ig");
            }
        }
    }

    private void makeComputerMove() {
        int column = ((ComputerPlayer) computerPlayer).generateMove(board);
        board.makeMove(column, computerPlayer.getDisc());
        System.out.println("A számítógép a(z) " + (char)('0' + column) + " oszlopba tette a korongját.");
    }

    private void switchPlayer() {
        currentPlayer = (currentPlayer == humanPlayer) ? computerPlayer : humanPlayer;
    }

    private boolean isGameOver() {
        return board.checkWin(Disc.YELLOW) || board.checkWin(Disc.RED) || board.isFull();
    }

    private void displayHighScores() {
        System.out.println("\nLegmagasabb pontszámok:");
        dbManager.getHighScores().forEach((name, wins) ->
                System.out.println(name + ": " + wins + " győzelem")
        );
    }
}
