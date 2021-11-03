package de.marcluque.tictactoeai.search;

import de.marcluque.tictactoeai.map.Map;
import de.marcluque.tictactoeai.map.Utility;

import java.util.function.IntConsumer;

/*
 * Created with <3 by marcluque, March 2020
 */
public final class MiniMaxSearch {

    private MiniMaxSearch() {}

    public static void search() {
        int mapRepresentation = Map.getMapRepresentation();
        int value = Integer.MIN_VALUE;
        Map.setVisitedStates(Map.getVisitedStates() + 1);

        for (int move : Map.getMoves()) {
            int newValue = minValue(Map.setTile(mapRepresentation, move, Map.getMAX()));

            if (newValue > value) {
                Map.setReturnMove(move);
                value = newValue;
            }
        }
    }

    private static int maxValue(final int mapRepresentation) {
        Map.setVisitedStates(Map.getVisitedStates() + 1);

        // Terminal test
        final int result = Utility.compute(mapRepresentation);
        if (result != Utility.MID_GAME_UTILITY) {
            return result;
        }
        final int[] value = {Integer.MIN_VALUE};

        // Calculate available moves and execute when found
        search(mapRepresentation, move -> value[0] = Math.max(value[0], minValue(Map.setTile(mapRepresentation, move, Map.getMAX()))));

        return value[0];
    }

    private static int minValue(final int mapRepresentation) {
        Map.setVisitedStates(Map.getVisitedStates() + 1);

        // Terminal test
        final int result = Utility.compute(mapRepresentation);
        if (result != Utility.MID_GAME_UTILITY) {
            return result;
        }

        final int[] value = {Integer.MAX_VALUE};

        // Calculate available moves and execute when found
        search(mapRepresentation, move -> value[0] = Math.min(value[0], maxValue(Map.setTile(mapRepresentation, move, Map.getMIN()))));

        return value[0];
    }

    private static void search(int mapRepresentation, IntConsumer consumer) {
        for (int position = 0; position < 9; position++) {
            if ((mapRepresentation & (1 << position + 9)) != (1 << position + 9) && (mapRepresentation & (1 << position)) != (1 << position)) {
                consumer.accept(position);
            }
        }
    }
}