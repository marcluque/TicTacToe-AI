package de.marcluque.tictactoeai.search;

import de.marcluque.tictactoeai.map.Map;
import de.marcluque.tictactoeai.map.Utility;

/*
 * Created with <3 by marcluque, March 2020
 */
public final class AlphaBetaSearch {

    private AlphaBetaSearch() {}

    public static void search() {
        int map = Map.getMapRepresentation();
        int value = Integer.MIN_VALUE;
        Map.setVisitedStates(Map.getVisitedStates() + 1);

        for (int move : Map.getMoves()) {
            int newValue = minValue(Map.setTile(map, move, Map.getMAX()), Integer.MIN_VALUE, Integer.MAX_VALUE);

            if (newValue > value) {
                Map.setReturnMove(move);
                value = newValue;
            }
        }
    }

    private static int maxValue(int map, int alpha, int beta) {
        Map.setVisitedStates(Map.getVisitedStates() + 1);

        // Terminal test
        final int result = Utility.compute(map);
        if (result != Utility.MID_GAME_UTILITY) {
            return result;
        }

        int value = Integer.MIN_VALUE;

        // Calculate available moves and execute when found
        for (int position = 0; position < 9; position++) {
            if ((map & (1 << position + 9)) != (1 << position + 9) && (map & (1 << position)) != (1 << position)) {
                value = Math.max(value, minValue(Map.setTile(map, position, Map.getMAX()), alpha, beta));

                if (value >= beta) {
                    break;
                }

                alpha = Math.max(alpha, value);
            }
        }

        return value;
    }

    private static int minValue(int map, int alpha, int beta) {
        Map.setVisitedStates(Map.getVisitedStates() + 1);

        // Terminal test
        final int result = Utility.compute(map);
        if (result != Utility.MID_GAME_UTILITY) {
            return result;
        }

        int value = Integer.MAX_VALUE;

        // Calculate available moves and execute when found
        for (int position = 0; position < 9; position++) {
            if ((map & (1 << position + 9)) != (1 << position + 9) && (map & (1 << position)) != (1 << position)) {
                value = Math.min(value, maxValue(Map.setTile(map, position, Map.getMIN()), alpha, beta));

                if (value <= alpha) {
                    break;
                }

                beta = Math.min(beta, value);
            }
        }

        return value;
    }
}