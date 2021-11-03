package de.marcluque.tictactoeai;

import de.marcluque.tictactoeai.map.Map;
import de.marcluque.tictactoeai.search.AlphaBetaSearch;
import de.marcluque.tictactoeai.search.MiniMaxSearch;
import de.marcluque.tictactoeai.utils.MapUtil;

/*
 * Created with <3 by marcluque, March 2021
 */
public final class PerformanceTest {

    private PerformanceTest() { }

    public static void main(final String[] args) {
        // Warm-up
        for (int i = 0; i < 100; i++) {
            MiniMaxSearch.search();
        }

        // Minimax
        long best = Integer.MAX_VALUE;
        for (int i = 0; i < 10000; i++) {
            Map.setVisitedStates(0);
            long start = System.nanoTime();
            MiniMaxSearch.search();
            long end = System.nanoTime();
            if ((end - start) < best) {
                best = (end - start);
            }
        }

        MapUtil.printStats("MiniMax", best);

        // Alpha-Beta
        best = Integer.MAX_VALUE;
        for (int i = 0; i < 10000; i++) {
            Map.setVisitedStates(0);
            long start = System.nanoTime();
            AlphaBetaSearch.search();
            long end = System.nanoTime();
            if ((end - start) < best) {
                best = (end - start);
            }
        }

        MapUtil.printStats("Alpha-Beta", best);
    }
}
