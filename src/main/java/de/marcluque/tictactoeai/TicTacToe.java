package de.marcluque.tictactoeai;

import de.marcluque.tictactoeai.map.Map;
import de.marcluque.tictactoeai.search.AlphaBetaSearch;
import de.marcluque.tictactoeai.search.MiniMaxSearch;


import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * Created with <3 by marcluque, March 2020
 */
public class TicTacToe {

    private static final Logger logger = Logger.getGlobal();

    private static final Scanner SCANNER = new Scanner(System.in);

    public static void main(String[] args) {
        String currentBoardString = "Current board:";

        logger.info("Please pick whether opponent shall use Alpha-Beta pruning (1) or plain MiniMax (0)");
        int choice = SCANNER.nextInt();
        while (choice != 0 && choice != 1) {
            logger.warning("Please pick 0 or 1");
            choice = SCANNER.nextInt();
        }

        logger.info("You are Player 1!");
        logger.info(currentBoardString);
        Map.printBoard(Map.getMapRepresentation());
        logger.info("Please pick a field from 1-9:");
        int input = SCANNER.nextInt();
        while (input < 1 || input > 9) {
            logger.warning("Please input a number between 1 and 9");
            input = SCANNER.nextInt();
        }

        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), input - 1, 0));
        Map.getMoves().remove(input - 1);
        logger.info(currentBoardString);
        Map.printBoard(Map.getMapRepresentation());

        gameLoop(choice);
    }

    private static void gameLoop(int choice) {
        String currentBoardString = "Current board:";
        int input;

        for (int i = 0; i < 4; i++) {
            long start = System.nanoTime();

            // MiniMax Opponent
            if (choice == 0) {
                MiniMaxSearch.search();
                long end = System.nanoTime() - start;
                Map.printStats("MiniMax", end);
            }
            // Alpha-Beta Opponent
            else {
                AlphaBetaSearch.search();
                long end = System.nanoTime() - start;
                Map.printStats("Alpha-Beta", end);
            }
            logger.info("Your opponent picked: " + (Map.getReturnMove() + 1));

            Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), Map.getReturnMove(), 1));
            Map.getMoves().remove(Map.getReturnMove());
            logger.info(currentBoardString);
            Map.printBoard(Map.getMapRepresentation());
            if (checkWin()) {
                break;
            }

            logger.info("Please pick a field from 1-9:");
            input = SCANNER.nextInt();
            while (input < 1 || input > 9) {
                logger.warning("Please input a number between 1 and 9");
                input = SCANNER.nextInt();
            }

            int tempMap = Map.setTile(Map.getMapRepresentation(), input - 1, 0);
            while (tempMap == Integer.MIN_VALUE) {
                input = SCANNER.nextInt();
                tempMap = Map.setTile(Map.getMapRepresentation(), input - 1, 0);
            }

            Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), input - 1, 0));
            Map.getMoves().remove(input - 1);
            logger.info(currentBoardString);
            Map.printBoard(Map.getMapRepresentation());
            if (checkWin()) {
                break;
            }

            Map.setVisitedStates(0);
        }
    }

    private static boolean checkWin() {
        int utility = Map.utility(Map.getMapRepresentation());
        switch (utility) {
            case 0:
                logger.info("Game done!\nDraw!");
                return true;
            case 1:
                logger.info("Game done!\nYour opponent wins!");
                return true;
            case -1:
                logger.info("Game done!\nYou win!");
                return true;
            default:
                logger.warning(String.format("Undefined Utility found: %d", utility));
                return false;
        }
    }
}