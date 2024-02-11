package me.relay.tictactoe.game.player;

import me.relay.tictactoe.game.board.BoardState;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class HumanPlayer extends Player{
    private final Scanner scanner;
    public HumanPlayer(PlayerType playerType, Scanner scanner) {
        super(playerType);

        this.scanner = scanner;
    }
    @Override
    public BoardState play(BoardState current) {
        ArrayList<int[]> availableMoves = current.getAvailableMoves();

        while (true) {
            int row, column;

            try {
                System.out.print("Enter row [0-2]: ");
                row = scanner.nextInt();

                System.out.print("Enter column [0-2]: ");
                column = scanner.nextInt();
            }
            catch (NoSuchElementException e) {
                System.out.println("Invalid input, try again.");
                scanner.nextLine();

                continue;
            }

            int[] move = {row, column};

            if (availableMoves.stream().anyMatch(m -> m[0] == move[0] && m[1] == move[1]))
                return current.play(row, column, this.playerType.toSymbol());

            if (row > 2 || column > 2 || row < 0 || column < 0)
                System.out.println("That space is outside of the bounds of the board, try again.");
            else
                System.out.println("That space is already occupied, try again.");
        }
    }
}
