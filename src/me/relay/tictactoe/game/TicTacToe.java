package me.relay.tictactoe.game;

import me.relay.tictactoe.game.board.BoardState;
import me.relay.tictactoe.game.player.*;

import java.util.BitSet;
import java.util.Random;
import java.util.Scanner;

public class TicTacToe {
    private static final String DECORATIVE_BORDER = "=================================================";
    public static boolean exitRequest = false;

    /**
     * Starts the game. Called by the entrypoint in Main.java.
     */
    public void start() {
        BoardState board = new BoardState();

        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to Tic-Tac-Toe!");

        String input;
        do {
            System.out.println("Input '1' (human) to play against the AI, or '2' (computer) to have the AIs face off: ");
            input = scanner.next().toLowerCase();
            System.out.println(input);
        } while (!input.matches("[12]"));

        Player[] players = generatePlayers(input.equals("2"), scanner);

        System.out.print(DECORATIVE_BORDER);
        System.out.println();
        board.print();

        while (true) {
            for (int i = 0; i < players.length; i++) {
                Player player = players[i];

                System.out.print("Player " + (i+1) + "'s turn...");
                board = player.play(board);
                System.out.println();

                System.out.print(DECORATIVE_BORDER);
                System.out.println();

                // If the player requested to quit the game, end it here.
                if (exitRequest)
                    System.exit(0);

                board.print();

                boolean draw = board.getAvailableMoves().isEmpty();
                boolean win = board.hasWinner();
                if (draw || win) {
                    if (win) {
                        System.out.println("Player " + (i+1) + " wins!");
                    }
                    else {
                        System.out.println("It's a draw!");
                    }

                    System.out.print(DECORATIVE_BORDER);
                    System.exit(0);
                }
            }
        }
    }

    private Player[] generatePlayers(boolean bothAI, Scanner scanner) {
        Player[] players = new Player[]
                    {
                            new AIPlayer(PlayerType.X, AIStrategy.RANDOM),
                            new AIPlayer(PlayerType.O, AIStrategy.RANDOM),
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
