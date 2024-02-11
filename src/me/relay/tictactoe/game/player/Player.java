package me.relay.tictactoe.game.player;

import me.relay.tictactoe.game.board.BoardState;

public abstract class Player {
    /**
     * Play the game.
     * @return The board state after the player has made their move.
     */
    public abstract BoardState play(BoardState current);

    protected PlayerType playerType;
    public Player (PlayerType type) {
        this.playerType = type;
    }
}
