package me.relay.tictactoe.game.player;

import me.relay.tictactoe.game.board.BoardSymbol;

public enum PlayerType {
    X, O;

    /**
     * @return The board representation of the player type.
     */
    public BoardSymbol toSymbol() {
        return switch (this) {
            case X -> BoardSymbol.X;
            case O -> BoardSymbol.O;
        };
    }

    /**
     * @return The opposite player type.
     */
    public PlayerType opposite() {
        return switch (this) {
            case X -> O;
            case O -> X;
        };
    }
}
