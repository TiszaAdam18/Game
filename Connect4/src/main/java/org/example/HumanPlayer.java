package org.example;


public class HumanPlayer extends Player {
    public < Disc > HumanPlayer(String name, Disc disc) {
        super(name, (org.example.Disc) disc);
    }

    @Override
    public int makeMove(Board board) {
        // Ez a metódus nem lesz használva, mivel a humán játékos lépését
        // közvetlenül a Game osztályban kezeljük
        throw new UnsupportedOperationException("Human player moves are handled in the Game class");
    }
}