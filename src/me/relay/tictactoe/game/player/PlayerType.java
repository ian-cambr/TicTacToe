package me.relay.tictactoe.game.player;

import me.relay.tictactoe.game.board.BoardStateException;
import me.relay.tictactoe.game.board.BoardSymbol;

public enum PlayerType {
    X, O;

    public BoardSymbol toSymbol() {
        return switch (this) {
            case X -> BoardSymbol.X;
            case O -> BoardSymbol.O;
            default -> throw new BoardStateException("PlayerType not X or O");
        };
    }
}
