import java.util.Scanner;

/*
 * Created with love by DataSecs on 16.03.2020.
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
            // MiniMax Opponent
            if (choice == 0) {
                long start = System.nanoTime();
                MiniMaxSearch.miniMax(1);
                long end = System.nanoTime() - start;
                System.out.println("=============================== Stats MiniMax ===============================");
                System.out.printf("Found move: %d%n", (Map.returnMove + 1));
                System.out.printf("Time: %d ns = %s microsec = %s ms%n", end, end / 1_000d, end / 1_000_000d);
                System.out.printf("Visited states: %d%n", Map.visitedStates);
                System.out.printf("Time/State: %d ns = %s microsec = %s ms%n", end / Map.visitedStates, (end / 1_000d) / Map.visitedStates, (end / 1_000_000d) / Map.visitedStates);
                System.out.println("============================================================================");

                System.out.println("Your opponent picked: " + (Map.returnMove + 1));
                Map.map = Map.setTile(Map.map, Map.returnMove, 1);
                Map.moves.remove(Map.returnMove);
                System.out.println("Current board:");
                Map.printBoard(Map.map);
                if (checkWin()) {
                    break;
                }
            }
            // Alpha-Beta Opponent
            else {
                long start = System.nanoTime();
                AlphaBetaSearch.alphaBetaSearch(1);
                long end = System.nanoTime() - start;
                System.out.println("============================= Stats Alpha-Beta ============================");
                System.out.printf("Found move: %d%n", (Map.returnMove + 1));
                System.out.printf("Time: %d ns = %s microsec = %s ms%n", end, end / 1_000d, end / 1_000_000d);
                System.out.printf("Visited states: %d%n", Map.visitedStates);
                System.out.printf("Time/State: %d ns = %s microsec = %s ms%n", end / Map.visitedStates, (end / 1_000d) / Map.visitedStates, (end / 1_000_000d) / Map.visitedStates);
                System.out.println("===========================================================================");

                System.out.println("Your opponent picked: " + (Map.returnMove + 1));
                Map.map = Map.setTile(Map.map, Map.returnMove, 1);
                Map.moves.remove(Map.returnMove);
                System.out.println("Current board:");
                Map.printBoard(Map.map);
                if (checkWin()) {
                    break;
                }
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