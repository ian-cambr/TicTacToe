package me.relay.tictactoe.game.player;
import me.relay.tictactoe.game.board.BoardState;
import me.relay.tictactoe.game.board.GameBoards;

public class AIPlayer extends Player {
    private final AIStrategy strategy;
    public AIPlayer (PlayerType type, AIStrategy strategy) {
        super(type);

        this.strategy = strategy;
    }
    @Override
    public BoardState play(BoardState current) {
        int[] strategy = this.evaluate(current);
        System.out.println(strategy[0] + "," + strategy[1]);

        return current.play(strategy[0], strategy[1], this.playerType.toSymbol());
    }

    private int[] evaluate(BoardState current) {
        return switch (strategy) {
            case RANDOM -> randomMove(current);
            case MINIMAX -> minimaxMove(current);
        };
    }

    private int[] randomMove(BoardState current) {
        return current.getRandomAvailableMove();
    }

    private int[] minimaxMove(BoardState current) {
        this.nodesExpanded = 0;

        // Going to depth 3
        BoardTuple best = minimax(current, 0, this.playerType == PlayerType.X, this.playerType);
        System.out.println("Nodes expanded = " + nodesExpanded);

        return current.deduce(best.board());
    }

    private static final int MAX_DEPTH = 3;
    private int nodesExpanded = 0;

    /**
     * Minimax algorithm. Recursively evaluates the board state!
     * @param node The current board state.
     * @param currentDepth The current depth of the tree in relation to "node" (starts at 0).
     * @param isMaximizing Whether the current player is maximizing or minimizing.
     * @param player The player to evaluate for.
     * @return The best future board state and its score. This will need to be deduced back to a move.
     */
    private BoardTuple minimax(BoardState node, int currentDepth, boolean isMaximizing, PlayerType player) {

        if (currentDepth == MAX_DEPTH || node.getWinner().isPresent() || node.getAvailableMoves().isEmpty()) {
            this.nodesExpanded++;
            return new BoardTuple(node, node.heuristicEvaluate());
        }

        BoardState[] boards = GameBoards.getInstance().getBoards(node, player);

        BoardTuple bestValue = new BoardTuple(null, isMaximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE);

        for (BoardState board : boards) {
            BoardTuple value = minimax(board, currentDepth + 1, !isMaximizing, player.opposite());

            if (isMaximizing) {
                bestValue = value.score() > bestValue.score()
                        ? value
                        : bestValue;
            } else {
                bestValue = value.score() < bestValue.score()
                        ? value
                        : bestValue;
            }
        }

        return bestValue;
    }
}

record BoardTuple(BoardState board, int score) {
}