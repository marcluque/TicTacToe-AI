public class AlphaBetaSearch {

    public static void alphaBetaSearch(int player) {
        int map = Map.map;
        Map.computerPlayer = player;

        int value = Integer.MIN_VALUE;

        for (int move : Map.moves) {
            int newValue = minValue(Map.setTile(map, move, player), (player + 1) % 2, Integer.MIN_VALUE, Integer.MAX_VALUE);
            if (newValue > value) {
                Map.returnMove = move;
                value = newValue;
            }
        }
    }

    private static int maxValue(int map, int player, int alpha, int beta) {
        // Terminal test
        int result = Map.utility(map);
        if (result != 100) {
            return result;
        }

        Map.visitedStates++;
        int value = Integer.MIN_VALUE;

        // Calculate available moves
        int[] moves = new int[9];
        int moveSize = 0;
        for (int position = 0; position < 9; position++) {
            if ((map & (1 << position + 22)) != (1 << position + 22) && (map & (1 << position + 13)) != (1 << position + 13)) {
                moves[moveSize++] = position;
            }
        }

        // Execute moves
        for (int i = 0; i < moveSize; i++) {
            value = Math.max(value, minValue(Map.setTile(map, moves[i], player), (player + 1) % 2, alpha, beta));

            if (value >= beta) {
                return value;
            }

            alpha = Math.max(alpha, value);
        }

        return value;
    }

    private static int minValue(int map, int player, int alpha, int beta) {
        // Terminal test
        int result = Map.utility(map);
        if (result != 100) {
            return result;
        }

        Map.visitedStates++;
        int value = Integer.MAX_VALUE;

        // Calculate available moves
        int[] moves = new int[9];
        int moveSize = 0;
        for (int position = 0; position < 9; position++) {
            if ((map & (1 << position + 22)) != (1 << position + 22) && (map & (1 << position + 13)) != (1 << position + 13)) {
                moves[moveSize++] = position;
            }
        }

        // Execute moves
        for (int i = 0; i < moveSize; i++) {
            value = Math.min(value, maxValue(Map.setTile(map, moves[i], player), (player + 1) % 2, alpha, beta));

            if (value <= alpha) {
                return value;
            }

            beta = Math.min(beta, value);
        }

        return value;
    }
}