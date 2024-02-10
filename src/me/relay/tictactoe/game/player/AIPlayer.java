package me.relay.tictactoe.game.player;
import me.relay.tictactoe.game.board.BoardState;

public class AIPlayer extends Player {
    private final AIStrategy strategy;
    public AIPlayer (PlayerType type, AIStrategy strategy) {
        super(type);

        this.strategy = strategy;
    }
    @Override
    public BoardState play(BoardState current) {
        int[] strategy = this.evaluate(current);

        return current.play(strategy[0], strategy[1], this.playerType.toSymbol());
    }

    private int[] evaluate(BoardState current) {
        return switch (strategy) {
            case RANDOM -> randomMove(current);
            case MINIMAX -> minimaxMove(current);

            default -> throw new IndexOutOfBoundsException("Strategy unavailable.");
        };
    }

    private int[] randomMove(BoardState current) {
        return current.getRandomAvailableMove();
    }

    private int[] minimaxMove(BoardState current) {
        return new int[] {-1, -1};
    }
}