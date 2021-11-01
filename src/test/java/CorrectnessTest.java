import de.marcluque.tictactoeai.map.Map;
import de.marcluque.tictactoeai.search.MiniMaxSearch;

import java.util.List;
import java.util.logging.Logger;

/*
 * Created with <3 by marcluque, March 2021
 */
public class CorrectnessTest {

    private static final Logger logger = Logger.getGlobal();

    public static void main(String[] args) {
        /* Board:
         * O X O
         * X O X
         * X
         */
        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 0, Map.getMIN()));
        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 2, Map.getMIN()));
        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 4, Map.getMIN()));

        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 1, Map.getMAX()));
        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 3, Map.getMAX()));
        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 5, Map.getMAX()));
        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 6, Map.getMAX()));

        List.of(0, 1, 2, 3, 4, 5, 6).forEach(Map.getMoves()::remove);

        MiniMaxSearch.search();

        assert Map.getVisitedStates() == 5;
        assert Map.getReturnMove() == 8;
        assert Map.utility(Map.getMapRepresentation()) == 100;

        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 7, Map.getMIN()));
        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 8, Map.getMAX()));

        assert Map.utility(Map.getMapRepresentation()) == 0;

        long best = Integer.MAX_VALUE;
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();
            Map.utility(Map.getMapRepresentation());
            long end = System.nanoTime();
            if ((end - start) < best) {
                best = (end - start);
            }
        }

        logger.info("TIME: " + best + " ns = " + (best / 1000d) + " µs");


        /* Board:
         * O X O
         * O X
         *   X
         */
        Map.setMapRepresentation(0);
        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 1, Map.getMIN()));
        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 4, Map.getMIN()));
        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 7, Map.getMIN()));

        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 0, Map.getMAX()));
        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 2, Map.getMAX()));
        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 3, Map.getMAX()));

        assert Map.utility(Map.getMapRepresentation()) == -1;
    }
}