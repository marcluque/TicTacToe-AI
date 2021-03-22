import java.util.*;

/*
 * Created with <3 by marcluque, March 2020
 */
public class Map {

    public static int map;

    public static Set<Integer> moves = new HashSet<>(List.of(0, 1, 2, 3, 4, 5, 6, 7, 8));

    public static int visitedStates = 0;

    public static int returnMove = -1;

    public static final int MAX = 1;

    public static final int MIN = 0;

    private static final int[] TERMINAL_TEST = new int[8];

    static {
        // ROW 1
        TERMINAL_TEST[0] = 0b001001001;
        // ROW 2
        TERMINAL_TEST[1] = TERMINAL_TEST[0] << 1;
        // ROW 3
        TERMINAL_TEST[2] = TERMINAL_TEST[0] << 2;

        // COL 1
        TERMINAL_TEST[3] = 0b000000111;
        // COL 2
        TERMINAL_TEST[4] = TERMINAL_TEST[3] << 3;
        // COL 3
        TERMINAL_TEST[5] = TERMINAL_TEST[3] << 6;

        // DIAG 1
        TERMINAL_TEST[6] = 0b100010001;
        // DIAG 2
        TERMINAL_TEST[7] = 0b001010100;
    }

    // Position is 0 to 8, player is 0 or 1
    public static int setTile(int map, int position, int player) {
        // Check tile is not in use
        if ((map & (1 << position + 9)) == (1 << position + 9) || (map & (1 << position)) == (1 << position)) {
            System.out.printf("Position %d is already set, please pick another position!%n", position + 1);
            return Integer.MIN_VALUE;
        } else {
            // Else write 1 to the target position
            map |= (1 << position + (player * 9));
        }

        return map;
    }

    public static int utility(int map) {
        int maxMap = (map & 0b111111111000000000) >> 9;
        int minMap = map & 0b111111111;

        for (int j : TERMINAL_TEST) {
            // MAX
            if ((maxMap & j) == j) {
                return 1;
            }
            // MIN
            else if ((minMap & j) == j) {
                return -1;
            }
        }

        boolean mapFull = ((map & 0b111111111) | (map & 0b111111111000000000) >> 9) == 0b111111111;
        return mapFull ? 0 : 100;
    }

    public static void printBoard(int map) {
        for (int i = 0; i < 9; i++) {
            String printSign = "[ ]";

            // Player 0
            int targetBit = 9 + i;
            if ((map & (1 << targetBit)) == (1 << targetBit)) {
                printSign = "[X]";
            }

            // Player 1
            targetBit = i;
            if ((map & (1 << targetBit)) == (1 << targetBit)) {
                printSign = "[O]";
            }

            System.out.print(printSign);

            if ((i + 1) % 3 == 0) {
                System.out.print("\n");
            }
        }
    }

    public static void printStats(String type, long endTime) {
        System.out.println("========================== Stats " + type + " =========================");
        System.out.printf("Found move: %d%n", (Map.returnMove + 1));
        System.out.printf("Time: %d ns = %s µs = %s ms%n", endTime, endTime / 1_000d, endTime / 1_000_000d);
        System.out.printf("Visited states: %d%n", Map.visitedStates);
        System.out.printf("Time/State: %d ns = %s µs = %s ms%n", endTime / Map.visitedStates, (endTime / 1_000d) / Map.visitedStates, (endTime / 1_000_000d) / Map.visitedStates);
        System.out.println("=====================================================================");
    }
}