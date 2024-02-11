package me.relay.tictactoe.game.board;

import me.relay.tictactoe.game.player.PlayerType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * Represents all evaluated game boards.
 * Prevents re-evaluation of the same board state.
 * Singleton class.
 */
public class GameBoards {
    private static final GameBoards instance = new GameBoards();
    private final HashMap<BoardState, BoardState[]> boards;
    private Stack<BoardState> expanded;

    private GameBoards () {
        this.boards = new HashMap<>();
        this.expanded = new Stack<BoardState>();
    }

    public static GameBoards getInstance () {
        return instance;
    }

    /**
     * Get all possible boards from the current board state. Caches results.
     * @param board The current board state.
     * @param player The player making the move.
     * @return All possible boards from the current board state.
     */
    public BoardState[] getBoards (BoardState board, PlayerType player) {
        BoardState[] boards = this.boards.get(board);

        if (boards == null)
            boards = this.possibleBoards(board, player);

        return boards;
    }

    private BoardState[] possibleBoards (BoardState board, PlayerType player) {
        ArrayList<int[]> availableMoves = board.getAvailableMoves();
        BoardState[] boards = new BoardState[availableMoves.size()];

        for (int i = 0; i < boards.length; i++) {
            int[] move = availableMoves.get(i);
            boards[i] = board.play(move[0], move[1], player.toSymbol());
        }

        this.boards.put(board, boards);

        return boards;
    }
}
