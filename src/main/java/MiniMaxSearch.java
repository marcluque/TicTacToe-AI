import java.util.function.Consumer;

/*
 * Created with love by DataSecs on 16.03.2020.
 */
public class MiniMaxSearch {

    public static void miniMax(int player) {
        int map = Map.map;
        Map.computerPlayer = player;

        int value = Integer.MIN_VALUE;

        for (int move : Map.moves) {
            int newValue = minValue(Map.setTile(map, move, player), (player + 1) % 2);

            if (newValue > value) {
                Map.returnMove = move;
                value = newValue;
            }
        }
    }

    private static int maxValue(int map, int player) {
        // Terminal test
        int result = Map.utility(map);
        if (result != 100) {
            return result;
        }

        Map.visitedStates++;
        final int[] value = {Integer.MIN_VALUE};

        // Calculate available moves and execute when found
        search(map, move -> {
            value[0] = Math.max(value[0], minValue(Map.setTile(map, move, player), (player + 1) % 2));
        });

        return value[0];
    }

    private static int minValue(int map, int player) {
        // Terminal test
        int result = Map.utility(map);
        if (result != 100) {
            return result;
        }

        Map.visitedStates++;
        final int[] value = {Integer.MAX_VALUE};

        // Calculate available moves and execute when found
        search(map, move -> {
            value[0] = Math.min(value[0], maxValue(Map.setTile(map, move, player), (player + 1) % 2));
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