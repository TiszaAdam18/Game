package org.example;

import java.util.Arrays;

public class Board {
    private final int rows;
    private final int columns;
    private final Disc[][] grid;

    public Board(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.grid = new Disc[rows][columns];
        for (Disc[] row : grid) {
            Arrays.fill(row, Disc.EMPTY);
        }
    }

    public void display() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                System.out.print("| ");
                switch (grid[i][j]) {
                    case YELLOW:
                        System.out.print("X ");
                        break;
                    case RED:
                        System.out.print("Y ");
                        break;
                    default:
                        System.out.print("  ");
                }
            }
            System.out.println("|");
        }
        System.out.println("  0   1   2   3   4   5   6");
    }

    public boolean isValidMove(int column) {
        return column >= 0 && column < columns && grid[0][column] == Disc.EMPTY;
    }

    public void makeMove(int column, Disc disc) {
        for (int row = rows - 1; row >= 0; row--) {
            if (grid[row][column] == Disc.EMPTY) {
                grid[row][column] = disc;
                break;
            }
        }
    }

    public boolean checkWin(Disc disc) {
        // Vízszintes ellenőrzés
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col <= columns - 4; col++) {
                if (grid[row][col] == disc &&
                    grid[row][col+1] == disc &&
                    grid[row][col+2] == disc &&
                    grid[row][col+3] == disc) {
                    return true;
                }
            }
        }

        // Függőleges ellenőrzés
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 0; col < columns; col++) {
                if (grid[row][col] == disc &&
                    grid[row+1][col] == disc &&
                    grid[row+2][col] == disc &&
                    grid[row+3][col] == disc) {
                    return true;
                }
            }
        }

        // Átlós ellenőrzés (balról jobbra)
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 0; col <= columns - 4; col++) {
                if (grid[row][col] == disc &&
                    grid[row+1][col+1] == disc &&
                    grid[row+2][col+2] == disc &&
                    grid[row+3][col+3] == disc) {
                    return true;
                }
            }
        }

        // Átlós ellenőrzés (jobbról balra)
        for (int row = 0; row <= rows - 4; row++) {
            for (int col = 3; col < columns; col++) {
                if (grid[row][col] == disc &&
                    grid[row+1][col-1] == disc &&
                    grid[row+2][col-2] == disc &&
                    grid[row+3][col-3] == disc) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean isFull() {
        for (int col = 0; col < columns; col++) {
            if (grid[0][col] == Disc.EMPTY) {
                return false;
            }
        }
        return true;
    }

    public int getRows( ) {
        return 0;
    }

    public int getColumns( ) {
        return 0;
    }

    public Object getDiscAt(int i, int j) {
        return null;
    }

    public void setDiscAt(int row, int column, Disc disc) {
    }
}