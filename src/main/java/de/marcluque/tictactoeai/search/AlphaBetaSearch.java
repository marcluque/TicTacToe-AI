package de.marcluque.tictactoeai.search;

import de.marcluque.tictactoeai.map.Map;
import de.marcluque.tictactoeai.map.Utility;

/*
 * Created with <3 by marcluque, March 2020
 */
public final class AlphaBetaSearch {

    private AlphaBetaSearch() {}

    public static void search() {
        final int intMap = Map.getMapRepresentation();
        int value = Integer.MIN_VALUE;
        Map.setVisitedStates(Map.getVisitedStates() + 1);

        for (int move : Map.getMoves()) {
            final int newValue = minValue(Map.setTile(intMap, move, Map.getMAX()), Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (newValue > value) {
                Map.setReturnMove(move);
                value = newValue;
            }
        }
    }

    private static int maxValue(final int intMap, int alpha, int beta) {
        Map.setVisitedStates(Map.getVisitedStates() + 1);

        // Terminal test
        final int result = Utility.compute(intMap);
        if (result != Utility.MID_GAME_UTILITY) {
            return result;
        }

        int value = Integer.MIN_VALUE;

        // Calculate available moves and execute when found
        for (int position = 0; position < 9; position++) {
            if ((intMap & (1 << position + 9)) != (1 << position + 9) && (intMap & (1 << position)) != (1 << position)) {
                value = Math.max(value, minValue(Map.setTile(intMap, position, Map.getMAX()), alpha, beta));

                if (value >= beta) {
                    break;
                }

                alpha = Math.max(alpha, value);
            }
        }

        return value;
    }

    private static int minValue(final int intMap, int alpha, int beta) {
        Map.setVisitedStates(Map.getVisitedStates() + 1);

        // Terminal test
        final int result = Utility.compute(intMap);
        if (result != Utility.MID_GAME_UTILITY) {
            return result;
        }

        int value = Integer.MAX_VALUE;

        // Calculate available moves and execute when found
        for (int position = 0; position < 9; position++) {
            if ((intMap & (1 << position + 9)) != (1 << position + 9) && (intMap & (1 << position)) != (1 << position)) {
                value = Math.min(value, maxValue(Map.setTile(intMap, position, Map.getMIN()), alpha, beta));

                if (value <= alpha) {
                    break;
                }

                beta = Math.min(beta, value);
            }
        }

        return value;
    }
}