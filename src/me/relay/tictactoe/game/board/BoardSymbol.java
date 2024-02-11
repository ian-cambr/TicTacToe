package me.relay.tictactoe.game.board;

public enum BoardSymbol {
    NONE, X, O;

    /**
      * @return The formatted string representation of the symbol.
     */
    @Override
    public String toString () {
        return switch (this) {
            case X -> " X ";
            case O -> " O ";
            case NONE -> "   ";
        };
    }

    /**
      * Returns the integer representation of the symbol.
      * Used for evaluating the board state.
      * @return 1 for X, -1 for O, 0 for NONE
     */
    public int asInteger () {
        return switch (this) {
            case X -> 1;
            case O -> -1;
            case NONE -> 0;
        };
    }
}

