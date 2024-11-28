package org.example;

public abstract class Player {
    protected String name;
    protected Disc disc;

    public Player(String name, Disc disc) {
        this.name = name;
        this.disc = disc;
    }



    public String getName() {
        return name;
    }

    public Disc getDisc() {
        return disc;
    }

    public abstract int makeMove(Board board);
}