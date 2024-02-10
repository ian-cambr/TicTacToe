package me.relay.tictactoe.game.board;

import me.relay.tictactoe.game.player.PlayerType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.StringJoiner;

public class BoardState {
    private final BoardSymbol[][] board = new BoardSymbol[3][3];
    public BoardState() {
        for (BoardSymbol[] row : board) {
            Arrays.fill(row, BoardSymbol.NONE);
        }
    }
    public BoardState(BoardState other) {
        for(int i = 0; i < other.board.length; i++)
            this.board[i] = other.board[i].clone();
    }

    /**
     * Play the requested move.
     *
     * @param row Row
     * @param column Column
     * @param symbol The symbol to play.
     * @return A new instance of BoardState after that move is played. Note that this does not affect this instance of BoardState.
     */
    public BoardState play(int row, int column, BoardSymbol symbol) {
        if (row > 2 || column > 2 || row < 0 || column < 0) {
            throw new IndexOutOfBoundsException("You tried to play a move outside of the bounds of the board: row:" + row + ", y: " + column);
        }
        if (this.board[row][column] != BoardSymbol.NONE) {
            throw new BoardStateException("You cannot play a move on top of an existing symbol!");
        }

        BoardState modified = new BoardState(this);
        modified.board[row][column] = symbol;

        return modified;
    }

    public int[] getRandomAvailableMove() {
        ArrayList<int[]> available = this.getAvailableMoves();

        return available.get(new Random().nextInt(available.size()));
    }

    public ArrayList<int[]> getAvailableMoves() {
        ArrayList<int[]> available = new ArrayList<>();

        // Filter by BoardSymbol.NONE.
        // I would use a stream normally, but it felt weird here.
        for (int row = 0; row < board.length; row++)
            for (int column = 0; column < board.length; column++)
                if (board[row][column] == BoardSymbol.NONE)
                    available.add(new int[]{row, column});

        return available;
    }

    private int[] getMaxMove (PlayerType player) {
        return null;
    }

    private int[] getMinMove (PlayerType player) {
        return null;
    }

    public void print() {
        System.out.println();
        StringJoiner table = new StringJoiner("\n---+---+---\n");

        for (int row = 0; row < board.length; row++) {
            StringJoiner rowBuilder = new StringJoiner("|");

            for (int col = 0; col < board.length; col++)
                rowBuilder.add(board[row][col].toString());

            table.add(rowBuilder.toString());
        }

        System.out.println(table);
        System.out.println();
    }

    public boolean hasWinner() {
        // Pull winning patterns from the board to check.
        // Rows 1-3, Columns 1-3, Diagonals 1/2
        BoardSymbol[][] lines = {
                {board[0][0], board[1][0], board[2][0]},
                {board[0][1], board[1][1], board[2][1]},
                {board[0][2], board[1][2], board[2][2]},

                {board[0][0], board[0][1], board[0][2]},
                {board[1][0], board[1][1], board[1][2]},
                {board[2][0], board[2][1], board[2][2]},

                {board[0][0], board[1][1], board[2][2]},
                {board[2][0], board[1][1], board[0][2]}
        };

        // Check patterns
        for (BoardSymbol[] line : lines) {
            if (line[0] != BoardSymbol.NONE && line[0] == line[1] && line[0] == line[2]) {
                return true;
            }
        }

        return false;
    }

    /**
     * Evaluate the current board state.
     * Heuristic: +3 for each row, column, or diagonal with 2 X and 1 empty space.
     * +1 for each row, column, or diagonal with 1 X and 2 empty spaces.
     * And the same for O.
     */
    public int evaluate() {
        return 0;
    }
}