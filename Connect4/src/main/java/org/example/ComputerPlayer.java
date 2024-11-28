package org.example;

import java.util.Random;

public class ComputerPlayer extends Player {
    private Random random;

    public ComputerPlayer(String name, Disc disc) {
        super(name, disc);
        this.random = new Random();
    }

    @Override
    public int makeMove(Board board) {
        return generateMove(board);
    }

    public int generateMove(Board board) {
        int column;
        do {
            column = random.nextInt(7); // 7 oszlop van (a-g)
        } while (!board.isValidMove(column));
        return column;
    }
}