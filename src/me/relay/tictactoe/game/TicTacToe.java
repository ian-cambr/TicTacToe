package me.relay.tictactoe.game;

import me.relay.tictactoe.game.board.BoardState;
import me.relay.tictactoe.game.player.*;

import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private static final String DECORATIVE_BORDER = "=================================================";
    public static boolean exitRequest = false;

    /**
     * Starts the game. Called by the entrypoint in Main.java.
     */
    public void start() {
        BoardState board = BoardState.getEmptyBoard();

        Scanner scanner = new Scanner(System.in);
        System.out.println(DECORATIVE_BORDER);

        String input;
        do {
            System.out.println();

            // IntelliJ suggested this over string concatenation.
            System.out.print("""
                    Select the AI's opponent:
                    [1] Human
                    [2] AI
                    ==>\t""");

            input = scanner.next();
        } while (!input.matches("[12]"));

        Player[] players = generatePlayers(input.equals("2"), scanner);

        System.out.println(DECORATIVE_BORDER);
        board.print();

        while (true) {
            for (int i = 0; i < players.length; i++) {
                Player player = players[i];

                System.out.print("Player " + (i+1) + "'s turn...");
                System.out.println();

                board = player.play(board);
                System.out.println();

                System.out.println(DECORATIVE_BORDER);

                // If the player requested to quit the game, end it here.
                if (exitRequest)
                    System.exit(0);

                board.print();

                boolean draw = board.getAvailableMoves().isEmpty();
                boolean win = board.getWinner().isPresent();
                if (draw || win) {
                    if (win) {
                        System.out.println("Player " + (i+1) + " wins!");
                    }
                    else {
                        System.out.println("It's a draw!");
                    }

                    System.out.println(DECORATIVE_BORDER);
                    System.exit(0);
                }
            }
        }
    }

    private Player[] generatePlayers(boolean bothAI, Scanner scanner) {
        Player[] players = new Player[]
                    {
                            new AIPlayer(PlayerType.X, AIStrategy.MINIMAX),
                            new AIPlayer(PlayerType.O, AIStrategy.MINIMAX),
                    };

        if (bothAI) return players;

        // Otherwise we must replace player 1 or player 2 with an AI player
        if (new Random().nextBoolean()) {
            players[0] = new HumanPlayer(PlayerType.X, scanner);
        }
        else {
            players[1] = new HumanPlayer(PlayerType.O, scanner);
        }

        return players;
    }
}
