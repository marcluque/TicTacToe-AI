import java.util.function.Consumer;

/*
 * Created with <3 by marcluque, March 2020
 */
public class MiniMaxSearch {

    public static void miniMax() {
        int map = Map.map;
        int value = Integer.MIN_VALUE;
        Map.visitedStates++;

        for (int move : Map.moves) {
            int newValue = minValue(Map.setTile(map, move, Map.MAX));

            if (newValue > value) {
                Map.returnMove = move;
                value = newValue;
            }
        }
    }

    private static int maxValue(int map) {
        Map.visitedStates++;

        // Terminal test
        int result = Map.utility(map);
        if (result != 100) {
            return result;
        }
        final int[] value = {Integer.MIN_VALUE};

        // Calculate available moves and execute when found
        search(map, move -> {
            value[0] = Math.max(value[0], minValue(Map.setTile(map, move, Map.MAX)));
        });

        return value[0];
    }

    private static int minValue(int map) {
        Map.visitedStates++;

        // Terminal test
        int result = Map.utility(map);
        if (result != 100) {
            return result;
        }

        final int[] value = {Integer.MAX_VALUE};

        // Calculate available moves and execute when found
        search(map, move -> {
            value[0] = Math.min(value[0], maxValue(Map.setTile(map, move, Map.MIN)));
        });

        return value[0];
    }

    public static void search(int map, Consumer<Integer> consumer) {
        for (int position = 0; position < 9; position++) {
            if ((map & (1 << position + 9)) != (1 << position + 9) && (map & (1 << position)) != (1 << position)) {
                consumer.accept(position);
            }
        }
    }
}