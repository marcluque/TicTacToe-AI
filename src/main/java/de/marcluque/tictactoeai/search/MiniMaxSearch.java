package de.marcluque.tictactoeai.search;

import de.marcluque.tictactoeai.map.Map;

import java.util.function.IntConsumer;

/*
 * Created with <3 by marcluque, March 2020
 */
public class MiniMaxSearch {

    private MiniMaxSearch() {}

    public static void search() {
        int map = Map.getMapRepresentation();
        int value = Integer.MIN_VALUE;
        Map.setVisitedStates(Map.getVisitedStates() + 1);

        for (int move : Map.getMoves()) {
            int newValue = minValue(Map.setTile(map, move, Map.getMAX()));

            if (newValue > value) {
                Map.setReturnMove(move);
                value = newValue;
            }
        }
    }

    private static int maxValue(int map) {
        Map.setVisitedStates(Map.getVisitedStates() + 1);

        // Terminal test
        int result = Map.utility(map);
        if (result != 100) {
            return result;
        }
        final int[] value = {Integer.MIN_VALUE};

        // Calculate available moves and execute when found
        search(map, move -> value[0] = Math.max(value[0], minValue(Map.setTile(map, move, Map.getMAX()))));

        return value[0];
    }

    private static int minValue(int map) {
        Map.setVisitedStates(Map.getVisitedStates() + 1);

        // Terminal test
        int result = Map.utility(map);
        if (result != 100) {
            return result;
        }

        final int[] value = {Integer.MAX_VALUE};

        // Calculate available moves and execute when found
        search(map, move -> value[0] = Math.min(value[0], maxValue(Map.setTile(map, move, Map.getMIN()))));

        return value[0];
    }

    private static void search(int map, IntConsumer consumer) {
        for (int position = 0; position < 9; position++) {
            if ((map & (1 << position + 9)) != (1 << position + 9) && (map & (1 << position)) != (1 << position)) {
                consumer.accept(position);
            }
        }
    }
}