/*
 * Created with <3 by marcluque, March 2020
 */
public class AlphaBetaSearch {

    public static void alphaBetaSearch() {
        int map = Map.map;
        int value = Integer.MIN_VALUE;
        Map.visitedStates++;

        for (int move : Map.moves) {
            int newValue = minValue(Map.setTile(map, move, Map.MAX), Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (newValue > value) {
                Map.returnMove = move;
                value = newValue;
            }
        }
    }

    private static int maxValue(int map, int alpha, int beta) {
        Map.visitedStates++;

        // Terminal test
        int result = Map.utility(map);
        if (result != 100) {
            return result;
        }

        int value = Integer.MIN_VALUE;

        // Calculate available moves and execute when found
        for (int position = 0; position < 9; position++) {
            if ((map & (1 << position + 9)) != (1 << position + 9) && (map & (1 << position)) != (1 << position)) {
                value = Math.max(value, minValue(Map.setTile(map, position, Map.MAX), alpha, beta));

                if (value >= beta) {
                    return value;
                }

                alpha = Math.max(alpha, value);
            }
        }

        return value;
    }

    private static int minValue(int map, int alpha, int beta) {
        Map.visitedStates++;

        // Terminal test
        int result = Map.utility(map);
        if (result != 100) {
            return result;
        }

        int value = Integer.MAX_VALUE;

        // Calculate available moves and execute when found
        for (int position = 0; position < 9; position++) {
            if ((map & (1 << position + 9)) != (1 << position + 9) && (map & (1 << position)) != (1 << position)) {
                value = Math.min(value, maxValue(Map.setTile(map, position, Map.MIN), alpha, beta));

                if (value <= alpha) {
                    return value;
                }

                beta = Math.min(beta, value);
            }
        }

        return value;
    }
}