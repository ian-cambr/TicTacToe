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
     * @param x
     * @param y
     * @param symbol
     * @return A new instance of BoardState after that move is played. Note that this does not affect this instance of BoardState.
     */
    public BoardState play(int x, int y, BoardSymbol symbol) {
        if (x > 2 || y > 2 || x < 0 || y < 0) {
            throw new IndexOutOfBoundsException("You tried to play a move outside of the bounds of the board: x:" + x + ", y: " + y);
        }
        if (this.board[x][y] != BoardSymbol.NONE) {
            throw new BoardStateException("You cannot play a move on top of an existing symbol!");
        }

        BoardState modified = new BoardState(this);
        modified.board[x][y] = symbol;

        return modified;
    }

    public int[] getRandomAvailableMove() {
        ArrayList<int[]> available = new ArrayList<>();

        // Filter by BoardSymbol.NONE.
        // I would use a stream normally, but it felt weird here.
        for (int i = 0; i < board.length; i++)
            for (int j = 0; j < board.length; j++)
                if (board[i][j] == BoardSymbol.NONE)
                    available.add(new int[]{i, j});

        return available.get(new Random().nextInt(available.size()));
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

        // Print y0, y1, y2
        for (int j = 0; j < board.length; j++) {
            StringJoiner row = new StringJoiner("|");

            for (int i = 0; i < board.length; i++)
                row.add(board[i][j].toString());

            table.add(row.toString());
        }

        System.out.println(table);
        System.out.println();
    }
}