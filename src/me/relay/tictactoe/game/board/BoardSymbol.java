package me.relay.tictactoe.game.board;

public enum BoardSymbol {
    NONE, X, O;

    @Override
    public String toString () {
        return switch (this) {
            case X -> " X ";
            case O -> " O ";
            case NONE -> "   ";
            default -> throw new IndexOutOfBoundsException("Symbol not found.");
        };
    }
}

