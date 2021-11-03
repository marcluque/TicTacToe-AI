package de.marcluque.tictactoeai;

import de.marcluque.tictactoeai.map.Map;
import de.marcluque.tictactoeai.map.Utility;
import de.marcluque.tictactoeai.search.AlphaBetaSearch;
import de.marcluque.tictactoeai.search.MiniMaxSearch;
import de.marcluque.tictactoeai.utils.LoggerUtil;
import de.marcluque.tictactoeai.utils.MapUtil;

import java.util.Scanner;
import java.util.logging.Logger;

/*
 * Created with <3 by marcluque, March 2020
 */
public class TicTacToe {

    private static final Logger LOGGER = LoggerUtil.createLogger("de.marcluque.tictactoeai",
            "[%1$tF %1$tT] %2$s %n", TicTacToe.class);

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        LOGGER.info("Please pick whether opponent shall use Alpha-Beta pruning (1) or plain MiniMax (0)");
        int choice = SCANNER.nextInt();
        while (choice != 0 && choice != 1) {
            LOGGER.warning("Please pick 0 or 1");
            choice = SCANNER.nextInt();
        }

        LOGGER.info("You are Player 1!");
        MapUtil.printBoard(Map.getMapRepresentation());
        LOGGER.info("Please pick a field from 1-9:");
        int input = SCANNER.nextInt();
        while (input < 1 || input > 9) {
            LOGGER.warning("Please input a number between 1 and 9");
            input = SCANNER.nextInt();
        }

        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), input - 1, 0));
        Map.getMoves().remove(input - 1);
        MapUtil.printBoard(Map.getMapRepresentation());

        gameLoop(choice);
    }

    private static void gameLoop(int choice) {
        int move;
        int player;

        for (int i = 1; i <= 8; i++) {
            if (i % 2 == 0) {
                move = playerMove() - 1;
                player = 0;
            } else {
                computerMove(choice);
                move = Map.getReturnMove();
                player = 1;
            }

            Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), move, player));
            Map.getMoves().remove(move);
            MapUtil.printBoard(Map.getMapRepresentation());
            if (checkWin()) {
                break;
            }
        }
    }

    private static int playerMove() {
        LOGGER.info("Please pick a field from 1-9:");
        int input = SCANNER.nextInt();
        while (input < 1 || input > 9) {
            LOGGER.warning("Please input a number between 1 and 9");
            input = SCANNER.nextInt();
        }

        int tempMap = Map.setTile(Map.getMapRepresentation(), input - 1, 0);
        while (tempMap == Integer.MIN_VALUE) {
            input = SCANNER.nextInt();
            tempMap = Map.setTile(Map.getMapRepresentation(), input - 1, 0);
        }

        return input;
    }

    private static void computerMove(int choice) {
        long start = System.nanoTime();

        // MiniMax Opponent
        if (choice == 0) {
            MiniMaxSearch.search();
            long end = System.nanoTime() - start;
            MapUtil.printStats("MiniMax", end);
        }
        // Alpha-Beta Opponent
        else {
            AlphaBetaSearch.search();
            long end = System.nanoTime() - start;
            MapUtil.printStats("Alpha-Beta", end);
        }

        Map.setVisitedStates(0);
        LOGGER.info("Your opponent picked: " + (Map.getReturnMove() + 1));
    }

    private static boolean checkWin() {
        int utility = Utility.compute(Map.getMapRepresentation());
        if (utility == 0) {
            LOGGER.info("Game done!\nDraw!");
            return true;
        } else if (utility == 1) {
            LOGGER.info("Game done!\nYour opponent wins!");
            return true;
        } else if (utility == -1) {
            LOGGER.info("Game done!\nYou win!");
            return true;
        } else {
            return false;
        }
    }
}