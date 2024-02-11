package me.relay.tictactoe.game.board;

public class BoardStateException extends RuntimeException {
    /**
     * Thrown when the board would be put into an invalid state by the requested operation.
     * @param s The message to display.
     */
    public BoardStateException (String s) {
        super(s);
    }
}
