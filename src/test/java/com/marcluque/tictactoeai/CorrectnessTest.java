package com.marcluque.tictactoeai;

import com.marcluque.tictactoeai.map.Map;
import com.marcluque.tictactoeai.map.Utility;
import com.marcluque.tictactoeai.utils.LoggerUtil;
import com.marcluque.tictactoeai.search.MiniMaxSearch;

import java.util.List;
import java.util.logging.Logger;

/*
 * Created with <3 by marcluque, March 2021
 */
public final class CorrectnessTest {

    private static final Logger LOGGER = LoggerUtil.createLogger("com.marcluque.tictactoeai",
            "[%1$tF %1$tT] %2$s %n", CorrectnessTest.class);

    private CorrectnessTest() { }

    public static void main(final String[] args) {
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
        assert Utility.compute(Map.getMapRepresentation()) == 100;

        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 7, Map.getMIN()));
        Map.setMapRepresentation(Map.setTile(Map.getMapRepresentation(), 8, Map.getMAX()));

        assert Utility.compute(Map.getMapRepresentation()) == 0;

        long best = Integer.MAX_VALUE;
        for (int i = 0; i < 1000; i++) {
            long start = System.nanoTime();
            Utility.compute(Map.getMapRepresentation());
            long end = System.nanoTime();
            if ((end - start) < best) {
                best = (end - start);
            }
        }

        final long finalBest = best;
        LOGGER.info(() -> String.format("Fastest move: %d ns = %s µs", finalBest, finalBest / 1000d));


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

        assert Utility.compute(Map.getMapRepresentation()) == -1;
    }
}