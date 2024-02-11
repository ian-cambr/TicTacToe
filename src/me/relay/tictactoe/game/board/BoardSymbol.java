package me.relay.tictactoe.game.board;

public enum BoardSymbol {
    NONE, X, O;

    @Override
    public String toString () {
        return switch (this) {
            case X -> " X ";
            case O -> " O ";
            case NONE -> "   ";
        };
    }

    public int asInteger () {
        return switch (this) {
            case X -> 1;
            case O -> -1;
            case NONE -> 0;
        };
    }
}

