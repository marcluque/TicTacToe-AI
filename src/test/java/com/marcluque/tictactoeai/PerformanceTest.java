package com.marcluque.tictactoeai;

import com.marcluque.tictactoeai.map.Map;
import com.marcluque.tictactoeai.utils.MapUtil;
import com.marcluque.tictactoeai.search.AlphaBetaSearch;
import com.marcluque.tictactoeai.search.MiniMaxSearch;

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
