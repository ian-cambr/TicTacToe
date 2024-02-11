package me.relay.tictactoe.game.player;

import me.relay.tictactoe.game.board.BoardState;

public abstract class Player {
    /**
      * Called by the subclass constructor to set the player type and initialize an abstract player.
      * @param type The type of the Player. See {@link me.relay.tictactoe.game.player.PlayerType}
     */
    protected Player (PlayerType type) {
        this.playerType = type;
    }

    /**
     * Play the game.
     * @return The board state after the player has made their move.
     */
    public abstract BoardState play(BoardState current);

    /**
     * The player's type (X or O).
     */
    protected PlayerType playerType;
}
