import java.util.*;

public class Map {

    public static int map;

    public static Set<Integer> moves = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));

    public static int visitedStates = -10;

    public static int returnMove = -1;

    public static int computerPlayer = -1;

    // Position is 0 to 8, player is 0 or 1
    public static int setTile(int map, int position, int player) {

        // Check tile is not in use
        if ((map & (1 << position + 22)) == (1 << position + 22) || (map & (1 << position + 13)) == (1 << position + 13)) {
            System.out.printf("Position %d is already set, please pick another position!%n", position + 1);
            return Integer.MIN_VALUE;
        } else {
            // Else write 1 to the target position
            map = map | (1 << position + 22 - (player * 9));
        }

        return map;
    }

    public static int utility(int map) {
        // 1. Win: If you have two in a row, play the third to get three in a row.
        // 2. Block: If the opponent has two in a row, play the third to block them.
        // 3. Fork: Create an opportunity where you can win in two ways.
        // 4. Block Opponent's Fork:
        //      Option 1: Create two in a row to force the opponent into defending, as long as it doesn't result in them creating a fork or winning. For example, if "X" has a corner, "O" has the center, and "X" has the opposite corner as well, "O" must not play a corner in order to win. (Playing a corner in this scenario creates a fork for "X" to win.)
        //      Option 2: If there is a configuration where the opponent can fork, block that fork.
        // 5. Center: Play the center.
        // 6. Opposite Corner: If the opponent is in the corner, play the opposite corner.
        // 7. Empty Corner: Play an empty corner.
        // 8. Empty Side: Play an empty side.

        int result = 0;
        int[] rows = new int[3];
        int[] cols = new int[3];
        int[] diags = new int[3];

        for (int position = 0; position < 9; position++) {
            // Check whether position is set by player
            int targetBitPlayer0 = (1 << (position + 22));
            int targetBitPlayer1 = (1 << (position + 13));

            int value0 = (map & targetBitPlayer0) == targetBitPlayer0 ? (computerPlayer == 0 ? 1 : -1) : 0;
            int value1 = (map & targetBitPlayer1) == targetBitPlayer1 ? (computerPlayer == 1 ? 1 : -1) : 0;

            // Row
            rows[position / 3] = rows[position / 3] + value0 + value1;
            if (rows[position / 3] == 3) {
                result++;
            } else if (rows[position / 3] == -3) {
                result--;
            }

            // Columns
            cols[position % 3] = cols[position % 3] + value0 + value1;
            if (cols[position % 3] == 3) {
                result++;
            } else if (cols[position % 3] == -3) {
                result--;
            }

            // Diagonals
            // First case is on the diagonal (0,0)..(n,n)
            if (position % 3 == position / 3) {
                diags[0] = diags[0] + value0 + value1;
                if (diags[0] == 3) {
                    result++;
                } else if (diags[0] == -3) {
                    result--;
                }
            }

            // Second case is on the diagonal (0,n)..(n,0)
            if ((position / 3) + (position % 3) == 2) {
                diags[1] = diags[1] + value0 + value1;
                if (diags[1] == 3) {
                    result++;
                } else if (diags[1] == -3) {
                    result--;
                }
            }
        }

        // If no winner was found AND board is full, we have a draw
        boolean fullBoard = ((map & (0b111111111 << 22)) | ((map & (0b111111111 << 13)) << 9)) == 2143289344;
        if (!fullBoard && result == 0) {
            result = 100;
        }

        return result;
    }

    public static void printBoard(int map) {
        for (int i = 0; i < 9; i++) {
            String printSign = "[ ]";

            // Player 0
            int targetBit = 22 + i;
            if ((map & (1 << targetBit)) == (1 << targetBit)) {
                printSign = "[X]";
            }

            // Player 1
            targetBit = 13 + i;
            if ((map & (1 << targetBit)) == (1 << targetBit)) {
                printSign = "[O]";
            }

            System.out.print(printSign);

            if ((i + 1) % 3 == 0) {
                System.out.print("\n");
            }
        }
    }
}