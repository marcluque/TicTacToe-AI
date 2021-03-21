import java.util.List;

/*
 * Created with <3 by marcluque, March 2021
 */
public class CorrectnessTest {

    public static void main(String[] args) {
        /* Board:
         * O X O
         * X O X
         * X
         */
        Map.map = Map.setTile(Map.map, 0, Map.MIN);
        Map.map = Map.setTile(Map.map, 2, Map.MIN);
        Map.map = Map.setTile(Map.map, 4, Map.MIN);

        Map.map = Map.setTile(Map.map, 1, Map.MAX);
        Map.map = Map.setTile(Map.map, 3, Map.MAX);
        Map.map = Map.setTile(Map.map, 5, Map.MAX);
        Map.map = Map.setTile(Map.map, 6, Map.MAX);

        Map.moves.removeAll(List.of(0, 1, 2, 3, 4, 5, 6));

        MiniMaxSearch.miniMax();

        assert Map.visitedStates == 5;
        assert Map.returnMove == 8;
        assert Map.utility(Map.map) == 100;

        Map.map = Map.setTile(Map.map, 7, Map.MIN);
        Map.map = Map.setTile(Map.map, 8, Map.MAX);

        assert Map.utility(Map.map) == 0;

        long best = Integer.MAX_VALUE;
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();
            Map.utility(Map.map);
            long end = System.nanoTime();
            if ((end - start) < best) {
                best = (end - start);
            }
        }

        System.out.println("TIME: " + best + " ns = " + (best / 1000d) + " µs");


        /* Board:
         * O X O
         * O X
         *   X
         */
        Map.map = 0;
        Map.map = Map.setTile(Map.map, 1, Map.MIN);
        Map.map = Map.setTile(Map.map, 4, Map.MIN);
        Map.map = Map.setTile(Map.map, 7, Map.MIN);

        Map.map = Map.setTile(Map.map, 0, Map.MAX);
        Map.map = Map.setTile(Map.map, 2, Map.MAX);
        Map.map = Map.setTile(Map.map, 3, Map.MAX);

        assert Map.utility(Map.map) == -1;
    }
}