package me.relay.tictactoe.game.board;

public class BoardStateException extends RuntimeException {
    public BoardStateException (String s) {
        super(s);
    }
    public BoardStateException () {
        super();
    }
}
