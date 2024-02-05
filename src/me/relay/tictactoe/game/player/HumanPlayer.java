package me.relay.tictactoe.game.player;

import me.relay.tictactoe.game.board.BoardState;

import java.util.Scanner;

public class HumanPlayer extends Player{
    private Scanner scanner;
    public HumanPlayer(PlayerType playerType, Scanner scanner) {
        super(playerType);

        this.scanner = scanner;
    }
    @Override
    public BoardState play(BoardState current) {
        return null;
    }
}
