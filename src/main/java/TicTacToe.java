import java.util.Scanner;

/*
 * Created with <3 by marcluque, March 2020
 */
public class TicTacToe {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Please pick whether opponent shall use Alpha-Beta pruning (1) or plain MiniMax (0)");
        int choice = sc.nextInt();
        while (choice != 0 && choice != 1) {
            System.err.println("Please pick 0 or 1");
            choice = sc.nextInt();
        }

        System.out.println("You are Player 1!");
        System.out.println("Current board:");
        Map.printBoard(Map.map);
        System.out.println("Please pick a field from 1-9:");
        int input = sc.nextInt();
        while (input < 1 || input > 9) {
            System.err.println("Please input a number between 1 and 9");
            input = sc.nextInt();
        }

        Map.map = Map.setTile(Map.map, input - 1, 0);
        Map.moves.remove(input - 1);
        System.out.println("Current board:");
        Map.printBoard(Map.map);

        for (int i = 0; i < 4; i++) {
            long start = System.nanoTime();

            // MiniMax Opponent
            if (choice == 0) {
                MiniMaxSearch.search();
                long end = System.nanoTime() - start;
                Map.printStats("MiniMax", end);
                System.out.println("Your opponent picked: " + (Map.returnMove + 1));
            }
            // Alpha-Beta Opponent
            else {
                AlphaBetaSearch.search();
                long end = System.nanoTime() - start;
                Map.printStats("Alpha-Beta", end);
                System.out.println("Your opponent picked: " + (Map.returnMove + 1));
            }

            Map.map = Map.setTile(Map.map, Map.returnMove, 1);
            Map.moves.remove(Map.returnMove);
            System.out.println("Current board:");
            Map.printBoard(Map.map);
            if (checkWin()) {
                break;
            }

            System.out.println("Please pick a field from 1-9:");
            input = sc.nextInt();
            while (input < 1 || input > 9) {
                System.err.println("Please input a number between 1 and 9");
                input = sc.nextInt();
            }

            int tempMap = Map.setTile(Map.map, input - 1, 0);
            while (tempMap == Integer.MIN_VALUE) {
                input = sc.nextInt();
                tempMap = Map.setTile(Map.map, input - 1, 0);
            }

            Map.map = Map.setTile(Map.map, input - 1, 0);
            Map.moves.remove(input - 1);
            System.out.println("Current board:");
            Map.printBoard(Map.map);
            if (checkWin()) {
                break;
            }

            Map.visitedStates = 0;
        }
    }

    private static boolean checkWin() {
        switch (Map.utility(Map.map)) {
            case 0:
                System.out.println("Game done!\nDraw!");
                return true;
            case 1:
                System.out.println("Game done!\nYour opponent wins!");
                return true;
            case -1:
                System.out.println("Game done!\nYou win!");
                return true;
        }

        return false;
    }
}