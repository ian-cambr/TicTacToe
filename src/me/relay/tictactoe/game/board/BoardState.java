package me.relay.tictactoe.game.board;

import java.util.*;

public class BoardState {
    private final BoardSymbol[][] board = new BoardSymbol[3][3];
    private final BoardState parent;

    public static BoardState getEmptyBoard() {
        return new BoardState();
    }
    private BoardState () {
        this.parent = null;
        for (BoardSymbol[] row : board) {
            Arrays.fill(row, BoardSymbol.NONE);
        }
    }

    /**
      * Copy constructor.
      * @param other The BoardState to copy.
      * Since internal lines will likely be recalculated anyway, we don't need to copy them.
     */
    private BoardState(BoardState other) {
        this.parent = other;
        for(int i = 0; i < other.board.length; i++)
            this.board[i] = other.board[i].clone();
    }

    public BoardState getParent() {
        return this.parent;
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
        modified.getLines(); // Recalculate the lines.

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

    public void print() {
        System.out.println();
        StringJoiner table = new StringJoiner("\n---+---+---\n");

        // This is clearer than a for-each loop.
        //noinspection ForLoopReplaceableByForEach
        for (int row = 0; row < board.length; row++) {
            StringJoiner rowBuilder = new StringJoiner("|");

            for (int col = 0; col < board.length; col++)
                rowBuilder.add(board[row][col].toString());

            table.add(rowBuilder.toString());
        }

        System.out.println(table);
        System.out.println();
    }

    /**
     * Except for in rare cases, this shouldn't be accessed directly.
     * Instead, use getLines().
     */
    private BoardSymbol[][] internalLines = null;
    private BoardSymbol[][] getLines() {
        if (internalLines == null) {
            internalLines = new BoardSymbol[][]{
                    {board[0][0], board[1][0], board[2][0]},
                    {board[0][1], board[1][1], board[2][1]},
                    {board[0][2], board[1][2], board[2][2]},

                    {board[0][0], board[0][1], board[0][2]},
                    {board[1][0], board[1][1], board[1][2]},
                    {board[2][0], board[2][1], board[2][2]},

                    {board[0][0], board[1][1], board[2][2]},
                    {board[2][0], board[1][1], board[0][2]}
            };
        }

        return internalLines;
    }

    public Optional<BoardSymbol> getWinner() {
        // Pull winning patterns from the board to check.
        // Rows 1-3, Columns 1-3, Diagonals 1/2

        BoardSymbol[][] lines = this.getLines();

        // Check patterns
        for (BoardSymbol[] line : lines) {
            if (line[0] != BoardSymbol.NONE && line[0] == line[1] && line[0] == line[2]) {
                return Optional.of(line[0]);
            }
        }

        return Optional.empty();
    }

    /**
     * Evaluate the current board state.
     * Heuristic: +3 for each row, column, or diagonal with 2 X and 1 empty space.
     * +1 for each row, column, or diagonal with 1 X and 2 empty spaces.
     * And the same for O.
     * 128 for X win, -128 for O win.
     */
    public int heuristicEvaluate() {
        // Check if the game is over.
        Optional<BoardSymbol> winner = this.getWinner();
        if (winner.isPresent()) {
            if (winner.get() == BoardSymbol.X) {
                return 128;
            }
            else {
                return -128;
            }
        }

        // Check rows, columns, and diagonals
        BoardSymbol[][] lines = this.getLines();

        // If the line has 2 X and 1 empty space, add 3 to the score.
        // If the line has 1 X and 2 empty spaces, add 1 to the score.
        // Same for O.

        int score = 0;
        for (BoardSymbol[] line : lines) {
            // Skip if the line has no X or O.
            if (Arrays.stream(line).allMatch(symbol -> symbol == BoardSymbol.NONE)) {
                continue;
            }
            // Skip if the line has both X and O.
            if (Arrays.stream(line).anyMatch(symbol -> symbol == BoardSymbol.X) && Arrays.stream(line).anyMatch(symbol -> symbol == BoardSymbol.O)) {
                continue;
            }

            // Filter out the empty spaces.
            List<BoardSymbol> symbols = Arrays.stream(line).filter(symbol -> symbol != BoardSymbol.NONE).toList();

            // If this fails, we are seriously in trouble. Already should have been skipped.
            // Anywho, we're going to get the first symbol and multiply it by the score of the line.
            BoardSymbol first = symbols.get(0);

            int baseScore = symbols.size() == 2
                    ? 3
                    : 1;

            score += first.asInteger() * baseScore;
        }

        return score;
    }

    /**
      * Deduce the move that was made to get from the current board to the board parameter.
      * Can travel up the board tree by using the parent of the board parameter.
      * @param position The board to compare to.
      * @return The move that was made to get from the current board to the board parameter.
      * @throws BoardStateException If the board parameter is not a valid move from the current board.
     */
    public int[] deduce(BoardState position) {
        while (position.getParent() != this) {
            if (position.getParent()== this) {
                throw new BoardStateException("The board parameter is not a valid move from the current board.");
            }

            position = position.getParent();
        }

        // Search for the difference between the two boards.
        for (int row = 0; row < this.board.length; row++) {
            for (int column = 0; column < this.board.length; column++) {
                if (this.board[row][column] != position.board[row][column]) {
                    return new int[]{row, column};
                }
            }
        }

        return null;
    }
}